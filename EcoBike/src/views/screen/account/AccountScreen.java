package views.screen.account;

import controller.AccountController;
import controller.VehicleInfoValidation;
import entity.customer.Customer;
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

public class AccountScreen extends BaseScreenHandler {

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
    private Button btnEdit;

    @FXML
    private Button btnSave;

    @FXML
    private Text title;

    @FXML
    private Button btnDelete;

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    private int idUser;

    public boolean isDelete() {
        return isDelete;
    }

    public void setDelete(boolean delete) {
        isDelete = delete;
    }

    private boolean isDelete;//có thể xóa hay k

    private AccountController accountController;

    VehicleInfoValidation vehicleInfoValidation = new VehicleInfoValidation();

    public AccountScreen(Stage stage, String screenPath, int idUser) throws IOException, SQLException {
        super(stage, screenPath);

        AccountController accountController = new AccountController();
        setController(accountController);
        vehicleInfoValidation.disableFutureDate(birthday);
        setIdUser(idUser);
        setImage();//set logo
        loadData();//load dữ liệu lên giao diên
        disableControl(true);
        hideButton(true);//set button mặc định sửa thông tin
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

    public void loadData() throws SQLException {
        Customer customer = this.accountController.getCustomerById(this.idUser);
        if (customer != null) {
            code.setText(customer.getCode());
            name.setText(customer.getName());
            birthday.setValue(parseStringToDate(customer.getBirthday()));
            switch (customer.getGender()) {
                case 1: {//nam
                    genderMale.setSelected(true);
                    genderFemale.setSelected(false);
                    break;
                }
                case 2: {//nữ
                    genderMale.setSelected(false);
                    genderFemale.setSelected(true);
                    break;
                }
                default: {
                    genderMale.setSelected(false);
                    genderFemale.setSelected(false);
                    break;
                }
            }
        }
    }

    @FXML
    public void mouseMove() {
        this.btnDelete.setVisible(this.isDelete);
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

    /**
     * Ẩn control input
     *
     * @param isDisable
     */
    public void disableControl(boolean isDisable) {
        if (isDisable) {
            name.setDisable(true);
            birthday.setDisable(true);
            genderMale.setDisable(true);
            genderFemale.setDisable(true);
        } else {
            name.setDisable(false);
            birthday.setDisable(false);
            genderMale.setDisable(false);
            genderFemale.setDisable(false);
        }
    }

    /**
     * Ẩn sửa thông tin tài khoản (true - ẩn; false - hiện)
     *
     * @param hideEdit
     */
    public void hideButton(boolean hideEdit) {
        if (hideEdit) {
            btnEdit.setVisible(true);
            btnSave.setVisible(false);
        } else {
            btnEdit.setVisible(false);
            btnSave.setVisible(true);
        }
    }

    @FXML
    public void back() {
        this.getPreviousScreen().show();
    }

    /**
     * Sửa thông tin
     *
     * @throws IOException
     * @throws SQLException
     */
    @FXML
    public void edit() throws IOException, SQLException {
        hideButton(false);
        disableControl(false);
    }

    /**
     * Lưu thông tin sửa
     *
     * @throws IOException
     * @throws SQLException
     */
    @FXML
    public void save() throws IOException, SQLException {
        Customer customer = getInforForm();
        if (this.accountController.validateFormAccount(customer)) {//validate đúng
            customer.setId(this.idUser);
            if (this.accountController.editUserInfor(customer)) {
                hideButton(true);
                disableControl(true);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Thông báo");
                alert.setHeaderText("Sửa thông tin thành công");
                alert.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Thông báo");
                alert.setHeaderText("Sửa thông tin thất bại");
                alert.setContentText("Vui lòng liên hệ quản trị viên để giải quyết!");
                alert.show();
            }
        } else {//validate lỗi
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Thông báo");
            alert.setHeaderText("Sửa thông tin thất bại");
            alert.setContentText("Tên không được để trống. Vui lòng kiểm tra lại!");
            alert.show();
        }
    }

    /**
     * lấy thông tin trên form
     *
     * @return Customer
     */
    public Customer getInforForm() throws SQLException {
        Customer customer = new Customer();
        customer.setName(name.getText());
        customer.setBirthday(parseDateToString(birthday));

        if (genderMale.isSelected()) {//Nếu Nam
            customer.setGender(1);
        } else if (genderFemale.isSelected()) {//Chọn Nữ
            customer.setGender(2);
        } else {
            customer.setGender(0);
        }
        return customer;
    }

    /**
     * Xóa tài khoản khách hàng
     *
     * @throws IOException
     * @throws SQLException
     */
    @FXML
    public void delete() throws IOException, SQLException {
        if (this.accountController.deleteUserById(this.getIdUser())) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Thông báo");
            alert.setHeaderText("Xóa người dùng thành công");
            alert.show();

            this.getPreviousScreen().show();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Thông báo");
            alert.setHeaderText("Xóa không thành công");
            alert.setContentText("Vui lòng liên hệ quản trị viên để giải quyết!");
            alert.show();
        }
    }

}
