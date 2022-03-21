package views.screen.turnback;

import config.ServiceConfig.ScreenTittle;
import controller.CustomerController;
import entity.bike.Vehicle;
import entity.rental.Rental;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
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
import views.screen.bill.Bill;

/**
 * @author nguyen
 * @create_date 06/01/2022
 */
public class TurnBackBike extends BaseScreenHandler {

  @FXML
  private ImageView logoHome;

  @FXML
  private ImageView logoUser;


  @FXML
  private TextField stationId;

  @FXML
  private Button submit;

  @FXML
  private Label lbUser;

  @FXML
  Button back;

  private int userId;

  private String bikeId;

private CustomerController customerController = new CustomerController();

  private Rental rental = new Rental();

  private Vehicle vehicle = new Vehicle();

  public TurnBackBike(Stage stage, String screenPath,  int idUser, String bikeId)
      throws IOException, SQLException {
    super(stage, screenPath);
    this.userId = idUser;
    this.bikeId = bikeId;
    lbUser.setText(customerController.getNameUser(idUser));
    setImage();
    onClickSubmit(stage);
    onclickBack(stage);
  }

  //click submit
  public void onClickSubmit(Stage primaryStage){
    submit.addEventHandler(MouseEvent.MOUSE_CLICKED,
        event -> {
          try {

            Rental rental = getRental();
            Vehicle vehicle = getVehicle(rental);
            String bikeCode = vehicle.getCode();
            int _stationId = Integer.parseInt(stationId.getText());
            Bill bill = new Bill(primaryStage, Configs.BILL_PATH, this.userId, bikeCode, _stationId);
            bill.setScreenTitle(ScreenTittle.BILL_SCREEN);
            bill.show();

          } catch (IOException | ParseException | SQLException e) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Thông báo");
            alert.setHeaderText("Mã xe hoặc id bãi xe nhập vào không đúng");
            alert.setContentText("Vui lòng kiểm tra lại!");
            alert.show();
          }
        });
  }

  public void setImage(){
    super.setImage(logoHome, Configs.IMAGE_PATH + "/" + "Logo.png");
    super.setImage(logoUser, Configs.IMAGE_PATH + "/" + "logoUserName.png");
  }

  public Rental getRental() throws SQLException {
    return rental.getBikeRenting(this.userId);
  }
  public Vehicle getVehicle(Rental rental) throws SQLException {
    return vehicle.getById(rental.getBikeId());
  }


  public void onclickBack(Stage primaryStage) {
    back.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
      BikeRenting bikeRenting = null;
      try {
        bikeRenting = new BikeRenting(stage, Configs.BIKE_RENTING, this.userId);
        bikeRenting.setScreenTitle(ScreenTittle.BIKE_RENTING);
        bikeRenting.show();

      } catch (IOException | SQLException e) {
        e.printStackTrace();
      }
    });
  }
}
