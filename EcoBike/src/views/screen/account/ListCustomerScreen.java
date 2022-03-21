package views.screen.account;

import controller.AccountController;
import controller.CustomerController;
import controller.ListCustomerController;
import controller.LoginController;
import entity.bike.Vehicle;
import entity.customer.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import utils.Configs;
import views.screen.BaseScreenHandler;
import views.screen.login.LoginScreen;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ListCustomerScreen extends BaseScreenHandler {

    @FXML
    private ImageView iconImg;

    @FXML
    private ImageView imgPoster;

    @FXML
    private Button back;

    @FXML
    private Label userName;

    @FXML
    private TextField searchUser;

    @FXML
    private TableView<Customer> userTable;

    @FXML
    private TableColumn<Customer, Integer> idColumn;

    @FXML
    private TableColumn<Customer, String> codeColumn;

    @FXML
    private TableColumn<Customer, String> nameColumn;

    @FXML
    private TableColumn<Customer, String> genderColumn;

    @FXML
    private TableColumn<Customer, String> birthdayColumn;

    private ObservableList<Customer> customerList;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    private int userId;

    private ListCustomerController listCustomerController = new ListCustomerController();;

    public ListCustomerScreen(Stage stage, String screenPath) throws IOException, SQLException {
        super(stage, screenPath);
        setImage();//set logo
    }

    public void setController(ListCustomerController listCustomerController){
        this.listCustomerController = listCustomerController;
    }

    public void setImage() {
        super.setImage(iconImg, Configs.IMAGE_PATH + "/" + "Logo.png");
        super.setImage(imgPoster, Configs.IMAGE_PATH + "/" + "poster.png");
    }

    public ListCustomerController getController(){
        return this.listCustomerController;
    }

    @FXML
    public void showCustomerList() throws SQLException {
        int userId = this.getUserId();
        this.setScreenTitle("Danh sách người dùng");
        this.userName.setText(this.listCustomerController.getNameUser(userId));

        List customer = null;
        customer = listCustomerController.getAllCustomer();
        //Thêm dữ liệu vào bảng
        customerList = FXCollections.observableArrayList(customer);
        idColumn.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("Id"));
        codeColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("Code"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("Name"));
        genderColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("Gender"));
        birthdayColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("Birthday"));
        userTable.setItems(customerList);

        //Tìm kiếm và trả về kết quả trong bảng
        FilteredList<Customer> filteredData = new FilteredList<>(customerList, b -> true);
        searchUser.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(customerList -> {
                if(newValue.isEmpty() || newValue.isBlank() || newValue == null) {
                    return true;
                }
                String searchString = newValue.toLowerCase();
                if(customerList.getCode().toLowerCase().indexOf(searchString) > -1) {
                    return true;
                } else if (customerList.getName().toLowerCase().indexOf(searchString) > -1) {
                    return true;
                } else if(customerList.getBirthday().toLowerCase().indexOf(searchString) > -1) {
                    return true;
                } else {
                    return false;
                }
            });
        });

        SortedList<Customer> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(userTable.comparatorProperty());
        userTable.setItems(sortedData);

        userTable.setRowFactory(tv -> {
            TableRow<Customer> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Customer ctm = userTable.getItems().get(userTable.
                            getSelectionModel().getSelectedIndex());
                    AccountScreen accountScreen = null;
                    try {
                        accountScreen = new AccountScreen(this.stage, Configs.ACCOUNT_SCREEN, ctm.getId());
                        accountScreen.setPreviousScreen(this);
                        accountScreen.setDelete(true);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    accountScreen.show();
                }
            });
            return row;
        });
    }

    @FXML
    public void back() {
        this.getPreviousScreen().show();

    }

    @FXML
    public void add() throws SQLException, IOException {
        AddAccountScreen addAccountScreen= new AddAccountScreen(this.stage, Configs.ADD_ACCOUNT_SCREEN_PATH);
        addAccountScreen.setPreviousScreen(this);
        addAccountScreen.show();
    }

    @FXML
    public void reset() throws SQLException {
        this.showCustomerList();
    }
}
