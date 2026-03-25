package com.projectD.fakedata.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "sim_card")
public class SimCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "iccid", nullable = false, unique = true, length = 22)
    private String iccid;

    @Column(name = "def_number", nullable = false, length = 22)
    private String defNumber;

    @Column(name = "activation_date", nullable = false)
    private LocalDateTime activationDate;

    @Column(name = "deactivation_date")
    private LocalDateTime deactivationDate;

    @Column(name = "plan", length = 100)
    private String plan;

    @Column(name = "geolocation", columnDefinition = "POINT", insertable = false, updatable = false)
    private String geolocation;

    @Column(name = "traffic_mb")
    private Long trafficMb;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mo_id", nullable = false)
    private MobileOperator mobileOperator;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipment_id")
    private Equipment equipment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id", nullable = false)
    private SimStatusDictionary status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIccid() {
        return iccid;
    }

    public void setIccid(String iccid) {
        this.iccid = iccid;
    }

    public String getDefNumber() {
        return defNumber;
    }

    public void setDefNumber(String defNumber) {
        this.defNumber = defNumber;
    }

    public LocalDateTime getActivationDate() {
        return activationDate;
    }

    public void setActivationDate(LocalDateTime activationDate) {
        this.activationDate = activationDate;
    }

    public LocalDateTime getDeactivationDate() {
        return deactivationDate;
    }

    public void setDeactivationDate(LocalDateTime deactivationDate) {
        this.deactivationDate = deactivationDate;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public String getGeolocation() {
        return geolocation;
    }

    public void setGeolocation(String geolocation) {
        this.geolocation = geolocation;
    }

    public Long getTrafficMb() {
        return trafficMb;
    }

    public void setTrafficMb(Long trafficMb) {
        this.trafficMb = trafficMb;
    }

    public MobileOperator getMobileOperator() {
        return mobileOperator;
    }

    public void setMobileOperator(MobileOperator mobileOperator) {
        this.mobileOperator = mobileOperator;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    public SimStatusDictionary getStatus() {
        return status;
    }

    public void setStatus(SimStatusDictionary status) {
        this.status = status;
    }
}
