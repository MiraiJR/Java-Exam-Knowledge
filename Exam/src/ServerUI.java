import Model.Ve;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class ServerUI extends JFrame {
    public ServerUI() {
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
            ServerUI serverUI = new ServerUI();
            serverUI.setVisible(true);
//            mockData();
            Thread mainServerThread = new Thread(new ServerSocketIO());
            mainServerThread.start();
        });
    }


    public static void mockData() {
        ArrayList<Ve> data = new ArrayList<>();
        data.add(new Ve("ABC1", "Bố già lắm chiêu", 1, 28, 9, 20, Long.valueOf(80000)));
        data.add(new Ve("ABC2", "Bố già lắm chiêu", 2, 28, 9, 20, Long.valueOf(80000)));
        data.add(new Ve("ABC3", "Bố già lắm chiêu", 3, 28, 9, 20, Long.valueOf(80000)));
        data.add(new Ve("ABC4", "Bố già lắm chiêu", 4, 28, 9, 20, Long.valueOf(80000)));
        data.add(new Ve("ABC5", "Bố già lắm chiêu", 5, 28, 9, 20, Long.valueOf(80000)));
        data.add(new Ve("ABC6", "Bố già lắm chiêu", 6, 28, 9, 20, Long.valueOf(80000)));
        data.add(new Ve("ABC7", "Bố già lắm chiêu", 7, 28, 9, 20, Long.valueOf(80000)));
        data.add(new Ve("ABC8", "Bố già lắm chiêu", 8, 28, 9, 20, Long.valueOf(80000)));

        ClientSocketIO.updateFile(data);
    }
}
