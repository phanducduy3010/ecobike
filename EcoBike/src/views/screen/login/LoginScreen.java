package views.screen.login;

import controller.LoginController;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import utils.Configs;
import views.screen.BaseScreenHandler;
import views.screen.home.HomeScreen;

import java.io.IOException;
import java.sql.SQLException;

public class LoginScreen extends BaseScreenHandler {
    @FXML
    private ImageView iconImg;

    @FXML
    private TextField loginCode;

    @FXML
    private TextField loginPassword;

    @FXML
    private Button btnLogin;

    private LoginController loginController;

    public LoginScreen(Stage stage, String screenPath) throws IOException {
        super(stage, screenPath);
        setImage();//set logo
    }

    public void setController(LoginController loginController){
        this.loginController = loginController;
    }

    public LoginController getController(){
        return this.loginController;
    }

    public void setImage() {
        super.setImage(iconImg, Configs.IMAGE_PATH + "/" + "Logo.png");
    }

    @FXML
    public void login() throws IOException, SQLException{
        String code = loginCode.getText();
        String pass = loginPassword.getText();
        //validate đăng nhập
        try {
            int idUser = this.loginController.login(code, pass);
            if(idUser != 0) {//đăng nhập thành công
                //chuyển đến màn home
                HomeScreen homeScreen = new HomeScreen(this.stage, Configs.HOME_SCREEN_PATH, idUser);
                homeScreen.setScreenTitle("Home Screen");
                homeScreen.show();
            }
            else {// đăng nhập lỗi
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Thông báo");
                alert.setHeaderText("Đăng nhập thất bại");
                alert.setContentText("Mã hoặc mật khẩu không đúng. Vui lòng kiểm tra lại!");
                alert.show();
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
}
