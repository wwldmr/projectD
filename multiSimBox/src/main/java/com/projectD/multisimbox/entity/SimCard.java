package com.projectD.multisimbox.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "sim_card")
public class SimCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "iccid", nullable = false, unique = true)
    private String iccid;

    @Column(name = "def_number", nullable = false)
    private String defNumber;

    @Column(name = "activation_date", nullable = false)
    private LocalDateTime activationDate;

    @Column(name = "deactivation_date", nullable = false)
    private LocalDateTime deactivationDate;

    @Column(name = "plan")
    private String plan;

    @Column(name = "traffic_mb")
    private Long trafficMb;

    @Column(name = "mo_id", nullable = false)
    private Long mobileOperatorId;

    @Column(name = "equipment_id")
    private Long equipmentId;

    @Column(name = "status_id", nullable = false)
    private Long statusId;
}
