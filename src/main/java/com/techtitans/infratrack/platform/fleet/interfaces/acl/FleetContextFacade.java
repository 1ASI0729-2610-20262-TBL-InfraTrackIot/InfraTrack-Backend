package com.techtitans.infratrack.platform.fleet.interfaces.acl;

import java.util.Optional;

/**
 * Anti-corruption facade for other bounded contexts (monitoring, site-management).
 */
public interface FleetContextFacade {

    record FleetMachineryView(
            Long id,
            String plateNumber,
            String model,
            String brand,
            String currentStatus
    ) {
    }

    boolean machineryExists(Long machineryId);
    String fetchPlateNumberByMachineryId(Long machineryId);
    Long fetchMachineryIdByPlateNumber(String plateNumber);
    boolean iotNodeExists(Long iotNodeId);
    Long fetchMachineryIdByIotNodeId(Long iotNodeId);
    Long fetchIotNodeIdByNodeIdentifier(String nodeIdentifier);
    Optional<FleetMachineryView> fetchMachineryView(Long machineryId);
    Optional<String> fetchNodeIdentifierByMachineryId(Long machineryId);
}
