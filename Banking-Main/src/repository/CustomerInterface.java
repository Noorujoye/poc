package repository;

import Model.Customer;

public interface CustomerInterface {
    Long save(Customer customer);
    Customer findByAccount(String accountNumber);
}


/*
save customer to db
find customer from db
delete and update customer

a single mobile number can be connected to two mobile phones , I have to implement this
extra features
 */