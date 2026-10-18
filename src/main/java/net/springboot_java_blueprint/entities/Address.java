package net.springboot_java_blueprint.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
public class Address extends EntityBase {
    @Column(nullable = false, length = Person.STRING_LENGTH)
    private String street;

    @Column(nullable = false, length = Person.STRING_LENGTH)
    private String number;

    @Column(nullable = false, length = Person.STRING_LENGTH)
    private String city;

    @Column(nullable = false, length = Person.STRING_LENGTH)
    private String postalCode;

    private UUID createdId;
    private OffsetDateTime createdDate;
    private UUID modifiedId;
    private OffsetDateTime modifiedDate;

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public UUID getCreatedId() {
        return createdId;
    }

    public void setCreatedId(UUID createdId) {
        this.createdId = createdId;
    }

    public OffsetDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(OffsetDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public UUID getModifiedId() {
        return modifiedId;
    }

    public void setModifiedId(UUID modifiedId) {
        this.modifiedId = modifiedId;
    }

    public OffsetDateTime getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(OffsetDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
    }
}
