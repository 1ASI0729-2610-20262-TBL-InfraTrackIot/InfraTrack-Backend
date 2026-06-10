package com.techtitans.infratrack.platform.monitoring.application.internal.outboundservices.acl;

import com.techtitans.infratrack.platform.fleet.interfaces.acl.FleetContextFacade;
import org.springframework.stereotype.Service;

@Service
public class ExternalFleetService {

    private final FleetContextFacade fleetContextFacade;

    public ExternalFleetService(FleetContextFacade fleetContextFacade) {
        this.fleetContextFacade = fleetContextFacade;
    }

    public boolean iotNodeExists(Long nodeId) {
        return nodeId != null && nodeId > 0 && fleetContextFacade.iotNodeExists(nodeId);
    }

    public boolean machineryExists(Long machineryId) {
        return machineryId != null && machineryId > 0 && fleetContextFacade.machineryExists(machineryId);
    }
}
