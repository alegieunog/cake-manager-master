package com.waracle.cakemgr.service;

import com.waracle.cakemgr.dto.Cake;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface CakeService {

    static Map<String, Cake> cakeMap = new HashMap<>();

    List<Cake> getCakes();

    Cake addCake(Cake cake);

    Cake getCake(String title);

    String getCakesAsJson();
}
