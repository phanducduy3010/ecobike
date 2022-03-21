package entity.customer;

import entity.bike.Vehicle;
import entity.db.ECOPARK;

import java.sql.*;
import java.util.ArrayList;

public class Customer {

    private Connection conn;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private int id;
    private String code;
    private String name;
    private String cardId;
    private int gender;
    private String birthday;
    private int type;
    private String password;

    public Customer() throws SQLException {
        conn = ECOPARK.getConnection();
    }

    public Customer(int id, String code, String name, String cardId, int gender, String birthday, int type, String password) throws SQLException{
        this.id = id;
        this.code = code;
        this.name = name;
        this.cardId = cardId;
        this.gender = gender;
        this.birthday = birthday;
        this.type = type;
        this.password = password;
    }

    public int login(String code, String pass) throws SQLException {
        String sql = "SELECT * FROM Customer WHERE Code= ? AND Password= ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, code);
        stmt.setString(2, pass);
        ResultSet rs = stmt.executeQuery();
        int idUser = 0;
        if(rs.next()) {
            idUser = rs.getInt(1);
        }
        return idUser;
    }

    public Customer getCustomerById(int id) throws SQLException{
        String sql = "SELECT * FROM Customer WHERE Id="+id;
        Statement stm = ECOPARK.getConnection().createStatement();
        ResultSet res = stm.executeQuery(sql);
        if(res.next()) {
            Customer customer = new Customer();
            customer.setId(res.getInt("Id"));
            customer.setCode(res.getString("Code"));
            customer.setName(res.getString("Name"));
            customer.setCardId(res.getString("CardId"));
            customer.setGender(res.getInt("Gender"));
            customer.setBirthday(res.getString("Birthday"));
            customer.setPassword(res.getString("Password"));
            customer.setType(res.getInt("Type"));
            return customer;
        }
        else {
            return null;
        }
    }

    public Customer getCustomerByCode(String code) throws SQLException{
        String sql = "SELECT * FROM Customer WHERE Code="+code;
        Statement stm = ECOPARK.getConnection().createStatement();
        ResultSet res = stm.executeQuery(sql);
        if(res.next()) {
            Customer customer = new Customer();
            customer.setId(res.getInt("Id"));
            customer.setCode(res.getString("Code"));
            customer.setName(res.getString("Name"));
            customer.setCardId(res.getString("CardId"));
            customer.setGender(res.getInt("Gender"));
            customer.setBirthday(res.getString("Birthday"));
            customer.setPassword(res.getString("Password"));
            customer.setType(res.getInt("Type"));
            return customer;
        }
        else {
            return null;
        }
    }

    /**
     * Sửa thông tin tài khoản
     * @param customer
     * @return
     * @throws SQLException
     */
    public boolean editUserInfor(Customer customer) throws SQLException {
        String sql = "UPDATE Customer SET Name= ?, Gender = ?, Birthday = ? WHERE Id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, customer.getName());
        stmt.setInt(2, customer.getGender());
        stmt.setString(3, customer.getBirthday());
        stmt.setInt(4, customer.getId());
        int rs = stmt.executeUpdate();
        if(rs != 0) {
            return true;
        }
        else {
            return false;
        }
    }

    public int getRoleUser(int id) throws SQLException {
        String sql = "SELECT Type FROM Customer WHERE Id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        int roleUser = 0;
        if(rs.next()) {
            roleUser = rs.getInt(1);
        }
        return roleUser;
    }

    public String getNameUser(int id) throws SQLException {
        String sql = "SELECT Name FROM Customer WHERE Id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        String nameUser = "";
        if(rs.next()) {
            nameUser = rs.getString(1);
        }
        return nameUser;
    }

    public ArrayList getAllCustomer() throws SQLException {
        ArrayList customerList = new ArrayList<>();
        String sql = "SELECT * FROM Customer WHERE Type = 1";      //=========================================
        Statement stm = ECOPARK.getConnection().createStatement();
        ResultSet res = stm.executeQuery(sql);
        while (res.next()) {
            Customer customer = new Customer();
            customer.setId(res.getInt("Id"));
            customer.setCode(res.getString("Code"));
            customer.setName(res.getString("Name"));
            customer.setBirthday(res.getString("Birthday"));
            customer.setGender(res.getInt("Gender"));
            customerList.add(customer);
        }
        return customerList;
    }

    public boolean deleteUserById(int id) throws SQLException {
        String sql = "DELETE FROM Customer WHERE Id = "+id;
        PreparedStatement stmt = conn.prepareStatement(sql);
        int rs = stmt.executeUpdate();
        if(rs != 0) {
            return true;
        }
        else {
            return false;
        }
    }

    public boolean addCustomer(Customer customer) throws SQLException {
        String sql = "INSERT INTO Customer (Code, Name, Password, Gender, Birthday, Type) VALUES (?,?,?,?,?,1)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, customer.getCode());
        stmt.setString(2, customer.getName());
        stmt.setString(3, customer.getPassword());
        stmt.setInt(4, customer.getGender());
        stmt.setString(5, customer.getBirthday());
        int rs = stmt.executeUpdate();
        if(rs != 0) {
            return true;
        }
        else {
            return false;
        }
    }

    public Customer getCustomerByCardId(String cardId) throws SQLException {
        String sql = "SELECT * FROM Customer WHERE CardId = "+cardId+";";
        Statement stm = ECOPARK.getConnection().createStatement();
        ResultSet res = stm.executeQuery(sql);
        if(res.next()){
            Customer customer = new Customer();
            customer.setId(res.getInt("Id"));
            customer.setCode(res.getString("Code"));
            customer.setName(res.getString("Name"));
            customer.setCardId(res.getString("CardId"));
            customer.setGender(res.getInt("Gender"));
            customer.setBirthday(res.getString("Birthday"));
            customer.setPassword(res.getString("Password"));
            customer.setType(res.getInt("Type"));
            return customer;
        }
        return null;
    }
}
