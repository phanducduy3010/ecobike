package controller;

import entity.customer.Customer;

import java.io.IOException;
import java.sql.SQLException;

public class AccountController {
    public CustomerController getCustomerController() {
        return customerController;
    }

    public void setCustomerController(CustomerController customerController) {
        this.customerController = customerController;
    }

    private CustomerController customerController;

    public AccountController() throws SQLException, IOException {
        customerController = new CustomerController();
    }

    public Customer getCustomerById(int id) throws SQLException {
        return customerController.getCustomerById(id);
    }

    public boolean validateFormAccount(Customer customer) {
        return customerController.validateFormAccount(customer);
    }

    public boolean validateFormAddAccount(Customer customer) throws SQLException {
        return customerController.validateFormAddAccount(customer);
    }

    public boolean editUserInfor(Customer customer) throws SQLException {
        return customerController.editUserInfor(customer);
    }

    public boolean addCustomer(Customer customer) throws SQLException {
        return customerController.addCustomer(customer);
    }

    public boolean deleteUserById(int id) throws SQLException {
        return customerController.deleteUserById(id);
    }
}
