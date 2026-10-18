package net.springboot_java_blueprint.entities;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.util.UUID;

@MappedSuperclass
public abstract class EntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private UUID version;

    @PrePersist
    @PreUpdate
    void assignNewVersion() {
        version = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    public UUID getVersion() {
        return version;
    }
}
