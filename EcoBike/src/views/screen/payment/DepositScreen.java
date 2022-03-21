package views.screen.payment;

import config.ServiceConfig;
import controller.ParkingController;
import controller.RentControllerSystem;
import entity.bike.Parking;
import entity.bike.Vehicle;
import entity.customer.Customer;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import utils.Configs;
import views.screen.BaseScreenHandler;
import views.screen.bike.BikeScreen;
import views.screen.turnback.BikeRenting;

import java.io.IOException;
import java.sql.SQLException;

public class DepositScreen extends BaseScreenHandler {

    @FXML
    private TextField cardIDInput;

    @FXML
    private Button verifyIDBtn;

    @FXML
    private TextArea notifyBox;

    @FXML
    private Button verifyDepositBtn;

    @FXML
    private Label cardError;

    @FXML
    private ImageView iconImg;

    @FXML
    private ImageView decoratedImg;

    @FXML
    private Button backBtn;

    private int userId;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    private RentControllerSystem rentControllerSystem;
    private Vehicle vehicle;
    private float depositFee;
    private String cardID;
    private int isDeposit;
    private int isVerifyIDCard;
    private ParkingController parkingController;

    public DepositScreen(Stage stage, String screenPath, Vehicle vehicle) throws IOException, SQLException {
        super(stage, screenPath);
        this.vehicle = vehicle;
        setImage();
        super.setImage(decoratedImg, Configs.IMAGE_PATH+"/welcome.png");
        this.isDeposit=0;
        this.isVerifyIDCard=0;
        this.parkingController = new ParkingController();
    }

    public void setController(RentControllerSystem rentControllerSystem){
        this.rentControllerSystem=rentControllerSystem;
    }

    public RentControllerSystem getController(){
        return this.rentControllerSystem;
    }

    /**
     * Phương thức kiểm tra thẻ thanh toán
     * @throws SQLException
     */
    @FXML
    public void verifyCardID() throws SQLException {
        cardError.setText("");
        notifyBox.setText("");
        this.cardID = cardIDInput.getText();
        ValidateInfo validateInfo = new ValidateInfo();
        if(validateInfo.validateIDCard(cardID)==false){
            cardError.setText("Mã thẻ thanh toán chỉ bao gồm chữ số!");
        } else if(this.rentControllerSystem.checkIdCard(cardID)==true){
            if(!rentControllerSystem.checkCardAvailability(Integer.parseInt(cardID))){
                notifyBox.setText("Thẻ đã được sử dụng để thuê một xe khác.");
                return;
            }
            this.isVerifyIDCard=1;
            //cardError.setText("");
            this.depositFee = this.rentControllerSystem.calculateDepositFee(this.vehicle.getVehicleType());
            notifyBox.setText("Loại xe mà bạn muốn thuê là: " + this.vehicle.getVehicleType() + '\n' + "Bạn vui lòng đặt cọc: " +
                    depositFee + " để sử dụng dịch vụ!" + "\n" + "Nếu đồng ý, bấm nút \"Xác nhận đặt cọc\" phía dưới.");
        }else{
            cardError.setText("Mã thẻ không đúng, xin vui lòng nhập lại.");
        }

    }

    /**
     * Phương thức xác nhận đặt cọc thuê xe
     * @throws SQLException
     */
    @FXML
    public void verifyDeposit() throws SQLException {
        if(isVerifyIDCard==0) return;
        boolean result = this.rentControllerSystem.processDeposit(this.cardID, this.depositFee, this.vehicle);
        if (result) {
            //tăng số khoảng trống trong bãi
            this.parkingController.increaseEmptyDocks(this.vehicle.getStationId());

            Customer customer = new Customer();
            Customer resCustomer = customer.getCustomerByCardId(cardID);
            Parking station = parkingController.getParkingById(vehicle.getStationId());
            String rentalCost= vehicle.getPrice()+"";
            this.rentControllerSystem.addRental(this.vehicle.getId(), resCustomer.getId(), station.getLocation(), rentalCost, Integer.parseInt(cardID));

//            notifyBox.setText("Đặt cọc thành công!\nBạn đã có thể sử dụng xe.");
            verifyDepositBtn.setOnMouseClicked(e->{
                verifyDepositBtn.setVisible(false);
                this.isDeposit=1;
            });
            BikeRenting bikeRenting = null;
            try {
                bikeRenting = new BikeRenting(stage, Configs.BIKE_RENTING, this.getUserId());
                bikeRenting.setScreenTitle(ServiceConfig.ScreenTittle.BIKE_RENTING);
                bikeRenting.show();
            } catch (IOException | SQLException e) {
                e.printStackTrace();
            }

        }else{
            notifyBox.setText("Giao dịch không thành công do tài khoản không đủ số dư!");
        }
    }

    public void setImage() {
        super.setImage(iconImg, Configs.IMAGE_PATH + "/" + "Logo.png");

    }

    public void backToPrevScreen(){
        if(isDeposit==0) getPreviousScreen().show();
        else {
            BikeScreen prevScreen = (BikeScreen) getPreviousScreen();
            TextArea txt = prevScreen.getTextArea();
            txt.setText(txt.getText().replace("Khả dụng", "Đã hết"));
            prevScreen.show();
        };
    }



}
