package views.screen.bike;

import controller.*;
import entity.bike.Parking;
import entity.bike.Vehicle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import utils.Configs;

import views.screen.BaseScreenHandler;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class VehicleListScreen extends BaseScreenHandler {

    @FXML
    private TableView<Vehicle> vehicleTable;

    @FXML
    private TableColumn<Vehicle, String> nameColumn;

    @FXML
    private TableColumn<Vehicle, String> codeColumn;

    @FXML
    private TableColumn<Vehicle, Integer> typeColumn;

    @FXML
    private TableColumn<Vehicle, Integer> priceColumn;

    @FXML
    private TableColumn<Vehicle, Integer> licenseColumn;

    @FXML
    private TableColumn<Vehicle, Integer> statusColumn;

    @FXML
    private TableColumn<Vehicle, String> dateColumn;

    @FXML
    private Button viewVehicle;

    @FXML
    private Pane addVehicleForm;

    @FXML
    private Pane pane;

    @FXML
    private TextField tfVehicleName;

    @FXML
    private TextField tfVehicleCode;

    @FXML
    private TextField tfLicense;

    @FXML
    private TextField tfVehicleType;

    @FXML
    private TextField tfPrice;

    @FXML
    private Label lbUser;

    @FXML
    private DatePicker dpManufactoring;

    @FXML
    private TextField tfImage;

    @FXML
    private TextField tfSearch;

    @FXML
    private Text txtWarning1;

    @FXML
    private Text txtWarning2;

    @FXML
    private Text txtSuccess;

    @FXML
    private ImageView imgLogo;

    @FXML
    private ImageView imgPoster;

    @FXML
    private ImageView imgUser;

    @FXML
    private Button btnShowInfo;

    @FXML
    private Button btnShowAddVehicleForm;

    @FXML
    private Pane paneRule;

    @FXML
    private Button btnUser;

    @FXML
    private Button btnAddVehicle;

    @FXML
    private Text txtStationNumber;

    @FXML
    private Text txtEmptyDocks;

    @FXML
    private Text txtOverEmptyDocks;

    private ObservableList<Vehicle> vehicleList;

    private int stationId;

    private int userRole;

    private int userId;

    private String userDisplayName;

    VehicleDetailController vehicleController = new VehicleDetailController();

    CustomerController customerController = new CustomerController();

    ParkingController parkingController = new ParkingController();

    VehicleInfoValidation vehicleInfoValidation = new VehicleInfoValidation();

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserDisplayName() {
        return userDisplayName;
    }

    public void setUserDisplayName(String userDisplayName) {
        this.userDisplayName = userDisplayName;
    }

    public int getStationId() {
        return stationId;
    }

    public void setStationId(int stationId) {
        this.stationId = stationId;
    }

    public int getUserRole() {
        return userRole;
    }

    public void setUserRole(int userRole) {
        this.userRole = userRole;
    }

    public VehicleListScreen(Stage stage, String screenPath) throws IOException, SQLException {
        super(stage, screenPath);
        setImage();
        paneRule.setVisible(false);
        pane.setVisible(true);
        addVehicleForm.setVisible(false);
    }

    public void setImage() {
        super.setImage(imgLogo, Configs.IMAGE_PATH + "/" + "Logo.png");
        super.setImage(imgPoster, Configs.IMAGE_PATH + "/" + "poster.png");
        super.setImage(imgUser, Configs.IMAGE_PATH + "/" + "user.png");
    }

    @FXML
    public void showVehicleList() throws SQLException {
        int userId = this.getUserId();
        this.setScreenTitle("Danh sách các xe trong bãi");
        int userRole = customerController.getRoleUser(userId);
        lbUser.setText(customerController.getNameUser(userId));
        txtStationNumber.setText(String.valueOf(this.stationId));
        txtEmptyDocks.setText(String.valueOf(parkingController.getEmptyDocksByStationId(this.stationId)));

        if(userRole == 1) {
            btnShowInfo.setVisible(true);
            btnShowAddVehicleForm.setVisible(false);
        } else {
            btnShowInfo.setVisible(false);
            btnShowAddVehicleForm.setVisible(true);
        }

        int stationId =  this.getStationId();

        vehicleInfoValidation.numericOnly(tfPrice);
        vehicleInfoValidation.formatDateTime(dpManufactoring);
        vehicleInfoValidation.disableFutureDate(dpManufactoring);

        List vehicle = null; // Khởi tạo một danh sách các phượng tiện
        try {
            vehicle = vehicleController.getVehiclesByStationId(stationId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //Thêm dữ liệu vào từng cột trong bảng
        vehicleList = FXCollections.observableArrayList(vehicle);
        nameColumn.setCellValueFactory(new PropertyValueFactory<Vehicle, String>("Name"));
        codeColumn.setCellValueFactory(new PropertyValueFactory<Vehicle, String>("Code"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<Vehicle, Integer>("Price"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<Vehicle, Integer>("VehicleType"));
        licenseColumn.setCellValueFactory(new PropertyValueFactory<Vehicle, Integer>("LicensePlate"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<Vehicle, Integer>("Status"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<Vehicle, String>("ManufactoringDate"));
        vehicleTable.setItems(vehicleList);

        //Tìm kiếm và trả về kết quả luôn trong bảng
        FilteredList<Vehicle> filteredData = new FilteredList<>(vehicleList, b -> true);
        tfSearch.textProperty().addListener((observable, oldValue, newValue) -> {
           filteredData.setPredicate(vehicleList -> {
               if(newValue.isEmpty() || newValue.isBlank() || newValue == null) {
                   return true;
               }
               String searchString = newValue.toLowerCase();
               if(vehicleList.getCode().toLowerCase().indexOf(searchString) > -1) {
                   return true;
               } else if (vehicleList.getName().toLowerCase().indexOf(searchString) > -1) {
                   return true;
               } else if(vehicleList.getLicensePlate().toLowerCase().indexOf(searchString) > -1) {
                   return true;
               } else if(vehicleList.getVehicleType().toLowerCase().indexOf(searchString) > -1) {
                   return true;
               }else if(vehicleList.getManufactoringDate().toLowerCase().indexOf(searchString) > -1) {
                   return true;
               }else if (String.valueOf(vehicleList.getPrice()).toLowerCase().indexOf(searchString) > -1) {
                   return true;
               } else {
                   return false;
               }
           });
        });

        SortedList<Vehicle> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(vehicleTable.comparatorProperty());
        vehicleTable.setItems(sortedData);

        vehicleTable.setRowFactory( tv -> {
            TableRow<Vehicle> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {

                    Vehicle vehicle1 = vehicleTable.getItems().get(vehicleTable.getSelectionModel().getSelectedIndex());
                    try {
                        //phaỉ có set controller ở đây vì bikescreen là màn hình đầu tiên, phải khởi tạo rentcontroller, nếu không thì những controller phía sau sẽ bị trống(null)
                        VehicleDetailViewScreen vehicleDetailViewScreen = new VehicleDetailViewScreen(this.stage, Configs.VEHICLE_DETAIL_PATH, userId);
                        RentControllerSystem rentControllerSystem = new RentControllerSystem();
                        vehicleDetailViewScreen.setController(rentControllerSystem);
                        vehicleDetailViewScreen.setVehicleCode(vehicle1.getCode());
                        vehicleDetailViewScreen.setLbUser(lbUser.getText());
                        vehicleDetailViewScreen.setPreviousScreen(this);
                        vehicleDetailViewScreen.setScreenTitle("Vehicle Detail");
                        vehicleDetailViewScreen.displayBikeInfo(rentControllerSystem.searchBike(vehicle1.getCode()), vehicleDetailViewScreen);
                        //bikeScreen.setImage();
                        vehicleDetailViewScreen.show();
                    } catch (IOException | SQLException e) {
                        e.printStackTrace();
                    }

                }
            });
            return row ;
        });
    }

    public void back() {
        this.getPreviousScreen().show();
    }

    @FXML
    public void backRule() {

        paneRule.setVisible(false);
        pane.setVisible(true);
    }

    public void showInfo() {
        paneRule.setVisible(true);
        pane.setVisible(false);
    }

    public void showAddVehicleForm() {
        addVehicleForm.setVisible(true);
        pane.setVisible(false);
        if (txtEmptyDocks.getText().equals("0")) {
            txtOverEmptyDocks.setVisible(true);
            btnAddVehicle.setDisable(true);
        }
    }

    public void backToListVehicle() throws SQLException {
        addVehicleForm.setVisible(false);
        txtWarning1.setVisible(false);
        txtWarning2.setVisible(false);
        txtSuccess.setVisible(false);
        pane.setVisible(true);
        txtEmptyDocks.setText(String.valueOf(parkingController.getEmptyDocksByStationId(this.stationId)));
    }

    public void addVehicle() throws SQLException, ParseException {
        String code = tfVehicleCode.getText();
        String price1 = tfVehicleCode.getText();
        String name = tfVehicleName.getText();
        String license = tfLicense.getText();
        String type = tfVehicleType.getText();
        String img = tfImage.getText();


        //Kiểm tra xem có bị trùng code xe trước không
        ArrayList<String> listCode = vehicleController.getAllVehicleCodes();
        int count = 0;
        for (int i = 0; i < listCode.size(); i++) {
            if (code.equals(listCode.get(i))) count++;
        }

        if (code.isEmpty() || price1.isEmpty() || name.isEmpty() ||
                license.isEmpty() || type.isEmpty() || img.isEmpty()) {
            txtWarning1.setVisible(true);
            System.out.print(count);
        } else if (count != 0) {
            txtWarning1.setVisible(false);
            txtWarning2.setVisible(true);
        } else {
            int price = Integer.parseInt(tfPrice.getText());
            String extractedDate = dpManufactoring.getValue().toString();// Whatever date you've extracted
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(extractedDate);
            String correctDate = new SimpleDateFormat("dd/MM/yyyy").format(date);
            Vehicle vehicle = new Vehicle(null, code, name, type, price,
                    stationId, img, license, 1, correctDate);
            int res = vehicleController.addVehicle(vehicle);
            System.out.print(res);
            txtWarning1.setVisible(false);
            txtWarning2.setVisible(false);
            txtSuccess.setVisible(true);
            vehicleList.add(vehicle);
            parkingController.minusEmptyDocks(this.stationId);
        }
    }
}
