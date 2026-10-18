package net.springboot_java_blueprint.controllers.v2;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("personsControllerV2")
@RequestMapping("/api/v2/persons")
public class PersonsController {

    @GetMapping
    public String getPersons() {
        return "Jonas Elias";
    }
}
