package views.screen.bike;

import controller.CustomerController;
import controller.ParkingController;
import controller.RentControllerSystem;
import controller.VehicleDetailController;
import entity.bike.Vehicle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import utils.Configs;
import views.screen.BaseScreenHandler;


import java.io.IOException;
import java.sql.SQLException;

public class VehicleDetailViewScreen extends BaseScreenHandler {

    @FXML
    private Label pageTitle;

    @FXML
    private TextField vehicleCode;
    public String getVehicleCode() {
        return vehicleCode.getText();
    }
    public void setVehicleCode(String vehicleCode)
    {
        this.vehicleCode.setText(vehicleCode) ;
    }

    @FXML
    private TextField vehicleName;

    @FXML
    private TextField vehicleType;

    @FXML
    private TextField vehicleRentalPrice;

    @FXML
    private TextField dateOfManufacture;

    @FXML
    private TextField licensePlate;

    @FXML
    private TextField imageVehicle;

    @FXML
    private ImageView imgLogo;

    @FXML
    private ImageView imgUser;

    @FXML
    private ImageView vehicleImage;

    @FXML
    private Text messNotify;

    @FXML
    private Pane paneNotification;

    @FXML
    private Button btnEditVehicle;

    @FXML
    private Button btnDeleteVehicle;

    @FXML
    private Button btnRentalVehicle;

    @FXML
    private Label lbUser;
    public void setLbUser(String userName) {
        lbUser.setText(userName);
    }

    private CustomerController customerController = new CustomerController();

    private int userId;
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public int getUserId() {
        return userId;
    }

    public int userRole;
    public void setUserRole(int role){
        this.userRole = role;
    }
    public int getUserRole(){
        return this.userRole;
    }

    private RentControllerSystem rentControllerSystem;

    private Vehicle vehicle;

    private String viewNotify;


    public VehicleDetailViewScreen(Stage stage, String screenPath,int userId ) throws IOException, SQLException {
        super(stage, screenPath);
        paneNotification.setVisible(false);
        setImage();
        setUserId(userId);
        int role = customerController.getRoleUser(userId);
        setUserRole(role);
        if(role == 1){
            // khach hang
            btnEditVehicle.setVisible(false);
            btnDeleteVehicle.setVisible(false);
            btnRentalVehicle.setVisible(true);
        } else if(role == 2){
            // admin
            btnEditVehicle.setVisible(true);
            btnDeleteVehicle.setVisible(true);
            btnRentalVehicle.setVisible(false);
        }
    }

    public void setImage() {
        super.setImage(imgLogo, Configs.IMAGE_PATH + "/" + "Logo.png");
        super.setImage(imgUser, Configs.IMAGE_PATH + "/" + "user.png");
    }

    public void setController(RentControllerSystem rentControllerSystem){
        this.rentControllerSystem = rentControllerSystem;
    }

    public void displayBikeInfo(Vehicle vehicle, VehicleDetailViewScreen vehicleDetailViewScreen) throws IOException, SQLException {
        this.vehicle= vehicle;
        vehicleDetailViewScreen.setScreenTitle("Vehicle Information Screen");
        vehicleDetailViewScreen.setScreenInfo(vehicle, vehicleDetailViewScreen); //phuong thuc o duoi
    };

    public void setScreenInfo(Vehicle vehicle, VehicleDetailViewScreen vehicleDetailViewScreen) throws SQLException {
        vehicleCode.setText(vehicle.showInfoVehicle("CODE"));
        vehicleName.setText(vehicle.getVehicleType());
        vehicleType.setText(vehicle.showInfoVehicle("TYPE"));
        vehicleRentalPrice.setText(vehicle.showInfoVehicle("PRICE"));
        dateOfManufacture.setText(vehicle.showInfoVehicle("BoD"));
        licensePlate.setText(vehicle.showInfoVehicle("LPL"));
        imageVehicle.setText(vehicle.showInfoVehicle("IMG"));

        vehicleDetailViewScreen.setController(rentControllerSystem);
        //set image
        vehicleDetailViewScreen.setImage(vehicleImage, vehicle.getImageURL());
    };

    @FXML
    public void clickRentalVehicle() throws IOException, SQLException {
        BikeScreen bikeScreen= new BikeScreen(this.stage , Configs.BIKE_SCREEN_PATH);
        bikeScreen.setController(rentControllerSystem);
        bikeScreen.setScreenTitle("Bike Screen");
        bikeScreen.setUserId(this.getUserId());
        bikeScreen.setPreviousScreen(this);
        bikeScreen.displayBikeInfo(rentControllerSystem.searchBike(vehicleCode.getText()), bikeScreen);
        bikeScreen.setImage();
        bikeScreen.show();
    }

    @FXML
    public void clickEditVehicle() throws IOException, SQLException {
        viewNotify = "EDIT";
        messNotify.setText("Bạn có muốn sửa thông tin xe "+ vehicleName.getText() + " không?");
        paneNotification.setVisible(true);
    }

    @FXML
    public void clickDeleteVehicle() throws IOException, SQLException {
        viewNotify = "DEL";
        messNotify.setText("Bạn có muốn xóa thông tin xe "+ vehicleName.getText() + " không?");
        paneNotification.setVisible(true);
    }

    @FXML
    public void btnCancelNotify() throws IOException, SQLException {
        paneNotification.setVisible(false);
    }

    @FXML
    public void backScreen() throws IOException, SQLException {

        VehicleListScreen vehicleListScreen = (VehicleListScreen) this.getPreviousScreen();
        vehicleListScreen.setUserId(this.getUserId());
        vehicleListScreen.showVehicleList();
        vehicleListScreen.show();
    }


    @FXML
    public void btnSaveNotify() throws IOException, SQLException {
        if(viewNotify == "EDIT"){
            String vName = vehicleName.getText();
            String vPype = vehicleType.getText();
            int vPrice = Integer.parseInt(vehicleRentalPrice.getText());
            String vManufacture = dateOfManufacture.getText();
            String vCode = vehicleCode.getText();

            Vehicle vehicle = new Vehicle(vCode, vName,vPype, vPrice, vManufacture);
            VehicleDetailController vehicleDetailController = new VehicleDetailController();

            int res = vehicleDetailController.updateInfoVehicle(vehicle);
            System.out.println(res);
        } else if (viewNotify == "DEL"){
            ParkingController parkingController = new ParkingController();
            parkingController.increaseEmptyDocks(this.vehicle.getStationId());
            VehicleDetailController vehicleDetailController = new VehicleDetailController();
            int res = vehicleDetailController.deleteVehicle(vehicleCode.getText());
            System.out.println(res);

            VehicleListScreen vehicleListScreen = (VehicleListScreen) this.getPreviousScreen();
            vehicleListScreen.setUserId(this.getUserId());
            vehicleListScreen.showVehicleList();
            vehicleListScreen.show();
        }
        paneNotification.setVisible(false);
    }
}
