package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class ListCustomerController {
    public CustomerController getCustomerController() {
        return customerController;
    }

    public void setCustomerController(CustomerController customerController) {
        this.customerController = customerController;
    }

    private CustomerController customerController;

    public ListCustomerController() throws SQLException, IOException {
        customerController = new CustomerController();
    }

    public String getNameUser(int id) throws SQLException {
        return customerController.getNameUser(id);
    }

    public ArrayList getAllCustomer() throws SQLException {
        return customerController.getAllCustomer();
    }
}
