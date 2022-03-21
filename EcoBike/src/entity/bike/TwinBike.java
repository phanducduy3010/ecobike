package entity.bike;

import java.sql.SQLException;

public class TwinBike extends Vehicle{
    public TwinBike() throws SQLException {
    }

    public TwinBike(String id, int price, int status, String vehicleType) throws SQLException {
        super(id, price, status, vehicleType);
    }
}
