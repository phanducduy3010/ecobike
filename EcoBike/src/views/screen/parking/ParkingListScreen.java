package views.screen.parking;

import controller.AccountController;
import controller.CustomerController;

import entity.bike.Parking;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import utils.Configs;
import views.screen.BaseScreenHandler;
import views.screen.account.AccountScreen;
import views.screen.bike.VehicleListScreen;

import java.io.IOException;
import java.sql.SQLException;

public class ParkingListScreen extends BaseScreenHandler{

    @FXML
    private ImageView logoHome;

    @FXML
    private ImageView logoUser;

    @FXML
    private ImageView backgroundParking;

    @FXML
    private Label parkingInfo1;

    @FXML
    private Label parkingInfo2;

    @FXML
    private Label parkingInfo3;

    @FXML
    private Label lbUser;

    @FXML
    private Button buttonInfo1;

    @FXML
    private Button buttonInfo2;

    @FXML
    private Button buttonInfo3;

    CustomerController customerController = new CustomerController();

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    private int idUser;

    public ParkingListScreen(Stage stage, String screenPath, int idUser) throws IOException, SQLException {
        super(stage, screenPath);
        setIdUser(idUser);
        lbUser.setText(customerController.getNameUser(this.getIdUser()));
        setScreenInfo();
        setImage();
        //onClickLogoUser(stage);
        onClickLogoHome();
        onClickButtonInfo1(stage);
        onClickButtonInfo2(stage);
        onClickButtonInfo3(stage);
    }

//    // Khi click vao logo User no se chuyen sang man hinh thong tin nguoi dung
//    public void onClickLogoUser(Stage primaryStage) {
//        logoUser.addEventHandler(MouseEvent.MOUSE_CLICKED,
//                event -> {
//                    AccountScreen accountScreen= null;
//                    try {
//                        accountScreen = new AccountScreen(primaryStage, Configs.ACCOUNT_SCREEN, this.idUser);
//                        AccountController accountController = new AccountController();
//                        accountScreen.setController(accountController);
//                        accountScreen.show();
//                    } catch (IOException | SQLException e) {
//                        e.printStackTrace();
//                    }
//                });
//    }

    // Click vao xem chi tiet
    public void onClickButtonInfo1(Stage primaryStage){
        buttonInfo1.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            VehicleListScreen vehicleListScreen = null;
            try {
                vehicleListScreen = new VehicleListScreen(primaryStage, Configs.VEHICLE_LIST_SCREEN_PATH);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            vehicleListScreen.setStationId(1);
            vehicleListScreen.setUserId(this.idUser);
            vehicleListScreen.setPreviousScreen(this);
            try {
                vehicleListScreen.showVehicleList();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            vehicleListScreen.show();
        });
    }

    //Click vao xem chi tiet
    public void onClickButtonInfo2(Stage primaryStage){
        buttonInfo2.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            VehicleListScreen vehicleListScreen = null;
            try {
                vehicleListScreen = new VehicleListScreen(primaryStage, Configs.VEHICLE_LIST_SCREEN_PATH);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            vehicleListScreen.setStationId(2);
            vehicleListScreen.setUserId(this.idUser);
            vehicleListScreen.setPreviousScreen(this);
            try {
                vehicleListScreen.showVehicleList();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            vehicleListScreen.show();
        });
    }

    //Click vao xem chi tiet
    public void onClickButtonInfo3(Stage primaryStage){
        buttonInfo3.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            VehicleListScreen vehicleListScreen = null;
            try {
                vehicleListScreen = new VehicleListScreen(primaryStage, Configs.VEHICLE_LIST_SCREEN_PATH);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            vehicleListScreen.setStationId(3);
            vehicleListScreen.setUserId(this.idUser);
            vehicleListScreen.setPreviousScreen(this);
            try {
                vehicleListScreen.showVehicleList();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            vehicleListScreen.show();
        });
    }

    public void setScreenInfo() throws SQLException {
        Parking parking1 = new Parking().getParkingById("1");
        parkingInfo1.setText(parking1.getLocation());
        Parking parking2 = new Parking().getParkingById("2");
        parkingInfo2.setText(parking2.getLocation());
        Parking parking3 = new Parking().getParkingById("3");
        parkingInfo3.setText(parking3.getLocation());
    }

    public void setImage(){
        super.setImage(logoHome, Configs.IMAGE_PATH + "/" + "Logo.png");
        super.setImage(logoUser, Configs.IMAGE_PATH + "/" + "user.png");
        super.setImage(backgroundParking, Configs.IMAGE_PATH + "/" + "parkinglist.png");
    }

    public void onClickLogoHome(){
        logoHome.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            this.getPreviousScreen().show();
        });
    }
}
