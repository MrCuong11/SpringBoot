package com.example.authdemo.dto;

import java.util.Map;

public class NgayLamViecDto {
    private int ngay;
    private double tongGio;
    private double tongTien;
    private Map<String, Double> chiTietGioTheoCa;

    // Getters and setters
    public int getNgay() {
        return ngay;
    }

    public void setNgay(int ngay) {
        this.ngay = ngay;
    }

    public double getTongGio() {
        return tongGio;
    }

    public void setTongGio(double tongGio) {
        this.tongGio = tongGio;
    }

    public double getTongTien() {
        return tongTien;
    }

    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }

    public Map<String, Double> getChiTietGioTheoCa() {
        return chiTietGioTheoCa;
    }

    public void setChiTietGioTheoCa(Map<String, Double> chiTietGioTheoCa) {
        this.chiTietGioTheoCa = chiTietGioTheoCa;
    }
} 