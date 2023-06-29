import java.io.Serializable;
import java.util.Date;

public class SinhVien implements Serializable {
    private Integer ma_so;
    private String ho_ten;
    private Date ngay_sinh;
    private String hinh_anh;

    public SinhVien(Integer ma_so, String ho_ten, Date ngay_sinh, String hinh_anh) {
        this.ma_so = ma_so;
        this.ho_ten = ho_ten;
        this.ngay_sinh = ngay_sinh;
        this.hinh_anh = hinh_anh;
    }

    public Integer getMa_so() {
        return ma_so;
    }

    public void setMa_so(Integer ma_so) {
        this.ma_so = ma_so;
    }

    public String getHo_ten() {
        return ho_ten;
    }

    public void setHo_ten(String ho_ten) {
        this.ho_ten = ho_ten;
    }

    public Date getNgay_sinh() {
        return ngay_sinh;
    }

    public void setNgay_sinh(Date ngay_sinh) {
        this.ngay_sinh = ngay_sinh;
    }

    public String getHinh_anh() {
        return hinh_anh;
    }

    public void setHinh_anh(String hinh_anh) {
        this.hinh_anh = hinh_anh;
    }
}
