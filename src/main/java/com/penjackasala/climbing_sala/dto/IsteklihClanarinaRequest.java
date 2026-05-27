package com.penjackasala.climbing_sala.dto;

import com.penjackasala.climbing_sala.enums.FilterTip;

import java.beans.ConstructorProperties;

public record IsteklihClanarinaRequest(
        FilterTip filterTip,
        Integer brojiDana
) {
    @ConstructorProperties({"filterTip", "brojiDana"})
    public IsteklihClanarinaRequest {}
}