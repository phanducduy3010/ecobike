package entity.bike;

import java.sql.SQLException;

public class Bike extends Vehicle {

    public Bike() throws SQLException {
        super();
    }

    public Bike(String id, int price, int status, String type) throws SQLException {
        super(id, price, status, type);
    }


}
