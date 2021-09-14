package com.it.phamthiminhthu.timetable.entities;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "subject_time_table")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder


public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "ky")
    private int ky;

    @Column(name = "vien" )
    private String vien;

    @Column(name = "ma_hoc_phan")
    private String maHp;

    @Column(name = "khoi_luong")
    private String khoiLuong;

    @Column(name = "ma_lop")
    private int maLop;

    @Column(name = "ma_lop_kem")
    private int maLopKem;

    @Column(name = "ten_hoc_phan" )
    private String tenHocPhan;

    @Column(name = "ghi_chu")
    private String ghiChu;

    @Column(name = "so_luong_max")
    private int soLuongMax;

    @Column(name = "thu")
    private int thu;

    @Column(name = "thoi_gian")
    private String thoiGian;

    @Column(name = "phong")
    private String phong;

    @Column(name = "loai_lop")
    private String loaiLop;

    @Column(name = "bat_dau")
    private int BD;

    @Column(name = "ket_thuc")
    private int KT;

    @Column(name = "buoi")
    private String buoi;

    @Column(name = "tuan")
    private String tuan;

    @Column(name = "so_buoi")
    private int soBuoi;
















}
