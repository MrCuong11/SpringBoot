package com.example.authdemo.entity;

import java.util.List;

public class NgayVaCot {
    public int ngay;
    public int startCol;
    public List<String> cacCa;

    public NgayVaCot(int ngay, int startCol, List<String> cacCa) {
        this.ngay = ngay;
        this.startCol = startCol;
        this.cacCa = cacCa;
    }
} 