package dto;

import model.Customer;

public class RegistrationRequest {
    /*
    It collects registration data
    RegistrationRequest, it is a request object , after creation should not be changed , that why final
    no setter , no one can make changes only getters
    customer is mutable because setName , setPhone exist
    But DTOs are usually Immutable, be'cuz they only carry data.

     */
    private final Customer customer;
    private final String username;
    private final String password;

    public RegistrationRequest(
            Customer customer,
            String username,
            String password) {
        this.customer = customer;
        this.username = username;
        this.password = password;
    }

    public Customer getCustomer() {
        return customer;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
