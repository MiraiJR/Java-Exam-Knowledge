import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class TableModel extends DefaultTableModel {
    private String[] columnNames;
    private ArrayList<SinhVien> sinhViens;

    public void setSinhViens(ArrayList<SinhVien> sinhViens) {
        this.sinhViens = sinhViens;
    }

    public TableModel(ArrayList<SinhVien> sinhViens) {
        super();
        this.sinhViens = sinhViens;
        this.columnNames = new String[]{"Mã số", "Họ tên", "Ngày sinh", "Hình ảnh"};
    }

    @Override
    public int getRowCount() {
        return columnNames == null ? 0 : sinhViens.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0 -> {
                return this.sinhViens.get(rowIndex).getMa_so();
            }
            case 1 -> {
                return this.sinhViens.get(rowIndex).getHo_ten();
            }
            case 2 -> {
                return this.sinhViens.get(rowIndex).getNgay_sinh();
            }
            case 3 -> {
                return this.sinhViens.get(rowIndex).getHinh_anh();
            }
            default -> {
                return null;
            }
        }
    }

    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }


    public SinhVien getDataAtRow(int row) {
        return sinhViens.get(row);
    }
}
