package entity.bike;

import entity.db.ECOPARK;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Parking {

    private Statement stm;
    private String id;
    private String name;
    private String location;
    private int emptyDocks;

    public Parking() throws SQLException {
        stm = ECOPARK.getConnection().createStatement();
    }

    public Parking(String id, String name, String location) throws SQLException{
        this.id = id;
        this.name = name;
        this.location = location;
    }

    public int getEmptyDocks() {
        return emptyDocks;
    }

    public void setEmptyDocks(int emptyDocks) {
        this.emptyDocks = emptyDocks;
    }

    public Parking getParkingById(String id) throws SQLException{
        String sql = "SELECT * FROM Station WHERE Id='"+id+"';";
        Statement stm = ECOPARK.getConnection().createStatement();
        ResultSet res = stm.executeQuery(sql);
        if(res.next()) {
            return (Parking) new Parking()
                    .setName(res.getString("Name"))
                    .setLocation(res.getString("Location"));
        }
        return null;
    }
    public int getEmptyDocksByStationId(int stationId) throws SQLException {
        int emptyDocks = 0;
        String sql ="SELECT EmptyDocks FROM Station WhERE Id = " + stationId;
        Statement stm = ECOPARK.getConnection().createStatement();
        ResultSet res = stm.executeQuery(sql);
        if(res.next()) {
            emptyDocks = res.getInt("EmptyDocks");
        }
        return emptyDocks;
    }

    public int minusEmptyDocks(int stationId) throws SQLException {
        String sql = "UPDATE Station SET EmptyDocks = EmptyDocks - 1 WHERE Id =" + stationId;
        Statement stm = ECOPARK.getConnection().createStatement();
        int res = stm.executeUpdate(sql);
        return res;
    }

    private Parking setName(String name) {
        this.name = name;
        return this;
    }
    private Parking setId(String id) {
        this.id = id;
        return this;
    }
    private Parking setLocation(String location) {
        this.location = location;
        return this;
    }


    public String getId(){
        return id;
    }
    public String getName(){
        return name;
    }
    public String getLocation(){
        return location;
    }

    public void increaseEmptyDocks(int stationId) throws SQLException {
        int currEmptyDocks = getEmptyDocksByStationId(stationId)+1;
        String sql = "UPDATE Station SET  EmptyDocks="+ currEmptyDocks +" WHERE Id='"+stationId+"';";
        stm = ECOPARK.getConnection().createStatement();
        stm.executeUpdate(sql);

    }



}
