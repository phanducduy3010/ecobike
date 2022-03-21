package controller;

import entity.bike.Bike;
import entity.bike.Vehicle;
import entity.card.Card;
import entity.rental.Rental;
import subsystem.IRentInterface;
import subsystem.RentSubsystem;
import views.screen.bike.BikeScreen;
import views.screen.bike.VehicleDetailViewScreen;
import views.screen.payment.interfacefee.DepositFeeCalculation;
import views.screen.payment.interfacefee.IDepositFee;

import java.io.IOException;
import java.sql.SQLException;

public class   RentControllerSystem {

    private Vehicle vehicle;
    private BikeScreen bikeScreen;
    private RentSubsystem rentSubsystem;
    private IRentInterface iRentInterface;
    private Card card;
    private Rental rental;

    public RentControllerSystem() throws SQLException, IOException {
        this.vehicle = new Vehicle();
        this.rentSubsystem = new RentSubsystem();
        this.card= new Card();
        this.rental= new Rental();

    }
    public Vehicle searchBike(String vehicleCode) throws SQLException, IOException {
        Vehicle res = vehicle.getVehicleByCode(vehicleCode);
        return  res;
    };

    public float calculateDepositFee(String vehicleType){
        IDepositFee iDepositFee = new DepositFeeCalculation();
        return iDepositFee.calculateDepositFee(vehicleType);
    }


    public boolean checkIdCard(String cardId) throws SQLException {
        return card.checkIdCard(cardId);
    }

    public boolean processDeposit(String cardID, float depositFee, Vehicle bike) throws SQLException {
        iRentInterface =  new RentSubsystem();
        boolean result = iRentInterface.requestDeposit(cardID, depositFee);
        if(result) {
            this.vehicle.updateStatus(bike);
        }
        return result;

    }

    public boolean checkVehicleAvailability(String code) throws SQLException {
        return vehicle.checkVehicleAvailability(code);
    }

    public void addRental(String vehicleId, int customerId, String startStation, String cost, int cardId) throws SQLException {
        rental.addRental(vehicleId, customerId, startStation, cost, cardId);
    }

    /**
     * Phương thức kiểm tra thẻ đã dùng để thuê một xe khác hay chưa
     * @param cardId : Mã của thẻ muốn kiểm tra
     * @throws SQLException
     * @return : giá trị kiểm tra đúng, sai
     */
    public boolean checkCardAvailability(int cardId) throws SQLException {
        return rental.checkCardAvailability(cardId);

    }
}

