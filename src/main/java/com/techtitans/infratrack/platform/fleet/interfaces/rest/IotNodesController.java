package com.techtitans.infratrack.platform.fleet.interfaces.rest;

import com.techtitans.infratrack.platform.fleet.application.commandservices.IotNodeCommandService;
import com.techtitans.infratrack.platform.fleet.application.queryservices.IotNodeQueryService;
import com.techtitans.infratrack.platform.fleet.domain.model.commands.LinkIotNodeToMachineryCommand;
import com.techtitans.infratrack.platform.fleet.domain.model.queries.GetAllIotNodesQuery;
import com.techtitans.infratrack.platform.fleet.domain.model.queries.GetIotNodeByIdQuery;
import com.techtitans.infratrack.platform.fleet.interfaces.rest.resources.CreateIotNodeResource;
import com.techtitans.infratrack.platform.fleet.interfaces.rest.resources.IotNodeResource;
import com.techtitans.infratrack.platform.fleet.interfaces.rest.transform.IotNodeResourceFromEntityAssembler;
import com.techtitans.infratrack.platform.shared.interfaces.rest.documentation.ApiDocumentation;
import com.techtitans.infratrack.platform.shared.interfaces.rest.transform.ResponseEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/iot-nodes", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "IoT Nodes", description = "IoT sensor node management")
public class IotNodesController {

    private final IotNodeCommandService iotNodeCommandService;
    private final IotNodeQueryService iotNodeQueryService;

    public IotNodesController(IotNodeCommandService iotNodeCommandService, IotNodeQueryService iotNodeQueryService) {
        this.iotNodeCommandService = iotNodeCommandService;
        this.iotNodeQueryService = iotNodeQueryService;
    }

    @GetMapping
    @Operation(
            summary = "List IoT nodes",
            description = "Returns all registered sensor nodes. " + ApiDocumentation.AUTH_STEPS,
            security = @SecurityRequirement(name = ApiDocumentation.SECURITY_SCHEME))
    @ApiResponse(responseCode = "200", description = "IoT nodes retrieved",
            content = @Content(schema = @Schema(implementation = IotNodeResource.class)))
    public ResponseEntity<List<IotNodeResource>> getAllIotNodes() {
        var items = iotNodeQueryService.handle(new GetAllIotNodesQuery()).stream()
                .map(IotNodeResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(items);
    }

    @GetMapping("/{iotNodeId}")
    @Operation(
            summary = "Get IoT node by ID",
            description = "Example: `GET /api/v1/iot-nodes/1`. " + ApiDocumentation.AUTH_STEPS,
            security = @SecurityRequirement(name = ApiDocumentation.SECURITY_SCHEME))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "IoT node found",
                    content = @Content(schema = @Schema(implementation = IotNodeResource.class))),
            @ApiResponse(responseCode = "404", description = "IoT node not found")
    })
    public ResponseEntity<IotNodeResource> getIotNodeById(
            @Parameter(example = "1") @PathVariable Long iotNodeId) {
        return iotNodeQueryService.handle(new GetIotNodeByIdQuery(iotNodeId))
                .map(IotNodeResourceFromEntityAssembler::toResourceFromEntity)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(
            summary = "Create IoT node",
            description = "Example body: `{\"nodeIdentifier\":\"IOT-001\",\"connectionStatus\":\"online\","
                    + "\"machineryId\":1}`. " + ApiDocumentation.AUTH_STEPS,
            security = @SecurityRequirement(name = ApiDocumentation.SECURITY_SCHEME))
    @ApiResponse(responseCode = "201", description = "IoT node created",
            content = @Content(schema = @Schema(implementation = IotNodeResource.class)))
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
    @Operation(
            summary = "Link IoT node to machinery",
            description = "REST nested assignment. Example: `PUT /api/v1/iot-nodes/1/machinery/2` with empty body. "
                    + ApiDocumentation.AUTH_STEPS,
            security = @SecurityRequirement(name = ApiDocumentation.SECURITY_SCHEME))
    @ApiResponse(responseCode = "200", description = "IoT node linked",
            content = @Content(schema = @Schema(implementation = IotNodeResource.class)))
    public ResponseEntity<?> linkIotNodeToMachinery(
            @Parameter(example = "1") @PathVariable Long iotNodeId,
            @Parameter(example = "2") @PathVariable Long machineryId) {
        var result = iotNodeCommandService.handle(new LinkIotNodeToMachineryCommand(iotNodeId, machineryId));
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                IotNodeResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.OK
        );
    }
}
