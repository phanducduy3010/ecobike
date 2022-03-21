package views.screen.payment.interfacefee;

public class DepositFeeCalculation implements IDepositFee{


    @Override
    public float calculateDepositFee(String vehicleType) {
        if (vehicleType.equals("Xe đạp")){
            return 400000;
        }else if(vehicleType.equals("Xe điện")){
            return 700000;
        }else if(vehicleType.equals("Xe đạp đôi")){
            return 550000;
        }
        return 0;
    }
}
