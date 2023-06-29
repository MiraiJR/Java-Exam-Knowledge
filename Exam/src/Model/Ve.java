package Model;

import java.io.Serializable;

public class Ve implements Serializable {
    private String ma_ve;
    private String ten_film;
    private Integer vi_tri_ngoi;
    private Integer ngay_chieu;
    private Integer gio_chieu;
    private Integer phut_chieu;
    private Long gia;
    private String nguoi_mua;

    public Ve(String ma_ve, String ten_film, Integer vi_tri_ngoi, Integer ngay_chieu, Integer gio_chieu, Integer phut_chieu, Long gia) {
        this.ma_ve = ma_ve;
        this.ten_film = ten_film;
        this.vi_tri_ngoi = vi_tri_ngoi;
        this.ngay_chieu = ngay_chieu;
        this.gio_chieu = gio_chieu;
        this.phut_chieu = phut_chieu;
        this.gia = gia;
        this.nguoi_mua = "";
    }

    public String getMa_ve() {
        return ma_ve;
    }

    public void setMa_ve(String ma_ve) {
        this.ma_ve = ma_ve;
    }

    public String getTen_film() {
        return ten_film;
    }

    public void setTen_film(String ten_film) {
        this.ten_film = ten_film;
    }

    public Integer getVi_tri_ngoi() {
        return vi_tri_ngoi;
    }

    public void setVi_tri_ngoi(Integer vi_tri_ngoi) {
        this.vi_tri_ngoi = vi_tri_ngoi;
    }

    public Integer getNgay_chieu() {
        return ngay_chieu;
    }

    public void setNgay_chieu(Integer ngay_chieu) {
        this.ngay_chieu = ngay_chieu;
    }

    public Integer getGio_chieu() {
        return gio_chieu;
    }

    public void setGio_chieu(Integer gio_chieu) {
        this.gio_chieu = gio_chieu;
    }

    public Integer getPhut_chieu() {
        return phut_chieu;
    }

    public void setPhut_chieu(Integer phut_chieu) {
        this.phut_chieu = phut_chieu;
    }

    public Long getGia() {
        return gia;
    }

    public void setGia(Long gia) {
        this.gia = gia;
    }

    public String getNguoi_mua() {
        return nguoi_mua;
    }

    public void setNguoi_mua(String nguoi_mua) {
        this.nguoi_mua = nguoi_mua;
    }
}
