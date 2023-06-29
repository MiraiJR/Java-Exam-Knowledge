package Client;

import Model.Ve;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ClientSocketIO implements Runnable {
    private Socket socket;
    private ArrayList<Ve> veArrayList;
    private Boolean responseMuaVe;
    private TableModel tableModel;
    private ClientUI mainFrame;

    public void setTableModel(TableModel tableModel) {
        this.tableModel = tableModel;
    }

    public void setMainFrame(ClientUI mainFrame) {
        this.mainFrame = mainFrame;
    }

    public ClientSocketIO(Socket socket) {
        this.socket = socket;
        this.veArrayList = new ArrayList<>();
        this.responseMuaVe = false;
        this.tableModel = tableModel;
    }

    // gửi msg -> server
    public static void sendMsg(Socket socket, Object data, int type) throws IOException {
        ObjectOutputStream dout = new ObjectOutputStream(socket.getOutputStream());
        dout.writeInt(type);
        dout.writeObject(data);
        dout.flush();
    }

    public ArrayList<Ve> getVeArrayList() {
        return veArrayList;
    }

    public Boolean getResponse() {
        return responseMuaVe;
    }

    @Override
    public void run() {
        while (true) {
            try {
                ObjectInputStream din = new ObjectInputStream(socket.getInputStream());
                int typeMsg = din.readInt();

                switch (typeMsg) {
                    case 1:
                        this.veArrayList = (ArrayList<Ve>) din.readObject();
                        tableModel.setVeArrayList(this.veArrayList);
                        tableModel.fireTableDataChanged();
                        break;
                    case 2:
                        this.responseMuaVe = (Boolean) din.readObject();
                        if(responseMuaVe) {
                            JOptionPane.showMessageDialog(this.mainFrame, "Mua vé thành công!");
                        } else {
                            JOptionPane.showMessageDialog(this.mainFrame, "Vé đã có người mua! Mua vé thất bại!");
                        }
                        break;
                    default:
                        break;
                }

            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
