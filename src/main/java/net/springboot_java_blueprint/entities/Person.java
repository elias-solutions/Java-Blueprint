package net.springboot_java_blueprint.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

    @Column(nullable = false, length = STRING_LENGTH)
    private String firstName;

    @Column(nullable = false, length = STRING_LENGTH)
    private String lastName;

    @Column(nullable = false)
    private LocalDate birthday;

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
