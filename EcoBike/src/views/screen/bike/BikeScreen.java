package views.screen.bike;

import controller.RentControllerSystem;
import entity.bike.Vehicle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import utils.Configs;
import views.screen.BaseScreenHandler;
import views.screen.payment.DepositScreen;

import java.io.IOException;
import java.sql.SQLException;

public class BikeScreen extends BaseScreenHandler {
    @FXML
    private Label nameVehicle;

    @FXML
    private TextArea vehicleInfo;

    @FXML
    private Button rentalBtn;

    @FXML
    private ImageView vehicleImage;

    @FXML
    private ImageView iconImg;

    @FXML
    private Label errorBox;

    @FXML
    private Button backBtn;

    private int userId;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    private Vehicle vehicle;

    private RentControllerSystem rentControllerSystem;

    public BikeScreen(Stage stage, String screenPath) throws IOException {
        super(stage, screenPath);
        setImage();
    }



    public void setController(RentControllerSystem rentControllerSystem){
        this.rentControllerSystem=rentControllerSystem;
    }

    public RentControllerSystem getController(){
        return this.rentControllerSystem;
    }


    /**
     * Phương thức hiển thị màn hình thông tin xe muốn thuê
     * @param vehicle: Xe muốn thuê
     * @param bikeScreen: màn hình hiển thị
     * @throws IOException
     * @throws SQLException
     */
    public void displayBikeInfo(Vehicle vehicle, BikeScreen bikeScreen) throws IOException, SQLException {
        this.vehicle= vehicle;
        bikeScreen.setScreenTitle("Vehicle Information Screen");
        bikeScreen.setScreenInfo(vehicle, bikeScreen); //phuong thuc o duoi

    };

    public void setScreenInfo(Vehicle vehicle, BikeScreen bikeScreen) throws SQLException {
        nameVehicle.setText(vehicle.getVehicleType());
        vehicleInfo.setText(vehicle.showInfo());
        bikeScreen.setController(rentControllerSystem);
        //set image
        bikeScreen.setImage(vehicleImage, vehicle.getImageURL());
    };

    @FXML
    public void rentVehicle() throws IOException, SQLException {
        int userId = this.getUserId();
        if(!this.rentControllerSystem.checkVehicleAvailability(this.vehicle.getCode())){
            errorBox.setText("              Xe không khả dụng, vui lòng chọn xe khác");

        }else {
            DepositScreen depositScreen = new DepositScreen(this.stage, Configs.DEPOSIT_SCREEN_PATH, this.vehicle);
            depositScreen.setUserId(userId);
            depositScreen.setPreviousScreen(this);
            depositScreen.setController(rentControllerSystem);
            depositScreen.show();
        }
    }

    public void setImage() {
        super.setImage(iconImg, Configs.IMAGE_PATH + "/" + "Logo.png");

    }

    public TextArea getTextArea(){
        return this.vehicleInfo;
    }

    public void backToPrevScreen(){
        getPreviousScreen().show();
    }

}
