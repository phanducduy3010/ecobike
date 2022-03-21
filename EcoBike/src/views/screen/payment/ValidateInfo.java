package views.screen.payment;

public class ValidateInfo {
    public ValidateInfo(){

    }

    public boolean validateIDCard(String cardID){
        for(int i=0; i<cardID.length(); i++){
            int so = cardID.charAt(i);
            if (so <48 || so >57){
                return false;
            }
        }
        return true;
    }

}
