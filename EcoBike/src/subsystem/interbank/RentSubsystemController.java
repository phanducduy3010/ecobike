package subsystem.interbank;

import entity.card.Card;

import java.sql.SQLException;

public class RentSubsystemController {
    private Card card;
    public RentSubsystemController() throws SQLException {
        this.card = new Card();
    }
    public boolean requestDeposit(String cardID, float depositFee) throws SQLException {
        return card.requestDeposit(cardID, depositFee);
    }
}
