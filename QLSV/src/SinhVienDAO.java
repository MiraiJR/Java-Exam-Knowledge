import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SinhVienDAO {
    public static void create(SinhVien sinhVien, MySQLConnection connection) {
        String sql_query = "INSERT INTO sinhvien (ma_so, ho_ten, ngay_sinh, hinh_anh) VALUES (?,?,?,?)";

        try (PreparedStatement ps = connection.getConnection().prepareStatement(sql_query)) {
            ps.setInt(1, sinhVien.getMa_so());
            ps.setString(2, sinhVien.getHo_ten());
            ps.setDate(3, new Date(sinhVien.getNgay_sinh().getTime()));
            ps.setString(4, sinhVien.getHinh_anh());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static SinhVien getOne(Integer maSo, MySQLConnection connection) {
        String sql_query = "SELECT * FROM sinhvien WHERE ma_so = ?";

        try (PreparedStatement ps = connection.getConnection().prepareStatement(sql_query)) {
            ps.setInt(1, maSo);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                SinhVien sinhVien = new SinhVien(rs.getInt("ma_so"), rs.getString("ho_ten"), (java.util.Date) rs.getDate("ngay_sinh"), rs.getString("hinh_anh"));
                return sinhVien;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    public static ArrayList<SinhVien> getAll(MySQLConnection connection) {
        String sql_query = "SELECT * FROM sinhvien ORDER BY ma_so";
        ArrayList<SinhVien> result = new ArrayList<>();

        try (PreparedStatement ps = connection.getConnection().prepareStatement(sql_query)) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                SinhVien sinhVien = new SinhVien(rs.getInt("ma_so"), rs.getString("ho_ten"), (java.util.Date) rs.getDate("ngay_sinh"), rs.getString("hinh_anh"));
                result.add(sinhVien);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    public static Integer generatedMaSo(MySQLConnection connection) {
        ArrayList<SinhVien> listSV = getAll(connection);
        if (listSV.size() == 0) {
            return 1;
        } else {
            return listSV.get(listSV.size() - 1).getMa_so() + 1;
        }
    }
}
