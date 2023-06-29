import Model.Ve;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientSocketIO implements Runnable {
    private static String nameFile = "data.txt";
    private Socket socket;

    public ClientSocketIO(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        while (true) {
            try {
                ObjectInputStream din = new ObjectInputStream(socket.getInputStream());
                int typeMsg = din.readInt(); // nhận loại msg
                System.out.println(socket);
                System.out.println(typeMsg);

                switch (typeMsg) {
                    case 1: // tìm kiếm vé
                        // thông tin người dùng gửi đến dưới dạng array string
                        ArrayList<String> dataClientSearch = (ArrayList<String>) din.readObject();

                        ArrayList<Ve> resultTimKiem = timVe(dataClientSearch.get(0), Integer.valueOf(dataClientSearch.get(1)));

                        // phản hồi cho người dùng
                        ServerSocketIO.reponseClient(socket, resultTimKiem, 1);
                        break;
                    case 2: // mua vé
                        ArrayList<String> dataClientBuy = (ArrayList<String>) din.readObject();

                        Boolean resultMuaVe = muaVe(dataClientBuy.get(0), dataClientBuy.get(1));

                        // phản hồi cho người dùng
                        ServerSocketIO.reponseClient(socket, resultMuaVe, 2);
                        break;
                    default:
                        break;
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private ArrayList<Ve> timVe(String tenPhim, Integer ngayChieu) throws FileNotFoundException {
        ArrayList<Ve> result = new ArrayList<>();
        ArrayList<Ve> data = readFile(); // đọc file lấy dữ liệu
        for (int i = 0; i < data.size(); i++) {
            // kiểm tra tên phim và ngày chiếu có khớp với dữ liệu user tìm
            if(tenPhim.equals("")) {
                if(data.get(i).getNgay_chieu() - ngayChieu == 0) {
                    result.add(data.get(i));
                }
            } else {
                if (data.get(i).getTen_film().toLowerCase().contains(tenPhim.toLowerCase()) && data.get(i).getNgay_chieu() - ngayChieu == 0) {
                    result.add(data.get(i));
                }
            }
        }

        return result;
    }

    private Boolean muaVe(String maVe, String hoTen) throws FileNotFoundException {
        Boolean result = false;
        ArrayList<Ve> data = readFile();

        for (int i = 0; i < data.size(); i++) {
            // kiểm tra vé đã có người mua hay chưa
            if (data.get(i).getMa_ve().equals(maVe)) {
                // vé chưa ai mua
                if (data.get(i).getNguoi_mua().equals("")) {
                    data.get(i).setNguoi_mua(hoTen);
                    result = true;
                } else { // vé đã có người mua
                    result = false;
                }

                break;
            }
        }

        updateFile(data);

        return result;
    }


    public static ArrayList<Ve> readFile() throws FileNotFoundException {
        ArrayList<Ve> result = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(nameFile)) {
            if (fis.available() <= 0) {
                return result;
            }

            ObjectInputStream ois = new ObjectInputStream(fis);
            result = (ArrayList<Ve>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    public static void updateFile(ArrayList<Ve> data) {
//        for(int i = 0; i < data.size(); i++) {
//            System.out.println(data.get(i).getMa_ve());
//        }
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(nameFile))) {
            oos.writeObject(data);

            oos.flush();
            oos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
