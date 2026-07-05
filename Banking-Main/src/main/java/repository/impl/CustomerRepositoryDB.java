package repository.impl;

import exception.CustomerCreationException;
import exception.DatabaseException;
import repository.CustomerRepository;
import model.Customer;

import java.sql.*;

public class CustomerRepositoryDB implements CustomerRepository {
    @Override
    public Long save(Connection connection , Customer customer) {
        String saveToDB = "INSERT INTO CUSTOMERS (name , phone , pan_card, aadhaar , address ,  kyc_status) VALUES (? , ? , ? , ? , ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(saveToDB , Statement.RETURN_GENERATED_KEYS)){
            ps.setString(1 , customer.getName());
            ps.setString(2 , customer.getPhone());
            ps.setString(3 , customer.getPanCard());
            ps.setString(4 , customer.getAadhaar());
            ps.setString(5 , customer.getAddress());
            ps.setString(6 , customer.getKycStatus().name());
            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) throw new CustomerCreationException("Customer insertion failed");
            try (ResultSet rs =  ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getLong(1);
                }
                throw new CustomerCreationException("Unable to fetch generated customer id");
            }
        } catch (SQLException e) {
            throw new DatabaseException("Unable to save customer",e);
        }
    }

    @Override
    public Customer findByPhone(Connection connection , String phone) {
        //String customerByPhone = "SELECT * FROM CUSTOMERS WHERE phone = ?";
        return null;
    }
}
