package entity.rental;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

import entity.bike.Bike;
import entity.bike.Vehicle;
import entity.db.ECOPARK;
import javafx.scene.control.Alert;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author nguyen
 * @create_date 04/01/2022
 */
public class Rental {


  public static String pattern = "yyyy-MM-dd hh:mm";
  public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

  private int id;
  private int userId;
  private int bikeId;
  private Date startTime;
  private Date endTime;
  private long cost;
  private String startStation;
  private String endStation;
  private String status;

  public Rental() {
  }

  public Rental(int id, int userId, int bikeId, Date startTime, Date endTime, long cost,
                String startStation, String endStation, String status) {
    this.id = id;
    this.userId = userId;
    this.bikeId = bikeId;
    this.startTime = startTime;
    this.endTime = endTime;
    this.cost = cost;
    this.startStation = startStation;
    this.endStation = endStation;
    this.status = status;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  public int getBikeId() {
    return bikeId;
  }

  public void setBikeId(int bikeId) {
    this.bikeId = bikeId;
  }

  public Date getStartTime() {
    return startTime;
  }

  public void setStartTime(Date startTime) {
    this.startTime = startTime;
  }

  public Date getEndTime() {
    return endTime;
  }

  public void setEndTime(Date endTime) {
    this.endTime = endTime;
  }

  public long getCost() {
    return cost;
  }

  public void setCost(long cost) {
    this.cost = cost;
  }

  public String getStartStation() {
    return startStation;
  }

  public void setStartStation(String startStation) {
    this.startStation = startStation;
  }

  public String getEndStation() {
    return endStation;
  }

  public void setEndStation(String endStation) {
    this.endStation = endStation;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public Rental getRentalById(int id){

    return null;
  }


  //getList...
  public Rental getRentalByVehicleId(String id) throws SQLException, ParseException {
    String sql = "SELECT * FROM Rental WHERE  VehicleId = " + id + " AND STATUS = 0";
    Statement stm = ECOPARK.getConnection().createStatement();
    ResultSet res = stm.executeQuery(sql);
    if(res.next()) {
      Rental rental = new Rental();
      rental.setId(res.getInt("Id"));
      rental.setBikeId(res.getInt("VehicleId"));
      rental.setUserId(res.getInt("CustomerId"));
      rental.setStartStation(res.getString("StartStation"));
      rental.setEndStation(res.getString("FinishStation"));
      if(res.getString("StartTime").length() ==0  || res.getString("StartTime") != null) {
        try {
          rental.setStartTime(simpleDateFormat.parse(res.getString("StartTime")));
        } catch (ParseException e){
          e.printStackTrace();
        }
      }

      return rental;
    }

    return null;
  }

  public void updateRentalInfo(long cost, String finishTime, String finishStation) throws SQLException {
    String sql = "UPDATE Rental SET  cost="+ cost +", finishTime = '"+ finishTime
            +"', STATUS = "+ 1 +", finishStation = '"+ finishStation+"' WHERE id ='"+this.getId()+"';";
    Statement stm = ECOPARK.getConnection().createStatement();
    stm.executeUpdate(sql);
  }

  public Rental getBikeRenting(int userId) throws SQLException {
    String sql = "SELECT * FROM Rental WHERE CUSTOMERID = " + userId + " AND STATUS = 0";
    Statement stm = ECOPARK.getConnection().createStatement();
    ResultSet res = stm.executeQuery(sql);
    if(res.next()) {
      Rental rental = new Rental();
      rental.setId(res.getInt("Id"));
      rental.setBikeId(res.getInt("VehicleId"));
      rental.setUserId(res.getInt("CustomerId"));
      rental.setStartStation(res.getString("StartStation"));
      rental.setEndStation(res.getString("FinishStation"));
      if(res.getString("StartTime").length() ==0  || res.getString("StartTime") != null) {
        try {
          rental.setStartTime(simpleDateFormat.parse(res.getString("StartTime")));
        } catch (ParseException e){
          e.printStackTrace();
        }
      }


      return rental;
    }

    Alert alert = new Alert(Alert.AlertType.WARNING);
    alert.setTitle("Thông báo");
    alert.setHeaderText("Không có xe nào đang được thuê");
    alert.setContentText("Không có xe nào đang được thuê");
    alert.show();
    return null;
  }

  public void addRental(String vehicleId, int customerId, String startStation, String cost, int cardId) throws SQLException {
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    Date date = new Date();
    String timeToRent = formatter.format(date);

    String sql = "INSERT INTO Rental (VehicleId, CustomerId, StartTime, StartStation," +
            "Cost, Status, CardId) VALUES ("+vehicleId+","+ customerId+",'"+timeToRent+"','"+ startStation+"',"+cost+","+"0"+","+cardId+");";
    Statement stm = ECOPARK.getConnection().createStatement();
    stm.executeUpdate(sql);
  }

  /**
   * Phương thức kiểm tra thẻ đã dùng để thuê một xe khác hay chưa
   * @param cardId : Mã của thẻ muốn kiểm tra
   * @throws SQLException
   * @return : giá trị kiểm tra đúng, sai
   */
  public boolean checkCardAvailability(int cardId) throws SQLException {
    String sql = "SELECT * FROM Rental WHERE  CardId = " + cardId+ ";";
    Statement stm = ECOPARK.getConnection().createStatement();
    ResultSet res = stm.executeQuery(sql);
    if(res.next()){
      return false;
    } else{
      return true;
    }
  }
}
