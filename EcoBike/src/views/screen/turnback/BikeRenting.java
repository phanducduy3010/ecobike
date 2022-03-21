package views.screen.turnback;

import config.ServiceConfig;
import config.ServiceConfig.ScreenTittle;
import controller.CustomerController;
import entity.bike.Vehicle;
import entity.rental.Rental;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

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
public class BikeRenting extends BaseScreenHandler {

  public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
      ServiceConfig.DATE_TIME_PATTERN);



  @FXML
  private ImageView logoHome;

  @FXML
  private ImageView logoUser;

  @FXML
  private TextField name;

  @FXML
  private TextField code;

  @FXML
  private TextField plate;

  @FXML
  private TextField startStation;

  @FXML
  private TextField startTime;

  @FXML
  private TextField price;

  @FXML
  private Button pay;

  @FXML
  private Label lbUser;

  @FXML
  Button back;

  private int userId;

  private Rental rental = new Rental();

  private Vehicle vehicle = new Vehicle();

  private CustomerController customerController = new CustomerController();

  public BikeRenting(Stage stage, String screenPath, int userId) throws IOException, SQLException {
    super(stage, screenPath);
    this.userId = userId;
    setImage();
    onclickBack(stage);
    onClickPay(stage);
    loadData();
    lbUser.setText(customerController.getNameUser(userId));
  }

  public void setImage() {
    super.setImage(logoHome, Configs.IMAGE_PATH + "/" + "Logo.png");
    super.setImage(logoUser, Configs.IMAGE_PATH + "/" + "logoUserName.png");
  }


  public void onClickPay(Stage primaryStage) {
    pay.addEventHandler(MouseEvent.MOUSE_CLICKED,
        event -> {
          TurnBackBike turnBackBikeScreen = null;
          try {

            Rental rental = getRental();
            Vehicle vehicle = getVehicle(rental);
            turnBackBikeScreen = new TurnBackBike(primaryStage, Configs.TURNBACK_BIKE, this.userId,
                vehicle.getId());
            turnBackBikeScreen.setScreenTitle("Turn back Bike");
            turnBackBikeScreen.show();

          } catch (IOException | SQLException e) {
            e.printStackTrace();
          }
        });
  }

  public void onclickBack(Stage primaryStage) {
    back.addEventHandler(MouseEvent.MOUSE_CLICKED,
        event -> {

          HomeScreen homeScreen = null;
          try {

            homeScreen = new HomeScreen(primaryStage, Configs.HOME_SCREEN_PATH, this.userId);
            homeScreen.setScreenTitle(ScreenTittle.HOME_SCREEN);
            homeScreen.show();

          } catch (IOException | SQLException e) {
            e.printStackTrace();
          }
        });

  }

  public void loadData(){
    try {
      Rental rental = getRental();
      Vehicle vehicle = getVehicle(rental);
      name.setText(vehicle.getName());
      code.setText(vehicle.getCode());
      plate.setText(vehicle.getLicensePlate());
      startStation.setText(rental.getStartStation());
      startTime.setText(simpleDateFormat.format(rental.getStartTime()));
      price.setText(String.valueOf(vehicle.getPrice()));
    } catch (SQLException e) {
      Alert alert = new Alert(AlertType.WARNING);
      alert.setTitle("Thông báo");
      alert.setHeaderText("Gặp lỗi trong quá trình x lý");
      alert.setContentText("Vui lòng thử lại!");
      alert.show();
    }
  }

  public Rental getRental() throws SQLException {
    return rental.getBikeRenting(this.userId);
  }

  public Vehicle getVehicle(Rental rental) throws SQLException {
    return vehicle.getById(rental.getBikeId());
  }

}
