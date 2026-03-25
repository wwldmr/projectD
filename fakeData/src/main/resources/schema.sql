CREATE TABLE IF NOT EXISTS mobile_operator (
    id      BIGSERIAL PRIMARY KEY,
    name    VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS sim_status_dictionary (
    id          BIGSERIAL PRIMARY KEY,
    code        VARCHAR(50) NOT NULL UNIQUE,
    description VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS client (
    id      BIGSERIAL PRIMARY KEY,
    bdo_id  BIGINT NOT NULL UNIQUE,
    name    VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS "object" (
    id          BIGSERIAL PRIMARY KEY,
    obj_number  BIGINT NOT NULL UNIQUE,
    status      VARCHAR(100),
    address     VARCHAR(255),
    geolocation POINT,
    client_id   BIGINT NOT NULL,
    CONSTRAINT fk_object_client
        FOREIGN KEY (client_id)
            REFERENCES client(id)
            ON UPDATE CASCADE
            ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS equipment (
    id            BIGSERIAL PRIMARY KEY,
    hostname      VARCHAR(255) NOT NULL,
    model         VARCHAR(255) NOT NULL,
    serial_number VARCHAR(100) NOT NULL,
    imei          VARCHAR(20) NOT NULL UNIQUE,
    availability  BOOLEAN DEFAULT TRUE,
    object_id     BIGINT NOT NULL,
    CONSTRAINT fk_equipment_object
        FOREIGN KEY (object_id)
            REFERENCES "object"(id)
            ON UPDATE CASCADE
            ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS sim_card (
    id                BIGSERIAL PRIMARY KEY,
    iccid             VARCHAR(22) NOT NULL UNIQUE,
    def_number        VARCHAR(22) NOT NULL,
    activation_date   TIMESTAMP NOT NULL,
    deactivation_date TIMESTAMP,
    plan              VARCHAR(100),
    geolocation       POINT,
    traffic_mb        BIGINT DEFAULT 0 CHECK (traffic_mb >= 0),
    mo_id             BIGINT NOT NULL,
    equipment_id      BIGINT,
    status_id         BIGINT NOT NULL,
    CONSTRAINT fk_sim_operator
        FOREIGN KEY (mo_id)
            REFERENCES mobile_operator(id)
            ON UPDATE CASCADE
            ON DELETE RESTRICT,
    CONSTRAINT fk_sim_equipment
        FOREIGN KEY (equipment_id)
            REFERENCES equipment(id)
            ON UPDATE CASCADE
            ON DELETE SET NULL,
    CONSTRAINT fk_sim_status
        FOREIGN KEY (status_id)
            REFERENCES sim_status_dictionary(id)
            ON UPDATE CASCADE
            ON DELETE RESTRICT,
    CONSTRAINT chk_deactivation_after_activation
        CHECK (
            deactivation_date IS NULL
                OR deactivation_date >= activation_date
            )
);

CREATE TABLE IF NOT EXISTS "group" (
    id        BIGSERIAL PRIMARY KEY,
    name      VARCHAR(100) NOT NULL UNIQUE,
    is_active BOOLEAN DEFAULT TRUE
);

CREATE TABLE IF NOT EXISTS role (
    id         BIGSERIAL PRIMARY KEY,
    authority  VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS group_role (
    group_id BIGINT NOT NULL,
    role_id  BIGINT NOT NULL,
    PRIMARY KEY (group_id, role_id),
    CONSTRAINT fk_group_role_group
        FOREIGN KEY (group_id)
            REFERENCES "group"(id)
            ON DELETE CASCADE,
    CONSTRAINT fk_group_role_role
        FOREIGN KEY (role_id)
            REFERENCES role(id)
            ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS "user" (
    id            BIGSERIAL PRIMARY KEY,
    login         VARCHAR(100) NOT NULL UNIQUE,
    email         VARCHAR(255) UNIQUE,
    status        VARCHAR(100),
    auth_type     VARCHAR(50) NOT NULL,
    password_hash TEXT,
    group_id      BIGINT NOT NULL,
    CONSTRAINT fk_user_group
        FOREIGN KEY (group_id)
            REFERENCES "group"(id)
            ON DELETE RESTRICT
);
