package Client;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

public class ClientUI extends JFrame {
    private static Socket socket;
    private static int port = 1234;
    private static TableModel tableModel;

    public ClientUI() {
        initComponent();
        init();
    }

    private void initComponent() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Ung dung ban ve xem phim");
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

    private void init() {
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) throws InterruptedException, InvocationTargetException {
        EventQueue.invokeAndWait(() -> {
            ClientUI clientUI = new ClientUI();
            clientUI.setVisible(true);

            try {
                // kết nối đến server
                socket = new Socket("localhost", port);

                // tạo thread cho client socket
                ClientSocketIO clientSocketIO = new ClientSocketIO(socket);
                clientSocketIO.setMainFrame(clientUI);
                new Thread(clientSocketIO).start();

                // input tìm kiếm
                JPanel panelHeader = new JPanel();
                panelHeader.setLayout(new FlowLayout());

                JTextField inputTimKiem = new JTextField();
                inputTimKiem.setText("");
                inputTimKiem.setPreferredSize(new Dimension(200, 30));
                panelHeader.add(inputTimKiem, BorderLayout.PAGE_START);

                // input ngày trong tháng
                JTextField inputNgay = new JTextField();
                inputNgay.setPreferredSize(new Dimension(100, 30));
                inputNgay.setText(getNgay().toString());
                panelHeader.add(inputNgay, BorderLayout.PAGE_START);

                // button tìm kiếm
                JButton btnTimKiem = new JButton("Tìm kiếm");
                panelHeader.add(btnTimKiem, BorderLayout.PAGE_START);
                btnTimKiem.setActionCommand("search");
                btnTimKiem.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // gửi request lấy danh sách vé
                        ArrayList<String> dataSendSearch = new ArrayList<>();
                        dataSendSearch.add(inputTimKiem.getText());
                        dataSendSearch.add(inputNgay.getText());
                        try {
                            ClientSocketIO.sendMsg(socket, dataSendSearch, 1);
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                });

                // thêm jpanel
                clientUI.add(panelHeader, BorderLayout.PAGE_START);


                // hiển thị table cho toàn bộ vé của hệ thống
                tableModel = new TableModel(clientSocketIO.getVeArrayList());
                JTable tableVe = new JTable(tableModel);
                tableVe.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
                    @Override
                    public void valueChanged(ListSelectionEvent e) {
                        if (!e.getValueIsAdjusting() && tableVe.getSelectedRow() != -1) {
                            String maVeMua = tableVe.getValueAt(tableVe.getSelectedRow(), 0).toString();
                            String hoTenKH = JOptionPane.showInputDialog("Nhập họ và tên: ");
                            if(hoTenKH != null) {
                                ArrayList<String> dataSendServer = new ArrayList<>();
                                dataSendServer.add(maVeMua);
                                dataSendServer.add(hoTenKH);
                                try {
                                    ClientSocketIO.sendMsg(socket, dataSendServer, 2);
                                } catch (IOException ex) {
                                    throw new RuntimeException(ex);
                                }
                            }
                        }
                    }
                });
                JScrollPane scrollPane = new JScrollPane(tableVe);
                clientUI.add(scrollPane, BorderLayout.CENTER);
                clientSocketIO.setTableModel(tableModel);

                clientUI.pack();

                // gửi request lấy danh sách vé
                ArrayList<String> dataSend = new ArrayList<>();
                dataSend.add(inputTimKiem.getText());
                dataSend.add(inputNgay.getText());
                ClientSocketIO.sendMsg(socket, dataSend, 1);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public static Integer getNgay() {
        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return localDate.getDayOfMonth();
    }
}
