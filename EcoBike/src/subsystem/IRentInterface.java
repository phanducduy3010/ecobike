package subsystem;

import java.sql.SQLException;

public interface IRentInterface {
    public abstract boolean requestDeposit(String cardId, float depositFee) throws SQLException;
}
