import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnection {
    private String url = "jdbc:mysql://localhost:3306/qlsv";
    private String username = "root";
    private String password = "1234";
    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public MySQLConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
