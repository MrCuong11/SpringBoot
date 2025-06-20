package com.example.authdemo.service;

import com.example.authdemo.dto.ChamCongResultDto;
import com.example.authdemo.dto.NgayLamViecDto;
import com.example.authdemo.entity.GiaCa;
import com.example.authdemo.entity.NgayLamViec;
import com.example.authdemo.entity.NgayVaCot;
import com.example.authdemo.entity.NhanVien;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ChamCongService {

    private final Map<String, GiaCa> bangGia = new HashMap<>();

    public List<ChamCongResultDto> processChamCongFile() throws Exception {
        // Load file from resources
        InputStream fis = new ClassPathResource("BangCong.xlsx").getInputStream();
        Workbook workbook = new XSSFWorkbook(fis);
        Sheet sheet = workbook.getSheetAt(0);

        // Clear previous data
        bangGia.clear();

        // Step 1: Read price table
        docBangGia(sheet);

        // Step 2: Read date and shift structure
        Row rowNgay = sheet.getRow(3);
        Row rowCa = sheet.getRow(5);
        List<NgayVaCot> ngayVaCotList = new ArrayList<>();
        int colTongLuong = 16;
        int col = colTongLuong + 1;
        while (col <= rowNgay.getLastCellNum()) {
            Cell cell = rowNgay.getCell(col);
            if (cell != null && cell.getCellType() == CellType.NUMERIC) {
                int ngay = (int) cell.getNumericCellValue();
                List<String> cacCa = new ArrayList<>();
                int nextCol = col;
                while (nextCol <= rowNgay.getLastCellNum()) {
                    Cell c = rowNgay.getCell(nextCol);
                    if (c != null && c.getCellType() == CellType.NUMERIC && nextCol != col) break;
                    cacCa.add(getCellValue(rowCa.getCell(nextCol)));
                    nextCol++;
                }
                ngayVaCotList.add(new NgayVaCot(ngay, col, cacCa));
                col = nextCol;
            } else {
                col++;
            }
        }

        // Step 3: Process each employee and build result list
        List<NhanVien> danhSachNhanVien = new ArrayList<>();
        for (int i = 6; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row == null) continue;

            Cell cellName = row.getCell(2);
            Cell cellLuong = row.getCell(16);
            if (cellName == null || cellLuong == null || getCellValue(cellName).isEmpty()) continue;

            String hoTen = cellName.getStringCellValue().trim();
            double luongExcel = getDoubleCell(cellLuong);
            GiaCa giaCa = bangGia.getOrDefault(hoTen, new GiaCa());
            
            NhanVien nv = new NhanVien(hoTen, luongExcel, giaCa);

            for (NgayVaCot ngayCot : ngayVaCotList) {
                NgayLamViec ngayLam = new NgayLamViec(ngayCot.ngay);
                boolean coLam = false;
                for (int j = 0; j < ngayCot.cacCa.size(); j++) {
                    int colIndex = ngayCot.startCol + j;
                    if (colIndex >= row.getLastCellNum()) break;
                    String val = getCellValue(row.getCell(colIndex));
                    if (!val.isEmpty()) {
                        try {
                            double gio = Double.parseDouble(val);
                            String caGoc = ngayCot.cacCa.get(j);
                            if (caGoc.equals("WK-D") || caGoc.equals("WK-N")) caGoc = "WK";
                            ngayLam.congGio(caGoc, gio);
                            coLam = true;
                        } catch (NumberFormatException ignored) {}
                    }
                }
                if (coLam) nv.themNgayLam(ngayLam);
            }
            danhSachNhanVien.add(nv);
        }

        workbook.close();
        fis.close();
        
        // Step 4: Convert NhanVien objects to DTOs
        return mapToDto(danhSachNhanVien);
    }
    
    private void docBangGia(Sheet sheet) {
        int dongGiaCa = 5;
        int cotBatDau = 3;

        Row rowTieuDe = sheet.getRow(dongGiaCa);
        List<String> danhSachCa = new ArrayList<>();
        List<Integer> viTriCotGiaCa = new ArrayList<>();
        
        int col = cotBatDau;
        while (col < rowTieuDe.getLastCellNum()) {
            String val = getCellValue(rowTieuDe.getCell(col));
            if (val.equalsIgnoreCase("$")) {
                List<String> caCon = new ArrayList<>();
                int j = col - 1;
                while (j >= cotBatDau) {
                    String ca = getCellValue(rowTieuDe.getCell(j));
                    if (ca.equalsIgnoreCase("$")) break;
                    caCon.add(0, ca);
                    j--;
                }
                String caGop = (caCon.size() == 1) ? caCon.get(0) : "WK";
                danhSachCa.add(caGop);
                viTriCotGiaCa.add(col);
            }
            col++;
        }

        for (int i = dongGiaCa + 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row == null) continue;

            Cell cellName = row.getCell(2);
            if (cellName == null) continue;

            String hoTen = cellName.getStringCellValue().trim();
            if (hoTen.isEmpty()) continue;

            GiaCa giaCa = new GiaCa();
            for (int j = 0; j < danhSachCa.size(); j++) {
                int colIndex = viTriCotGiaCa.get(j);
                Cell cellGia = row.getCell(colIndex);
                double gia = getDoubleCell(cellGia);
                giaCa.setGiaCa(danhSachCa.get(j), gia);
            }
            bangGia.put(hoTen, giaCa);
        }
    }

    private String getCellValue(Cell cell) {
        if (cell == null) return "";
        if (cell.getCellType() == CellType.STRING) return cell.getStringCellValue().trim();
        if (cell.getCellType() == CellType.NUMERIC) return String.valueOf(cell.getNumericCellValue());
        return "";
    }

    private double getDoubleCell(Cell cell) {
        try {
            if (cell == null) return 0.0;
            if (cell.getCellType() == CellType.NUMERIC) return cell.getNumericCellValue();
            if (cell.getCellType() == CellType.STRING) return Double.parseDouble(cell.getStringCellValue().replace(",", ""));
            if (cell.getCellType() == CellType.FORMULA) {
                if (cell.getCachedFormulaResultType() == CellType.NUMERIC) return cell.getNumericCellValue();
                if (cell.getCachedFormulaResultType() == CellType.STRING)
                    return Double.parseDouble(cell.getStringCellValue().replace(",", ""));
            }
        } catch (Exception e) {
            System.err.println("Cannot read cell data: " + e.getMessage());
        }
        return 0.0;
    }

    private List<ChamCongResultDto> mapToDto(List<NhanVien> danhSachNhanVien) {
        return danhSachNhanVien.stream().map(nv -> {
            ChamCongResultDto dto = new ChamCongResultDto();
            dto.setHoTen(nv.getHoTen());
            dto.setLuongThucTeExcel(nv.getLuongThucTe());
            double luongTinhToan = nv.tinhTongLuong();
            dto.setLuongTinhToan(luongTinhToan);
            dto.setChenhLech(luongTinhToan - nv.getLuongThucTe());

            List<NgayLamViecDto> ngayLamViecDtos = nv.getDsNgayLam().stream().map(ngayLam -> {
                NgayLamViecDto ngayDto = new NgayLamViecDto();
                ngayDto.setNgay(ngayLam.getNgay());
                ngayDto.setTongGio(ngayLam.tongGio());
                ngayDto.setTongTien(ngayLam.tinhTien(nv.getGiaCa()));
                ngayDto.setChiTietGioTheoCa(ngayLam.getGioTheoCa());
                return ngayDto;
            }).collect(Collectors.toList());
            
            dto.setChiTietNgayLam(ngayLamViecDtos);
            return dto;
        }).collect(Collectors.toList());
    }
} 