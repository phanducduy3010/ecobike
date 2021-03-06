package entity.bike;

import entity.db.ECOPARK;

import java.sql.*;
import java.util.ArrayList;

public class Vehicle {

    protected Statement stm;
    protected String id;
    protected int price;
    protected int quantity;
    protected String imageURL;
    protected String nameVehicle;
    protected String code;
    protected String vehicleType;
    protected String licensePlate;
    protected int stationId;
    protected int status;
    protected String manufactoringDate;
    protected String name;


    public Vehicle() throws SQLException {
        stm = ECOPARK.getConnection().createStatement();
    }

    public Vehicle(String id, int price, int status, String vehicleType) throws SQLException{
        this.id = id;
        this.price = price;
        this.status = status;
        this.vehicleType = vehicleType;
    }

    public Vehicle(String code, String name, String vehicleType, int price, String manufactoringDate) throws SQLException{
        this.code = code;
        this.price = price;
        this.nameVehicle = name;
        this.vehicleType = vehicleType;
        this.manufactoringDate = manufactoringDate;
    }

    public Vehicle(String id, String code, String name, String vehicleType, int price, int stationId, String imageURL, String licensePlate, int status, String manufactoringDate) throws SQLException{
        this.id = id;
        this.price = price;
        this.stationId = stationId;
        this.imageURL = imageURL;
        this.nameVehicle = name;
        this.code = code;
        this.vehicleType = vehicleType;
        this.licensePlate = licensePlate;
        this.status = status;
        this.manufactoringDate = manufactoringDate;
    }

    public Vehicle setDate(String manufactoringDate) {
        this.manufactoringDate =manufactoringDate;
        return this;
    }

    public Vehicle setStatus(int status) {
        this.status=status;
        return this;
    }

    public Vehicle setCode(String code) {
        this.code=code;
        return this;
    }
    
    public Vehicle setVehicleType(String vehicleType){
        this.vehicleType=vehicleType;
        return this;
    }
    
    public Vehicle setLicensePlate(String licensePlate){
        this.licensePlate = licensePlate;
        return this;
    }
    
    public Vehicle setStationId(int stationId){
        this.stationId= stationId;
        return this;
    }

    public int getStationId() {
        return stationId;
    }


    public String getName() {
        return nameVehicle;
    }

    public Vehicle setName(String name) {
        this.nameVehicle = name;
        return this;
    }

    public String getId() {
        return this.id;
    }

    public Vehicle setId(String id){
        this.id = id;
        return this;
    }

    public int getPrice() {
        return this.price;
    }

    public Vehicle setPrice(int price) {
        this.price = price;
        return this;
    }

    public String getImageURL(){
        return this.imageURL;
    }

    public Vehicle setVehicleURL(String url){
        this.imageURL = url;
        return this;
    }

    public String getNameVehicle() {
        return nameVehicle;
    }

