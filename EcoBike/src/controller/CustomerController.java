package controller;

import entity.customer.Customer;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerController {

    private Customer customer;

    public CustomerController() throws SQLException, IOException {
        this.customer = new Customer();
    }

    public Customer getCustomerById(int id) throws SQLException {
        return customer.getCustomerById(id);
    }

    public int login(String code, String pass) throws SQLException {
        return customer.login(code, pass);
    }

    /**
     * Validate form thông tin account (tên không đc trống)
     * @param customer
     * @return
     */
    public boolean validateFormAccount(Customer customer) {
        if(customer.getName().length() > 0) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Validate form thông tin thêm account (tên, mã, pass không đc trống)
     * @param customer
     * @return
     */
    public boolean validateFormAddAccount(Customer customer) throws SQLException {
        if((customer.getName().length() <= 0 || customer.getCode().length() <= 0 || customer.getPassword().length() <= 0) ||
                (customer.getCode().length()>0 && this.customer.getCustomerByCode(customer.getCode()) != null)) {
            return false;
        }
        else {
            return true;
        }
    }

    public boolean editUserInfor(Customer customer) throws SQLException {
        return customer.editUserInfor(customer);
    }

    public boolean addCustomer(Customer customer) throws SQLException {
        return customer.addCustomer(customer);
    }

    public int getRoleUser(int id) throws SQLException {
        return customer.getRoleUser(id);
    }

    public String getNameUser(int id) throws SQLException {
        return customer.getNameUser(id);
    }

    public ArrayList getAllCustomer() throws SQLException {
        return customer.getAllCustomer();
    }

    public boolean deleteUserById(int id) throws SQLException {
        return customer.deleteUserById(id);
    }
}
