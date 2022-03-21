package config;

import java.text.SimpleDateFormat;

/**
 * @author nguyen
 * @create_date 05/01/2022
 */
public interface ServiceConfig {

  final long BIKE_DEPOSIT = 1000000;
  final long EBIKE_DEPOSIT = 200000;
  final long TWIN_BIKE_DEPOSIT = 300000;

  public static String DATE_TIME_PATTERN = "yyyy-MM-dd hh:mm";

  interface ScreenTittle{
    final String HOME_SCREEN = "Home";
    final String TURNBACK_BIKE = "Turn back bike";
    final String BILL_SCREEN = "Bill";
    final String BIKE_RENTING = "Bike renting";
  }
}
