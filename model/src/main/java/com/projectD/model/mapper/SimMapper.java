package com.projectD.model.mapper;

import com.projectD.model.dto.SimDto;
import com.projectD.model.entity.SimCard;

public class SimMapper {
    public static SimDto mapToSimDto(SimCard simCard) {
        SimDto simDto = new SimDto();

        simDto.setId(simCard.getId());
        simDto.setIccid(simCard.getIccid());
        simDto.setStatus(simDto.getStatus());
        //getName()
        simDto.setOperator(simDto.getOperator());
        simDto.setImei(simCard.getImei());

        return simDto;
    }

    public static SimCard mapToSimCard(SimDto simDto) {
        SimCard simCard = new SimCard();

        simCard.setId(simDto.getId());
        simCard.setIccid(simDto.getIccid());
        simCard.setImei(simDto.getImei());

        return simCard;
    }
}
