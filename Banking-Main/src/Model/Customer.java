package Model;

//one customer many accounts
public class Customer {
    Long customerId;
    String name;
    String phone;
    String panCard;
    String aadhaar;
    String address;

    public enum KycStatus {
        PENDING,
        VERIFIED,
        REJECTED
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setPanCard(String panCard) {
        this.panCard = panCard;
    }

    public void setAadhaar(String aadhaar) {
        this.aadhaar = aadhaar;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
