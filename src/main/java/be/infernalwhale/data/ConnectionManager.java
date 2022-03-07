package be.infernalwhale.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
    private final static String CONNECTION_STRING = "jdbc:mysql://192.168.0.42:33062/testdb";
    private final static String USER = "testuser";
    private final static String PWD = "intec-123";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(CONNECTION_STRING, USER, PWD);
    }
}
