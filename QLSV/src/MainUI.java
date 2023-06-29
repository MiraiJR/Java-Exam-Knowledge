
import org.jdatepicker.impl.DateComponentFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

public class MainUI extends JFrame {

    public MainUI() {
        initComponent();
        init();
    }

    public void initComponent() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("QLSV");
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 1080, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 720, Short.MAX_VALUE)
        );

        pack();
    }

    public void init() {
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) throws InterruptedException, InvocationTargetException {
        EventQueue.invokeAndWait(() -> {
            MainUI mainUI = new MainUI();
            mainUI.setVisible(true);

            // kết nối mysql server
            MySQLConnection mySQLConnection = new MySQLConnection();

            // lấy danh sách sinh viên
            ArrayList<SinhVien> sinhViens = SinhVienDAO.getAll(mySQLConnection);

            // tạo table
            TableModel tableModel = new TableModel(sinhViens);
            JTable tableSinhVien = new JTable(tableModel);
            JScrollPane scrollPane = new JScrollPane(tableSinhVien);

            // tạo form thêm sinh viên
            JPanel panelForm = new JPanel(new GridLayout(3,2));
            JTextField inputHoTen = new JTextField();
            inputHoTen.setPreferredSize(new Dimension(100, 30));
            JLabel labelHoTen = new JLabel("Họ và tên: ");
            panelForm.add(labelHoTen);
            panelForm.add(inputHoTen);
            panelForm.add(new JLabel("Ngày sinh:"));
            // tạo input đẻ chọn ngày tháng năm sinh
            UtilDateModel model = new UtilDateModel();
            Properties p = new Properties();
            p.put("text.today", "Hôm nay");
            p.put("text.month", "Tháng");
            p.put("text.year", "Năm");
            JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
            JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateComponentFormatter());
            panelForm.add(datePicker);

            // button thêm sinh viên
            JButton btnThem = new JButton("Thêm sinh viên");
            btnThem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String hoTen = inputHoTen.getText();
                    Date ngaySinh = (Date) datePicker.getModel().getValue();

                    if(hoTen.equals("") || ngaySinh == null) {
                        JOptionPane.showMessageDialog(mainUI, "Vui lòng điền đầy đủ thông tin!");
                    } else {
                        Integer maSo = SinhVienDAO.generatedMaSo(mySQLConnection);
                        SinhVien sinhVien = new SinhVien(maSo, hoTen, ngaySinh, null);
                        SinhVienDAO.create(sinhVien, mySQLConnection);
                        tableModel.setSinhViens(SinhVienDAO.getAll(mySQLConnection));
                        tableModel.fireTableDataChanged();
                    }
                }
            });
            panelForm.add(btnThem);

            // thêm vào mainUI
            mainUI.add(panelForm,BorderLayout.PAGE_START);
            mainUI.add(scrollPane, BorderLayout.CENTER);
        });
    }
}