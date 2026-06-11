package com.techtitans.infratrack.platform.fleet.interfaces.rest;

import com.techtitans.infratrack.platform.fleet.application.commandservices.MachineryCommandService;
import com.techtitans.infratrack.platform.fleet.application.queryservices.MachineryQueryService;
import com.techtitans.infratrack.platform.fleet.domain.model.queries.GetAllMachineryQuery;
import com.techtitans.infratrack.platform.fleet.domain.model.queries.GetMachineryByIdQuery;
import com.techtitans.infratrack.platform.fleet.interfaces.rest.resources.CreateMachineryResource;
import com.techtitans.infratrack.platform.fleet.interfaces.rest.resources.MachineryResource;
import com.techtitans.infratrack.platform.fleet.interfaces.rest.resources.UpdateMachineryResource;
import com.techtitans.infratrack.platform.fleet.interfaces.rest.transform.MachineryResourceFromEntityAssembler;
import com.techtitans.infratrack.platform.shared.interfaces.rest.transform.ResponseEntityAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/machinery", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Machinery", description = "Fleet machinery (transports) management")
public class MachineryController {

    private final MachineryCommandService machineryCommandService;
    private final MachineryQueryService machineryQueryService;

    public MachineryController(MachineryCommandService machineryCommandService, MachineryQueryService machineryQueryService) {
        this.machineryCommandService = machineryCommandService;
        this.machineryQueryService = machineryQueryService;
    }

    @GetMapping
    public ResponseEntity<List<MachineryResource>> getAllMachinery() {
        var items = machineryQueryService.handle(new GetAllMachineryQuery()).stream()
                .map(MachineryResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(items);
    }

    @GetMapping("/{machineryId}")
    public ResponseEntity<MachineryResource> getMachineryById(@PathVariable Long machineryId) {
        return machineryQueryService.handle(new GetMachineryByIdQuery(machineryId))
                .map(MachineryResourceFromEntityAssembler::toResourceFromEntity)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createMachinery(@RequestBody CreateMachineryResource resource) {
        var command = MachineryResourceFromEntityAssembler.toCreateCommandFromResource(resource);
        var result = machineryCommandService.handle(command);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                MachineryResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{machineryId}")
    public ResponseEntity<?> updateMachinery(@PathVariable Long machineryId, @RequestBody UpdateMachineryResource resource) {
        var command = MachineryResourceFromEntityAssembler.toUpdateCommandFromResource(machineryId, resource);
        var result = machineryCommandService.handle(command);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                MachineryResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.OK
        );
    }
}