    public String getCode() {
        return code;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public Vehicle setManufactoringDate(String manufactoringDate) {
        this.manufactoringDate = manufactoringDate;
        return this;
    }

    public String getManufactoringDate() {
        return manufactoringDate;
    }

    public int getStatus() {
        return status;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    @Override
    public String toString() {
        return "{" +
                " id='" + id + "'" +
                ", price='" + price + "'" +
                ", quantity='" + quantity + "'" +
                ", type='" + vehicleType + "'" +
                ", imageURL='" + imageURL + "'" +
                "}";
    }

    public String vehicleStatus(int status){
        if (status==0){
            return "???? h???t";
        }else return "Kh??? d???ng";
    }

    // hi???n th??? th??ng tin
    public String showInfoVehicle(String typeCode){
        switch (typeCode) {
            case "CODE": {
                return code;
            }
            case "TYPE": {
                return vehicleType;
            }
            case "STATUS": {
                return vehicleStatus(status);
            }
            case "PRICE": {
                return "" + price;
            }
            case "BoD": {
                return manufactoringDate;
            }
            case "LPL": {
                return licensePlate;
            }
            case "IMG": {
                return imageURL;
            }
            default:
                return null;
        }
    }

    public String showInfo(){

        return "M?? xe: "+code+"\n"+
                "Lo???i xe: "+vehicleType+"\n"+
                "Tr???ng th??i xe: "+vehicleStatus(status)+"\n"+
                "Gi?? thu?? xe: "+price+"\n"+
                "Ng??y s???n xu???t: "+manufactoringDate+"\n";
    }

    //==============================================================================================================
    public Bike getVehicleByCode(String code) throws SQLException{
        String sql = "SELECT * FROM Vehicle WHERE Code='"+code+"';";
        Statement stm = ECOPARK.getConnection().createStatement();
        ResultSet res = stm.executeQuery(sql);
        if(res.next()) {

            return (Bike) new Bike()
                    .setId(res.getString("Id"))
                    .setCode(res.getString("Code"))
                    .setPrice(res.getInt("Price"))
                    .setVehicleType(res.getString("VehicleType"))
                    .setVehicleURL(res.getString("ImgUrl"))
                    .setLicensePlate(res.getString("LicensePlate"))
                    .setStationId(Integer.parseInt(res.getString("StationId")))
                    .setStatus(res.getInt("Status"))
                    .setDate(res.getString("ManufactoringDate"));
        }
        return null;
    }

    public int deleteVehicle(String code) throws SQLException {
        String sql = "DELETE FROM Vehicle where Code = '" + code + "';";

        stm = ECOPARK.getConnection().createStatement();
        int res = stm.executeUpdate(sql);
        return res;
    }

    // l???y xe theo id b??i
    public ArrayList getVehiclesByStationId(int stationId) throws SQLException {
        ArrayList vehicleList = new ArrayList<>();
        String sql = "SELECT * FROM Vehicle WHERE StationId = " + stationId;
        Statement stm = ECOPARK.getConnection().createStatement();
        ResultSet res = stm.executeQuery(sql);
        while (res.next()) {
            Vehicle vehicle = new Vehicle();
            vehicle.setId(res.getString("Id"));
            vehicle.setCode(res.getString("Code"));
            vehicle.setName(res.getString("Name"));
            vehicle.setPrice(res.getInt("Price"));
            vehicle.setManufactoringDate(res.getString("ManufactoringDate"));
            vehicle.setVehicleType(res.getString("VehicleType"));
            vehicle.setLicensePlate(res.getString("LicensePlate"));
            vehicle.setStatus(res.getInt("Status"));
            vehicleList.add(vehicle);
        }
        return vehicleList;
    }

    //L???y to??n b??? m?? xe ????? so s??nh khi th??m m???t xe m???i
    public ArrayList<String> getAllVehicleCodes() throws SQLException {
        ArrayList<String> arrCode = new ArrayList<>();
        String sql = "SELECT Code FROM Vehicle;";
        Statement stm = ECOPARK.getConnection().createStatement();
        ResultSet res = stm.executeQuery(sql);
        while (res.next()) {
            arrCode.add(res.getString("Code"));
        }
        return arrCode;
    }

    //Th??m m???t xe m???i
    public int createVehicle(Vehicle vehicle) throws SQLException {
        String sql = "INSERT INTO Vehicle('Code', 'Name', 'LicensePlate', 'ManufactoringDate'," +
                "'Price','VehicleType', 'StationId', 'Status', 'ImgURL') " +
                "VALUES ('"  + vehicle.getCode() + "', '" + vehicle.getName() + "', '" + vehicle.getLicensePlate() + "', '" + vehicle.getManufactoringDate() + "', " +
                + vehicle.getPrice() + ", '" + vehicle.getVehicleType() + "'," + vehicle.getStationId() + "," + 1 + ", '" + vehicle.getImageURL() + "')";
        stm = ECOPARK.getConnection().createStatement();
        int res = stm.executeUpdate(sql);
        return res;
    }

    // thay ?????i tr???ng th??i
    public void updateStatus(Vehicle bike) throws SQLException {
        //cap nhat lai trang thai xe
        String sql = "UPDATE Vehicle SET  Status="+ 0 +" WHERE Code='"+bike.getCode()+"';";
        stm = ECOPARK.getConnection().createStatement();
        stm.executeUpdate(sql);
    }

    // s???a th??ng tin xe
    public int updateVehicle(Vehicle bike) throws SQLException {
        String sql = "update Vehicle set name = '" + bike.getNameVehicle()
                + "', Price = "+ bike.getPrice()
                + ", VehicleType = '"+ bike.getVehicleType()
                + "', ManufactoringDate = '" + bike.getManufactoringDate()
                + "' where code = '" + bike.getCode() + "';";

        stm = ECOPARK.getConnection().createStatement();
        int res = stm.executeUpdate(sql);
        return res;
    }

    public boolean checkVehicleAvailability(String code) throws SQLException {
        String sql = "SELECT * FROM Vehicle WHERE Code='"+code+"';";
        Statement stm = ECOPARK.getConnection().createStatement();
        ResultSet res = stm.executeQuery(sql);
        if(res.getInt("Status")==1){
            return true;
        }
        return false;

    }

    public Vehicle getById(int id) throws SQLException {
        String sql = "SELECT * FROM Vehicle WHERE ID='" + id + "';";
        Statement stm = ECOPARK.getConnection().createStatement();
        ResultSet res = stm.executeQuery(sql);
        if (res.next()) {
            return (Bike) new Bike()
                    .setId(res.getString("Id"))
                    .setCode(res.getString("Code"))
                    .setPrice(res.getInt("Price"))
                    .setVehicleType(res.getString("VehicleType"))
                    .setVehicleURL(res.getString("ImgUrl"))
                    .setLicensePlate(res.getString("LicensePlate"))
                    .setStationId(res.getInt("StationId"))
                    .setStatus(res.getInt("Status"))
                    .setDate(res.getString("ManufactoringDate"))
                    .setName(res.getString("Name"));
        }
        return null;
    }

    public void updateAfterPay(Vehicle bike, int status) throws SQLException {
        //cap nhat lai trang thai xe
        String sql =
                "UPDATE Vehicle SET  STATUS=" + 1  + " , STATIONID = "+ bike.getStationId()+ "  WHERE Code='" + bike.getCode() + "';";
        stm = ECOPARK.getConnection().createStatement();
        stm.executeUpdate(sql);
    }
}
