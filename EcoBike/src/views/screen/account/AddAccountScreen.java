package views.screen.account;

import controller.AccountController;
import controller.VehicleInfoValidation;
import entity.customer.Customer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import utils.Configs;
import views.screen.BaseScreenHandler;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AddAccountScreen extends BaseScreenHandler {
    @FXML
    private ImageView iconImg;

    @FXML
    private TextField code;

    @FXML
    private TextField name;

    @FXML
    private DatePicker birthday;

    @FXML
    private RadioButton genderMale;

    @FXML
    private RadioButton genderFemale;

    @FXML
    private PasswordField password;

    @FXML
    private Button btnAdd;

    private AccountController accountController;

    VehicleInfoValidation vehicleInfoValidation = new VehicleInfoValidation();

    public AddAccountScreen(Stage stage, String screenPath) throws IOException, SQLException {
        super(stage, screenPath);

        AccountController accountController = new AccountController();
        setController(accountController);
        vehicleInfoValidation.disableFutureDate(birthday);
        setImage();//set logo
    }

    public void setController(AccountController accountController) {
        this.accountController = accountController;
    }

    public AccountController getController() {
        return this.accountController;
    }

    public void setImage() {
        super.setImage(iconImg, Configs.IMAGE_PATH + "/" + "Logo.png");
    }

    /**
     * Chuyển từ string sang datepicker
     *
     * @param dateString String
     * @return LocalDate
     */
    public LocalDate parseStringToDate(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate localDate = LocalDate.parse(dateString, formatter);
        return localDate;
    }

    /**
     * Chuyển từ datepicker sang string
     *
     * @param date Datepicker
     * @return String
     */
    public String parseDateToString(DatePicker date) {
        return date.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }

    @FXML
    public void back() throws IOException, SQLException {
        this.getPreviousScreen().show();
    }

    @FXML
    public void add() throws SQLException, IOException {
        Customer customer = getInforForm();
        if(this.accountController.validateFormAddAccount(customer)) {//validate đúng
            if(this.accountController.addCustomer(customer)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Thông báo");
                alert.setHeaderText("Thêm tài khoản khách hàng thành công");
                alert.show();
                this.getPreviousScreen().show();
            }
            else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Thông báo");
                alert.setHeaderText("Thêm tài khoản khách hàng thất bại");
                alert.setContentText("Vui lòng liên hệ quản trị viên để giải quyết!");
                alert.show();
            }
        }
        else {//validate lỗi
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Thông báo");
            alert.setHeaderText("Thêm tài khoản khách hàng thất bại");
            alert.setContentText("Mã, Tên, Mật khẩu không được để trống. Mã không được trùng");
            alert.show();
        }
    }

    /**
     * lấy thông tin trên form
     * @return Customer
     */
    public Customer getInforForm() throws SQLException {
        Customer customer = new Customer();
        customer.setCode(code.getText());
        customer.setName(name.getText());
        customer.setPassword(password.getText());
        customer.setBirthday(parseDateToString(birthday));
        if(genderMale.isSelected()) {//Nếu Nam
            customer.setGender(1);
        }
        else if(genderFemale.isSelected()) {//Chọn Nữ
            customer.setGender(2);
        }
        else {
            customer.setGender(0);
        }
        return customer;
    }
}
