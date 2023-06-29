package Client;

import Model.Ve;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class TableModel extends DefaultTableModel {
    private String[] columnNames;
    private ArrayList<Ve> veArrayList;

    public void setVeArrayList(ArrayList<Ve> veArrayList) {
        this.veArrayList = veArrayList;
    }

    public TableModel(ArrayList<Ve> veArrayList) {
        super();
        this.veArrayList = veArrayList;
        this.columnNames = new String[]{"Mã vé", "Tên film", "Vị trí ngồi", "Ngày chiếu", "Giờ chiếu", "Phút chiếu", "Giá"};
    }

    @Override
    public int getRowCount() {
        return columnNames == null ? 0 : veArrayList.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0 -> {
                return this.veArrayList.get(rowIndex).getMa_ve();
            }
            case 1 -> {
                return this.veArrayList.get(rowIndex).getTen_film();
            }
            case 2 -> {
                return this.veArrayList.get(rowIndex).getVi_tri_ngoi();
            }
            case 3 -> {
                return this.veArrayList.get(rowIndex).getNgay_chieu();
            }
            case 4 -> {
                return this.veArrayList.get(rowIndex).getGio_chieu();
            }
            case 5 -> {
                return this.veArrayList.get(rowIndex).getPhut_chieu();
            }
            case 6 -> {
                return this.veArrayList.get(rowIndex).getGia();
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


    public Ve getDataAtRow(int row) {
        return veArrayList.get(row);
    }
}
