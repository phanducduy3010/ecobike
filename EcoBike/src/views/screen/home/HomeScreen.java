package views.screen.home;

import controller.CustomerController;
import controller.LoginController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import utils.Configs;
import views.screen.BaseScreenHandler;
import views.screen.account.AccountScreen;
import views.screen.account.ListCustomerScreen;
import views.screen.login.LoginScreen;
import views.screen.parking.ParkingListScreen;

import java.io.IOException;
import java.sql.SQLException;

public class HomeScreen extends BaseScreenHandler {

    @FXML
    private ImageView imgLogo;

    @FXML
    private ImageView imgUser;

    @FXML
    private ImageView backgroundHome;

    @FXML
    private Button parkingButton;

    @FXML
    private Button btnListCustomer;

    @FXML
    private Button btnLogout;

    @FXML
    private Label lbUser;

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    private int idUser;

    private CustomerController customerController = new CustomerController();

    public HomeScreen(Stage stage, String screenPath, int idUser) throws IOException, SQLException {
        super(stage, screenPath);
        setIdUser(idUser);
        lbUser.setText(customerController.getNameUser(this.getIdUser()));
        setImage();
        onClickLogoUser(stage);
        onClickParkingButton(stage);
        setShowBtnListCustomer(idUser);
    }

    /**
     * Kiểm tra quyền để ẩn/hiện btn xem danh sách người dùng
     */
    public void setShowBtnListCustomer(int idUser) throws SQLException {
        int role = customerController.getRoleUser(idUser);
        if(role == 2) {
            btnListCustomer.setVisible(true);
        }
        else {
            btnListCustomer.setVisible(false);
        }
    }

    // Khi click vao logo User no se chuyen sang man hinh thong tin nguoi dung
    public void onClickLogoUser(Stage primaryStage) {
        imgUser.addEventHandler(MouseEvent.MOUSE_CLICKED,
                event -> {
                    try {
                        AccountScreen accountScreen = new AccountScreen(primaryStage, Configs.ACCOUNT_SCREEN, this.idUser);
                        accountScreen.setPreviousScreen(this);
                        accountScreen.setDelete(false);
                        accountScreen.show();
                    } catch (IOException | SQLException e) {
                        e.printStackTrace();
                    }
                });
    }

    public void onClickParkingButton(Stage stage){
        parkingButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            ParkingListScreen parkingListScreen = null;
            try {
                        parkingListScreen = new ParkingListScreen(stage, Configs.PARKING_SCREEN_PATH, this.idUser);
                        parkingListScreen.setScreenTitle("Parking List Screen");
                        parkingListScreen.setPreviousScreen(this);
                        parkingListScreen.show();
                    } catch (IOException | SQLException e) {
                        e.printStackTrace();
                    }
        });
    }


    public void setImage(){
        super.setImage(imgLogo, Configs.IMAGE_PATH + "/" + "Logo.png");
        super.setImage(imgUser, Configs.IMAGE_PATH + "/" + "logoUserName.png");
        super.setImage(backgroundHome, Configs.IMAGE_PATH + "/" + "backgroundHome.png");
    }

    @FXML
    public void showListCustomer() throws SQLException, IOException {
        ListCustomerScreen listCustomerScreen = null;
        listCustomerScreen = new ListCustomerScreen(this.stage, Configs.LIST_CUSTOMER_SCREEN_PATH);

        listCustomerScreen.setUserId(this.idUser);
        listCustomerScreen.setPreviousScreen(this);
        listCustomerScreen.showCustomerList();
        listCustomerScreen.show();
    }

    @FXML
    public void logout() throws SQLException, IOException {
        LoginScreen loginScreen= new LoginScreen(this.stage, Configs.LOGIN);
        LoginController loginController = new LoginController();
        loginScreen.setController(loginController);

        loginScreen.show();
    }
}

