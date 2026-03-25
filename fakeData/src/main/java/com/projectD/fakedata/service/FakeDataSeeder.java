package com.projectD.fakedata.service;

import com.github.javafaker.Faker;
import com.projectD.fakedata.config.FakeDataProperties;
import com.projectD.fakedata.entity.AppGroup;
import com.projectD.fakedata.entity.MobileOperator;
import com.projectD.fakedata.entity.Role;
import com.projectD.fakedata.entity.SimCard;
import com.projectD.fakedata.entity.SimStatusDictionary;
import com.projectD.fakedata.entity.User;
import com.projectD.fakedata.repository.AppGroupRepository;
import com.projectD.fakedata.repository.MobileOperatorRepository;
import com.projectD.fakedata.repository.RoleRepository;
import com.projectD.fakedata.repository.SimCardRepository;
import com.projectD.fakedata.repository.SimStatusDictionaryRepository;
import com.projectD.fakedata.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class FakeDataSeeder {
    private static final int MAX_ATTEMPTS = 1000;
    private static final List<String> OPERATORS = List.of("MTS", "Beeline", "MegaFon", "T2", "Yota");
    private static final List<String> STATUSES = List.of("NEW", "ACTIVE", "SUSPENDED", "BLOCKED");

    private final RoleRepository roleRepository;
    private final AppGroupRepository appGroupRepository;
    private final UserRepository userRepository;
    private final MobileOperatorRepository mobileOperatorRepository;
    private final SimStatusDictionaryRepository simStatusDictionaryRepository;
    private final SimCardRepository simCardRepository;
    private final FakeDataProperties properties;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public FakeDataSeeder(
            RoleRepository roleRepository,
            AppGroupRepository appGroupRepository,
            UserRepository userRepository,
            MobileOperatorRepository mobileOperatorRepository,
            SimStatusDictionaryRepository simStatusDictionaryRepository,
            SimCardRepository simCardRepository,
            FakeDataProperties properties
    ) {
        this.roleRepository = roleRepository;
        this.appGroupRepository = appGroupRepository;
        this.userRepository = userRepository;
        this.mobileOperatorRepository = mobileOperatorRepository;
        this.simStatusDictionaryRepository = simStatusDictionaryRepository;
        this.simCardRepository = simCardRepository;
        this.properties = properties;
    }

    @Transactional
    public void seed() {
        Faker faker = new Faker(Locale.forLanguageTag(properties.getLocale()));

        Role userRole = getOrCreateRole("USER");
        Role adminRole = getOrCreateRole("ADMIN");

        AppGroup usersGroup = getOrCreateGroup("users", Set.of(userRole));
        AppGroup adminsGroup = getOrCreateGroup("admins", Set.of(userRole, adminRole));

        List<MobileOperator> operators = OPERATORS.stream().map(this::getOrCreateOperator).toList();
        List<SimStatusDictionary> statuses = STATUSES.stream().map(this::getOrCreateStatus).toList();

        for (int i = 0; i < properties.getUsersCount(); i++) {
            User user = new User();
            user.setLogin(generateUniqueLogin(faker));
            user.setEmail(generateUniqueEmail(faker));
            user.setStatus("ACTIVE");
            user.setAuthType("LOCAL");
            user.setPasswordHash(passwordEncoder.encode(faker.internet().password(10, 16)));
            user.setGroup(ThreadLocalRandom.current().nextInt(10) == 0 ? adminsGroup : usersGroup);
            userRepository.save(user);
        }

        for (int i = 0; i < properties.getSimCardsCount(); i++) {
            SimCard simCard = new SimCard();
            LocalDateTime activationDate = generateActivationDate();
            simCard.setIccid(generateUniqueIccid(faker));
            simCard.setDefNumber(generateDefNumber(faker));
            simCard.setActivationDate(activationDate);
            simCard.setDeactivationDate(generateDeactivationDate(activationDate));
            simCard.setPlan(faker.options().option("S", "M", "L", "XL"));
            simCard.setGeolocation(null);
            simCard.setTrafficMb(ThreadLocalRandom.current().nextLong(0, 100_000));
            simCard.setMobileOperator(operators.get(ThreadLocalRandom.current().nextInt(operators.size())));
            simCard.setStatus(statuses.get(ThreadLocalRandom.current().nextInt(statuses.size())));
            simCard.setEquipment(null);
            simCardRepository.save(simCard);
        }
    }

    private Role getOrCreateRole(String authority) {
        return roleRepository.findByAuthority(authority).orElseGet(() -> {
            Role role = new Role();
            role.setAuthority(authority);
            return roleRepository.save(role);
        });
    }

    private AppGroup getOrCreateGroup(String name, Set<Role> roles) {
        return appGroupRepository.findByName(name).orElseGet(() -> {
            AppGroup group = new AppGroup();
            group.setName(name);
            group.setIsActive(true);
            group.setRoles(roles);
            return appGroupRepository.save(group);
        });
    }

    private MobileOperator getOrCreateOperator(String name) {
        return mobileOperatorRepository.findByName(name).orElseGet(() -> {
            MobileOperator operator = new MobileOperator();
            operator.setName(name);
            return mobileOperatorRepository.save(operator);
        });
    }

    private SimStatusDictionary getOrCreateStatus(String code) {
        return simStatusDictionaryRepository.findByCode(code).orElseGet(() -> {
            SimStatusDictionary status = new SimStatusDictionary();
            status.setCode(code);
            status.setDescription("Auto-generated status " + code);
            return simStatusDictionaryRepository.save(status);
        });
    }

    private String generateUniqueLogin(Faker faker) {
        for (int i = 0; i < MAX_ATTEMPTS; i++) {
            String login = faker.name().username().replace(".", "_").toLowerCase();
            if (!userRepository.existsByLogin(login)) {
                return login;
            }
        }
        throw new IllegalStateException("Could not generate unique login.");
    }

    private String generateUniqueEmail(Faker faker) {
        for (int i = 0; i < MAX_ATTEMPTS; i++) {
            String email = faker.internet().emailAddress().toLowerCase();
            if (!userRepository.existsByEmail(email)) {
                return email;
            }
        }
        throw new IllegalStateException("Could not generate unique email.");
    }

    private String generateUniqueIccid(Faker faker) {
        for (int i = 0; i < MAX_ATTEMPTS; i++) {
            String iccid = faker.numerify("89####################");
            if (!simCardRepository.existsByIccid(iccid)) {
                return iccid;
            }
        }
        throw new IllegalStateException("Could not generate unique ICCID.");
    }

    private String generateDefNumber(Faker faker) {
        return "+7" + faker.numerify("##########");
    }

    private LocalDateTime generateActivationDate() {
        return LocalDateTime.now().minusDays(ThreadLocalRandom.current().nextLong(1, 365));
    }

    private LocalDateTime generateDeactivationDate(LocalDateTime activationDate) {
        return activationDate.plusDays(ThreadLocalRandom.current().nextLong(1, 365));
    }

}
