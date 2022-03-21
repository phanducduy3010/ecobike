package entity.card;

import entity.db.ECOPARK;

import java.sql.*;

public class Card {

    protected Statement stm;
    private int id;
    private String password;
    private long balance;

    public Card() throws SQLException {
        stm = ECOPARK.getConnection().createStatement();
    }

    public Card (int id, String password) throws SQLException{
        this.id = id;
        this.password =password;
    }
    // getter and setter
    public Statement getStm() {
        return stm;
    }
    public void setStm(Statement stm) {
        this.stm = stm;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public long getBalance() {
        return balance;
    }
    public void setBalance(long balance) {
        this.balance = balance;
    }
    public int getId() {
        return id;
    }

    public boolean updateAfterPay(Card card) throws SQLException {
        String sql = "UPDATE Card SET  BALANCE =? WHERE Id = ?";
        Connection conn  = ECOPARK.getConnection();
        PreparedStatement stm = conn.prepareStatement(sql);
        stm.setString(1, String.valueOf(card.getBalance()));
        stm.setString(2, String.valueOf(card.getId()));
        int rs = stm.executeUpdate();
        if(rs != 0) {
            return true;
        }
        else {
            return false;
        }
    }

    public Card getCard(String cardId) throws SQLException {
        String sql = "SELECT * FROM Card WHERE Id="+ cardId;
        Statement stm = ECOPARK.getConnection().createStatement();
        ResultSet res = stm.executeQuery(sql);
        if(res.next()) {
            Card card = new Card();
            card.setId(res.getInt("Id"));
            card.setPassword(res.getString("Password"));
            card.setBalance(res.getLong("Balance"));
            return card;
        }
        return null;
    }

    //Lay thong tin so du tai khoan
    public float getBalance( String cardId) throws SQLException {
        String sql = "SELECT * FROM Card WHERE Id="+ cardId+";";
        Statement stm = ECOPARK.getConnection().createStatement();
        ResultSet res = stm.executeQuery(sql);
        return res.getFloat("Balance");
    }

    public boolean requestDeposit( String cardId, float depositFee) throws SQLException {
        float balance = getBalance(cardId);
        System.out.println("so dư: "+balance);
        System.out.println("phí cọc: "+depositFee);
        if (depositFee > balance){
            System.out.println("Khong du so du");
            return false;
        }

        //cap nhat lai so du
        float remainder = balance - depositFee;
        String sql = "UPDATE Card SET Balance="+remainder+" WHERE Id="+cardId+";";
        Statement stm = ECOPARK.getConnection().createStatement();
        stm.executeUpdate(sql);

        return true;
    }

    public boolean checkIdCard(String cardId) throws SQLException {
        String sql = "SELECT * FROM Card WHERE Id='"+cardId+"';";
        Statement stm = ECOPARK.getConnection().createStatement();
        ResultSet res = stm.executeQuery(sql);
        if(res.next()) {

            return true;
        }
        return false;
    }



}
