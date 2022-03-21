package subsystem;

import subsystem.interbank.RentSubsystemController;

import java.sql.SQLException;

public class RentSubsystem implements IRentInterface{
    private RentSubsystemController rentSubsystemController;
    public RentSubsystem() throws SQLException {
        this.rentSubsystemController = new RentSubsystemController();
    }

    @Override
    public boolean requestDeposit(String cardId, float depositFee) throws SQLException {
        return rentSubsystemController.requestDeposit(cardId, depositFee);
        //return false;
    }
}
