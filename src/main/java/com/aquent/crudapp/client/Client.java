package com.aquent.crudapp.client;

import com.aquent.crudapp.person.Person;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public class Client {

    private Integer clientId;

    @NotNull
    @Size(min = 1, max = 100, message = "Company name is required with maximum length of 100")
    private String companyName;

    @Size(max = 100, message = "Website URI should have maximum length of 100")
    private String websiteUri;

    @Size(max = 20, message = "Phone number should have maximum length of 20")
    private String phoneNumber;

    @Size(max = 100, message = "Address should have maximum length of 100")
    private String address;

    @Size(max = 10, message = "Contacts list should not exceed 10 elements")
    private List<Integer> contacts;

    // Getters and setters

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getWebsiteUri() {
        return websiteUri;
    }

    public void setWebsiteUri(String websiteUri) {
        this.websiteUri = websiteUri;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Integer> getContacts() {
        return contacts;
    }

    public void setContacts(List<Integer> contacts) {
        this.contacts = contacts;
    }
}
