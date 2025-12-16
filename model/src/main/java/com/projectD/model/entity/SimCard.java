package com.projectD.model.entity;

import com.projectD.model.enums.SimStatus;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "sim_cards")
public class SimCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "iccid", nullable = false, unique = true)
    private String iccid;

    @Column(name = "imei")
    private String imei;

    @Column(name = "status_id")
    private SimStatus status;
}
