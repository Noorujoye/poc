package model;

import model.enums.KycStatus;

public class Customer {
    private Long customerId;
    private String name;
    private String phone;
    private String panCard;
    private String aadhaar;
    private String address;
    private KycStatus kycStatus;

    public Customer(String name,
                    String phone,
                    String panCard,
                    String aadhaar,
                    String address) {

        this.name = name;
        this.phone = phone;
        this.panCard = panCard;
        this.aadhaar = aadhaar;
        this.address = address;
        this.kycStatus = KycStatus.PENDING;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getPanCard() {
        return panCard;
    }

    public String getAadhaar() {
        return aadhaar;
    }

    public void setAadhaar(String aadhaar) {
        this.aadhaar = aadhaar;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public KycStatus getKycStatus() {
        return kycStatus;
    }

    public void setKycStatus(KycStatus kycStatus) {
        this.kycStatus = kycStatus;
    }
}
