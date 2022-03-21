package entity.db;

import java.sql.Connection;
import java.sql.DriverManager;
//import utils.*;

public class ECOPARK {

	private static Connection connect;

    public static Connection getConnection() {
        if (connect != null) return connect;
        try {
			Class.forName("org.sqlite.JDBC");
            String url = "jdbc:sqlite:./assets/db/ecobike.db";
            connect = DriverManager.getConnection(url);
            System.out.println("Connect database successfully");
        } catch (Exception e) {
            System.out.println("Error when connect to database");
        } 
        return connect;
    }
    

    public static void main(String[] args) {
    	ECOPARK.getConnection();
    }
}
