package net.springboot_java_blueprint.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderColumn;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class Person extends EntityBase {
    static final int STRING_LENGTH = 200;
    static final int ENUM_LENGTH = 20;
    public static final int DESCRIPTION_MAX_LENGTH = 2000;
    public static final int EMAIL_MAX_LENGTH = 254;
    public static final int PHONE_MAX_LENGTH = 50;

    @Column(nullable = false, length = STRING_LENGTH)
    private String firstName;

    @Column(nullable = false, length = STRING_LENGTH)
    private String lastName;

    private LocalDate birthday;

    @Enumerated(EnumType.STRING)
    @Column(length = ENUM_LENGTH)
    private Gender gender;

    @Column(length = DESCRIPTION_MAX_LENGTH)
    private String description;

    @Column(length = EMAIL_MAX_LENGTH)
    private String email;

    @Column(length = PHONE_MAX_LENGTH)
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(length = ENUM_LENGTH)
    private PersonType type;

    private UUID createdId;
    private OffsetDateTime createdDate;
    private UUID modifiedId;
    private OffsetDateTime modifiedDate;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "person_id", nullable = false)
    @OrderColumn(name = "position")
    private List<Address> addresses = new ArrayList<>();

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

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public PersonType getType() {
        return type;
    }

    public void setType(PersonType type) {
        this.type = type;
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

    public List<Address> getAddresses() {
        return addresses;
    }
}
