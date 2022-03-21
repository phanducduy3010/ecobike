package controller;


import java.io.IOException;
import java.sql.SQLException;

public class LoginController {

    public CustomerController getCustomerController() {
        return customerController;
    }

    public void setCustomerController(CustomerController customerController) {
        this.customerController = customerController;
    }

    private CustomerController customerController;

    public LoginController() throws SQLException, IOException {
        customerController = new CustomerController();
    }

    public int login(String code, String pass) throws SQLException {
        return this.customerController.login(code, pass);
    }
}
