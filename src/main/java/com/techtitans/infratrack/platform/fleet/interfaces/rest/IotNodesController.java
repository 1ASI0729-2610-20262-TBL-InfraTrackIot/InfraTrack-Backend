package com.techtitans.infratrack.platform.fleet.interfaces.rest;

import com.techtitans.infratrack.platform.fleet.application.commandservices.IotNodeCommandService;
import com.techtitans.infratrack.platform.fleet.application.queryservices.IotNodeQueryService;
import com.techtitans.infratrack.platform.fleet.domain.model.commands.LinkIotNodeToMachineryCommand;
import com.techtitans.infratrack.platform.fleet.domain.model.queries.GetAllIotNodesQuery;
import com.techtitans.infratrack.platform.fleet.domain.model.queries.GetIotNodeByIdQuery;
import com.techtitans.infratrack.platform.fleet.interfaces.rest.resources.CreateIotNodeResource;
import com.techtitans.infratrack.platform.fleet.interfaces.rest.resources.IotNodeResource;
import com.techtitans.infratrack.platform.fleet.interfaces.rest.transform.IotNodeResourceFromEntityAssembler;
import com.techtitans.infratrack.platform.shared.interfaces.rest.transform.ResponseEntityAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/iotNodes", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "IoT Nodes", description = "IoT sensor node management")
public class IotNodesController {

    private final IotNodeCommandService iotNodeCommandService;
    private final IotNodeQueryService iotNodeQueryService;

    public IotNodesController(IotNodeCommandService iotNodeCommandService, IotNodeQueryService iotNodeQueryService) {
        this.iotNodeCommandService = iotNodeCommandService;
        this.iotNodeQueryService = iotNodeQueryService;
    }

    @GetMapping
    public ResponseEntity<List<IotNodeResource>> getAllIotNodes() {
        var items = iotNodeQueryService.handle(new GetAllIotNodesQuery()).stream()
                .map(IotNodeResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(items);
    }

    @GetMapping("/{iotNodeId}")
    public ResponseEntity<IotNodeResource> getIotNodeById(@PathVariable Long iotNodeId) {
        return iotNodeQueryService.handle(new GetIotNodeByIdQuery(iotNodeId))
                .map(IotNodeResourceFromEntityAssembler::toResourceFromEntity)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createIotNode(@RequestBody CreateIotNodeResource resource) {
        var command = IotNodeResourceFromEntityAssembler.toCreateCommandFromResource(resource);
        var result = iotNodeCommandService.handle(command);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                IotNodeResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{iotNodeId}/machinery/{machineryId}")
    public ResponseEntity<?> linkIotNodeToMachinery(@PathVariable Long iotNodeId, @PathVariable Long machineryId) {
        var result = iotNodeCommandService.handle(new LinkIotNodeToMachineryCommand(iotNodeId, machineryId));
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                IotNodeResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.OK
        );
    }
}
