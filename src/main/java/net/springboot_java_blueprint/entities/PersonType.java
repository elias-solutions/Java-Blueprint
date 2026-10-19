package net.springboot_java_blueprint.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum PersonType {
    @JsonProperty("Freelancer") FREELANCER,
    @JsonProperty("Employee") EMPLOYEE
}
