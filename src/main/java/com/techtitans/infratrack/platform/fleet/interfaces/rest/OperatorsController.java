package com.techtitans.infratrack.platform.fleet.interfaces.rest;

import com.techtitans.infratrack.platform.fleet.application.commandservices.FleetOperatorCommandService;
import com.techtitans.infratrack.platform.fleet.application.queryservices.FleetOperatorQueryService;
import com.techtitans.infratrack.platform.fleet.domain.model.queries.GetAllFleetOperatorsQuery;
import com.techtitans.infratrack.platform.fleet.domain.model.queries.GetFleetOperatorByIdQuery;
import com.techtitans.infratrack.platform.fleet.interfaces.rest.resources.CreateOperatorResource;
import com.techtitans.infratrack.platform.fleet.interfaces.rest.resources.OperatorResource;
import com.techtitans.infratrack.platform.fleet.interfaces.rest.transform.OperatorResourceFromEntityAssembler;
import com.techtitans.infratrack.platform.shared.interfaces.rest.transform.ResponseEntityAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/operators", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Operators", description = "Fleet operator (driver) management")
public class OperatorsController {

    private final FleetOperatorCommandService fleetOperatorCommandService;
    private final FleetOperatorQueryService fleetOperatorQueryService;

    public OperatorsController(
            FleetOperatorCommandService fleetOperatorCommandService,
            FleetOperatorQueryService fleetOperatorQueryService) {
        this.fleetOperatorCommandService = fleetOperatorCommandService;
        this.fleetOperatorQueryService = fleetOperatorQueryService;
    }

    @GetMapping
    public ResponseEntity<List<OperatorResource>> getAllOperators() {
        var items = fleetOperatorQueryService.handle(new GetAllFleetOperatorsQuery()).stream()
                .map(OperatorResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(items);
    }

    @GetMapping("/{operatorId}")
    public ResponseEntity<OperatorResource> getOperatorById(@PathVariable Long operatorId) {
        return fleetOperatorQueryService.handle(new GetFleetOperatorByIdQuery(operatorId))
                .map(OperatorResourceFromEntityAssembler::toResourceFromEntity)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
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
