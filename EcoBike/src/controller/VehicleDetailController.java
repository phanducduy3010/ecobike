package controller;

import entity.bike.Vehicle;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class VehicleDetailController {

    private Vehicle vehicle;

    public VehicleDetailController() throws SQLException, IOException {
        this.vehicle = new Vehicle();
    }

    public int deleteVehicle(String code)throws SQLException, IOException {
        int res = vehicle.deleteVehicle(code);
        return res;
    }

    public int updateInfoVehicle(Vehicle newVehicle) throws SQLException, IOException {
        int res = vehicle.updateVehicle(newVehicle);
        return res;
    }

    public int addVehicle(Vehicle vhc) throws SQLException {
        int res = vehicle.createVehicle(vhc);
        return res;
    }

    //Lấy toàn bộ xe theo id của bãi xe
    public static ArrayList getVehiclesByStationId(int stationId) throws SQLException {
        return new Vehicle().getVehiclesByStationId(stationId);
    }

    //Lấy toàn bộ mã của xe
    public static ArrayList<String> getAllVehicleCodes() throws SQLException {
        return new Vehicle().getAllVehicleCodes();
    }
}
