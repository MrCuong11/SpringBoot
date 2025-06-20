package com.example.authdemo.entity;

import java.util.HashMap;
import java.util.Map;

public class GiaCa {
    private final Map<String, Double> donGiaCa = new HashMap<>();

    public void setGiaCa(String ca, double gia) {
        donGiaCa.put(ca, gia);
    }

    public double getGia(String ca) {
        return donGiaCa.getOrDefault(ca, 0.0);
    }
} 