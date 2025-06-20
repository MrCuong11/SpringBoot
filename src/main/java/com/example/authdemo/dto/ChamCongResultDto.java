package com.example.authdemo.dto;

import java.util.List;

public class ChamCongResultDto {
    private String hoTen;
    private double luongThucTeExcel;
    private double luongTinhToan;
    private double chenhLech;
    private List<NgayLamViecDto> chiTietNgayLam;

    // Getters and Setters
    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public double getLuongThucTeExcel() {
        return luongThucTeExcel;
    }

    public void setLuongThucTeExcel(double luongThucTeExcel) {
        this.luongThucTeExcel = luongThucTeExcel;
    }

    public double getLuongTinhToan() {
        return luongTinhToan;
    }

    public void setLuongTinhToan(double luongTinhToan) {
        this.luongTinhToan = luongTinhToan;
    }

    public double getChenhLech() {
        return chenhLech;
    }

    public void setChenhLech(double chenhLech) {
        this.chenhLech = chenhLech;
    }

    public List<NgayLamViecDto> getChiTietNgayLam() {
        return chiTietNgayLam;
    }

    public void setChiTietNgayLam(List<NgayLamViecDto> chiTietNgayLam) {
        this.chiTietNgayLam = chiTietNgayLam;
    }
} 