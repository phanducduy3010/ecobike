package views.screen.bill;

import config.ServiceConfig;
import config.ServiceConfig.ScreenTittle;
import config.VehicleTypeConfig;
import controller.CustomerController;
import entity.bike.Bike;
import entity.bike.Parking;
import entity.bike.Vehicle;
import entity.card.Card;
import entity.customer.Customer;
import entity.db.ECOPARK;
import entity.rental.Rental;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import utils.Configs;
import views.screen.BaseScreenHandler;
import views.screen.home.HomeScreen;

/**
 * @author nguyen
 * @create_date 06/01/2022
 */
public class Bill extends BaseScreenHandler {

  public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
      ServiceConfig.DATE_TIME_PATTERN);

  @FXML
  private ImageView logoHome;

  @FXML
  private ImageView logoUser;

  @FXML
  private TextField name;

  @FXML
  private TextField start;

  @FXML
  private TextField end;

  @FXML
  private TextField bike;

  @FXML
  private TextField cost;

  @FXML
  private TextField price;

  @FXML
  private Button pay;

  @FXML
  private Label lbUser;

  private int userId;

  private String bikeCode;

  private int stationId;

  private Vehicle vehicleInstance = new Vehicle();

  private Rental rentalInstance = new Rental();

  private Card card = new Card();

  private Parking parking = new Parking();

  private CustomerController customerController = new CustomerController();

  public Bill(Stage stage, String screenPath, int userId, String bikeCode, int stationId)
      throws IOException, SQLException, ParseException {
    super(stage, screenPath);
    this.stationId = stationId;
    this.bikeCode = bikeCode;
    this.userId = userId;
    lbUser.setText(customerController.getNameUser(userId));
    billDetail();
    setImage();
    onClickPay(stage);
  }


  public void setImage() {
    super.setImage(logoHome, Configs.IMAGE_PATH + "/" + "Logo.png");
    super.setImage(logoUser, Configs.IMAGE_PATH + "/" + "logoUserName.png");
  }

  public Rental getRental() {
    try {
      return this.giveBackBike(bikeCode, userId);
    } catch (SQLException | ParseException e) {

      Alert alert = new Alert(AlertType.INFORMATION);
      alert.setTitle("Thông báo");
      alert.setHeaderText("Sửa thông tin thất bại");
      alert.setContentText("Tên không được để trống. Vui lòng kiểm tra lại!");
      alert.show();
      return null;
    }
  }

  public Customer getCustomer(Rental rental) throws SQLException, ParseException {
    return new Customer().getCustomerById(rental.getUserId());
  }

  public Vehicle getVehicle(Rental rental) throws SQLException, ParseException {
    return getVehicleByCode(rental.getBikeId());
  }

  public void billDetail() {
    try {
      Rental rental = getRental();
      rental.setEndTime(new Date());
      Customer customer = getCustomer(rental);
      Vehicle vehicle = getVehicle(rental);
      String customerName = customer.getName();
      name.setText(customerName);
      bike.setText(vehicle.getNameVehicle());
      String startTime = simpleDateFormat.format(rental.getStartTime());
      start.setText(startTime);
      String endTime = simpleDateFormat.format(new Date());
      end.setText(endTime);
      price.setText(String.valueOf(vehicle.getPrice()));
      long _cost = calculate(rental.getStartTime().getTime(), rental.getEndTime().getTime(),
          vehicle.getPrice());
      cost.setText(String.valueOf(_cost));
    } catch (SQLException | ParseException e) {
      Alert alert = new Alert(AlertType.WARNING);
      alert.setTitle("Thông báo");
      alert.setHeaderText("Gặp lỗi trong quá trình x lý");
      alert.setContentText("Vui lòng thử lại!");
      alert.show();
    }
  }

  public long calculate(long startTime, long endTime, long price) {
    double timeLength = (endTime - startTime) / (60 * 1000 * 60);
    if (timeLength < 1) {
      return 0;
    } else {
      return (long) (timeLength * price);
    }
  }


  public Bike getVehicleByCode(int code) throws SQLException {
    String sql = "SELECT * FROM Vehicle WHERE id='" + code + "';";
    Statement stm = ECOPARK.getConnection().createStatement();
    ResultSet res = stm.executeQuery(sql);
    if (res.next()) {

      return (Bike) new Bike()
          .setId(res.getString("Id"))
          .setCode(res.getString("Code"))
          .setPrice(res.getInt("Price"))
          .setVehicleType(res.getString("VehicleType"))
          .setVehicleURL(res.getString("ImgUrl"))
          .setLicensePlate(res.getString("LicensePlate"))
          .setStationId(res.getInt("StationId"))
          .setStatus(res.getInt("Status"))
          .setDate(res.getString("ManufactoringDate"))
          .setName(res.getString("Name"));
    }
    return null;
  }

  public void warn() {
    Alert alert = new Alert(AlertType.INFORMATION);
    alert.setTitle("Thông báo");
    alert.setHeaderText("Thông tin xe và bãi xe nhập vào không đúng");
    alert.setContentText("Thông tin xe và bãi xe nhập vào không đúng. Vui lòng kiểm tra lại!");
    alert.show();
  }

  public Rental giveBackBike(String code, int userId) throws SQLException, ParseException {
    try {
      Vehicle _vehicle = vehicleInstance.getVehicleByCode(code);
      if (_vehicle != null) {
        Rental _rental = rentalInstance.getRentalByVehicleId(_vehicle.getId());
        if (_rental != null) {
          if (userId == _rental.getUserId()) {
            return _rental;
          }
        } else {
          warn();
          return null;
        }
      } else {
        warn();
        return null;
      }
      warn();
      return null;
    } catch (SQLException | ParseException e) {
      warn();
      return null;
    }
  }


  public void onClickPay(Stage primaryStage) {
    pay.addEventHandler(MouseEvent.MOUSE_CLICKED,
        event -> {
          try {
            Rental rental = getRental();
            Vehicle vehicle = getVehicle(rental);
            long cost = calculate(rental.getStartTime().getTime(), (new Date()).getTime(),
                vehicle.getPrice());
            pay(rental, this.userId, vehicle.getVehicleType(), cost);
            updateVehicleInfo(vehicle, this.stationId);
            Parking p = getParking(String.valueOf(this.stationId));
            rental.updateRentalInfo(cost, simpleDateFormat.format(new Date()), p.getName());
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Thông báo");
            alert.setHeaderText("Thanh toán thành công");
            alert.setContentText("Thanh toán thành công");
            alert.show();
            HomeScreen homeScreen = new HomeScreen(primaryStage, Configs.HOME_SCREEN_PATH, this.userId);
            homeScreen.setScreenTitle(ScreenTittle.HOME_SCREEN);
            homeScreen.show();

          } catch (SQLException | ParseException | IOException e) {
            e.printStackTrace();
          }
        });
  }



  public void pay(Rental rental, int userId, String vehicleType, long cost)
      throws SQLException, ParseException {
    Customer _customer = getCustomer(rental);
    String cardId = _customer.getCardId();
    Card _card = card.getCard(cardId);
    switch (vehicleType) {
      case VehicleTypeConfig.BIKE:
        _card.setBalance(_card.getBalance() + ServiceConfig.BIKE_DEPOSIT);
        break;
      case VehicleTypeConfig.E_BIKE:
        _card.setBalance(_card.getBalance() + ServiceConfig.EBIKE_DEPOSIT);
        break;
      case VehicleTypeConfig.TWIN_BIKE:
        _card.setBalance(_card.getBalance() + ServiceConfig.TWIN_BIKE_DEPOSIT);
        break;
      default:
        _card.setBalance(_card.getBalance() + 100000);
    }
    if (!(_card.getBalance() < cost)) {
      //tinh tien + update tai khoan
      _card.setBalance(_card.getBalance() - cost);
      card.updateAfterPay(_card);
    } else {
      Alert alert = new Alert(AlertType.INFORMATION);
      alert.setTitle("Thông báo");
      alert.setHeaderText("Số dư trong tài khoản không đủ.");
      alert.setContentText("Số dư trong tài khoản không đủ. Vui lòng nạp thêm tiền tại quầy!");
      alert.show();
    }
  }

  public void updateVehicleInfo(Vehicle _vehicle, int stationId) throws SQLException {
    _vehicle.setStationId(stationId);
    vehicleInstance.updateAfterPay(_vehicle, 1);
  }

  public Parking getParking(String id) throws SQLException {
    return  parking.getParkingById(String.valueOf(id));
  }
}
