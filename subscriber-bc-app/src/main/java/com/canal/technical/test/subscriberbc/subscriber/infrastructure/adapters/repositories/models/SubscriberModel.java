package com.canal.technical.test.subscriberbc.subscriber.infrastructure.adapters.repositories.models;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "SUBSCRIBER")
public class SubscriberModel {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    @Column(name = "SUBSCRIBERID", unique = true)
    private String subscriberId;

    @Column(name = "FNAME")
    private String firstName;

    @Column(name = "LNAME")
    private String lastName;

    @Column(name = "MAIL", unique = true)
    private String mail;

    @Column(name = "PHONE", unique = true)
    private String phoneNumber;

    @Column(name = "ISACTIV")
    private boolean isActive;

    public SubscriberModel() {
    }

    public SubscriberModel(Long id, String subscriberId, String firstName, String lastName, String mail, String phoneNumber, boolean isActive) {
        this.id = id;
        this.subscriberId = subscriberId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mail = mail;
        this.phoneNumber = phoneNumber;
        this.isActive = isActive;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long subscriberId) {
        this.id = subscriberId;
    }

    public String getSubscriberId() {
        return subscriberId;
    }

    public void setSubscriberId(String subscriberId) {
        this.subscriberId = subscriberId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubscriberModel that = (SubscriberModel) o;
        return isActive == that.isActive && Objects.equals(id, that.id) && Objects.equals(subscriberId, that.subscriberId) && Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && Objects.equals(mail, that.mail) && Objects.equals(phoneNumber, that.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, subscriberId, firstName, lastName, mail, phoneNumber, isActive);
    }

    @Override
    public String toString() {
        return "SubscriberModel{" +
                "id=" + id +
                ", subscriberId='" + subscriberId + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", mail='" + mail + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", isActive=" + isActive +
                '}';
    }
}
