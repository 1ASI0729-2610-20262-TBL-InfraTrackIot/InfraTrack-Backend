package com.techtitans.infratrack.platform.fleet.interfaces.rest;

import com.techtitans.infratrack.platform.fleet.application.commandservices.FleetOperatorCommandService;
import com.techtitans.infratrack.platform.fleet.interfaces.rest.resources.CreateOperatorResource;
import com.techtitans.infratrack.platform.fleet.interfaces.rest.transform.OperatorResourceFromEntityAssembler;
import com.techtitans.infratrack.platform.shared.interfaces.rest.transform.ResponseEntityAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/operators", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Operators", description = "Fleet operator (driver) management")
public class OperatorsController {

    private final FleetOperatorCommandService fleetOperatorCommandService;

    public OperatorsController(FleetOperatorCommandService fleetOperatorCommandService) {
        this.fleetOperatorCommandService = fleetOperatorCommandService;
    }

    @PostMapping
    public ResponseEntity<?> createOperator(@RequestBody CreateOperatorResource resource) {
        var command = OperatorResourceFromEntityAssembler.toCreateCommandFromResource(resource);
        var result = fleetOperatorCommandService.handle(command);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                OperatorResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED
        );
    }
}
