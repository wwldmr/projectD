package com.projectD.multisimbox.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimCardResponse {
    private long id;
    private String mobileOperator;
    private String defNumber;
    private String iccid;
    private String simStatus;
    private String simStatusBdo;

    private String activationDate;
    private String deactivationDate;
    private Long objectNumber;
    private String objectStatus;
    private String objectAddress;
    private String equipmentModel;
}
