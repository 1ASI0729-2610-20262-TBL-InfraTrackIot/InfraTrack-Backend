package com.techtitans.infratrack.platform.fleet.interfaces.rest;

import com.techtitans.infratrack.platform.fleet.application.queryservices.MachineryQueryService;
import com.techtitans.infratrack.platform.fleet.domain.model.queries.GetAllMachineryQuery;
import com.techtitans.infratrack.platform.fleet.domain.model.queries.GetMachineryByIdQuery;
import com.techtitans.infratrack.platform.fleet.interfaces.rest.resources.MachineryResource;
import com.techtitans.infratrack.platform.fleet.interfaces.rest.transform.MachineryResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/machinery", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Machinery", description = "Fleet machinery (transports) management")
public class MachineryController {

    private final MachineryQueryService machineryQueryService;

    public MachineryController(MachineryQueryService machineryQueryService) {
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
}
