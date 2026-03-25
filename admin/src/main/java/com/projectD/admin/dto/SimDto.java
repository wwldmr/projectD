package com.projectD.admin.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SimDto {
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
