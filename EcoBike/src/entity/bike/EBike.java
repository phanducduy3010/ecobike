package entity.bike;

import java.sql.SQLException;

public class EBike extends Vehicle{
    private float batteryPercent;

    public EBike() throws SQLException {
    }

    public EBike(String id, int price, int status, String vehicleType) throws SQLException {
        super(id, price, status, vehicleType);
    }
}
