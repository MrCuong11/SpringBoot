package com.example.authdemo.entity;

import java.util.ArrayList;
import java.util.List;

public class NhanVien {
    private final String hoTen;
    private final double luongThucTe;
    private final GiaCa giaCa;
    private final List<NgayLamViec> dsNgayLam = new ArrayList<>();

    public NhanVien(String hoTen, double luongThucTe, GiaCa giaCa) {
        this.hoTen = hoTen;
        this.luongThucTe = luongThucTe;
        this.giaCa = giaCa;
    }

    public void themNgayLam(NgayLamViec ngay) {
        dsNgayLam.add(ngay);
    }

    public double tinhTongLuong() {
        return dsNgayLam.stream().mapToDouble(ngay -> ngay.tinhTien(giaCa)).sum();
    }
    
    public String getHoTen() {
        return hoTen;
    }

    public double getLuongThucTe() {
        return luongThucTe;
    }

    public List<NgayLamViec> getDsNgayLam() {
        return dsNgayLam;
    }

    public GiaCa getGiaCa() {
        return giaCa;
    }
} 