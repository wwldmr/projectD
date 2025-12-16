package com.projectD.model.dto;

import com.projectD.model.enums.SimStatus;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SimDto {
    private long id;
    private String iccid;
    private SimStatus status;
    private String operator;
    private String imei;
}
