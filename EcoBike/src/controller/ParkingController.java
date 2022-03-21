package controller;

import entity.bike.Parking;

import java.sql.SQLException;

public class ParkingController {
    private Parking parking;

    public ParkingController() throws SQLException {
        this.parking = new Parking();
    }

    public int getEmptyDocksByStationId(int stationId) throws SQLException {
        return parking.getEmptyDocksByStationId(stationId);
    }

    public void minusEmptyDocks(int stationId) throws SQLException {
        parking.minusEmptyDocks(stationId);
    }

    public void increaseEmptyDocks(int stationId) throws SQLException {
        parking.increaseEmptyDocks(stationId);
    }

    public Parking getParkingById(int stationId) throws SQLException {
        String tmp = stationId+"";
        return parking.getParkingById(tmp);
    }
}
