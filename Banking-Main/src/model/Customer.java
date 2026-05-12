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
                    String address, Enum kycStatus) {

        this.name = name;
        this.phone = phone;
        this.panCard = panCard;
        this.aadhaar = aadhaar;
        this.address = address;
        this.kycStatus = KycStatus.VERIFIED;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getCustomerId() {
        return customerId;
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

    public String getAddress() {
        return address;
    }

    public KycStatus getKycStatus() {
        return kycStatus;
    }


    public void setKycStatus(KycStatus kycStatus) {
        this.kycStatus = kycStatus;
    }
}
