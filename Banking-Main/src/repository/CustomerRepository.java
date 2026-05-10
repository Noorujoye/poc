package repository;

import model.Customer;

import java.sql.Connection;

public interface CustomerRepository {
    Long save(Connection connection , Customer customer);
    Customer findByPhone(Connection connection , String phone);
}

//Service controls transaction connection, that's why I added connection parameter















/*
save customer to db
find customer from db
delete and update customer

a single mobile number can be connected to two mobile phones , I have to implement this
extra features
 */