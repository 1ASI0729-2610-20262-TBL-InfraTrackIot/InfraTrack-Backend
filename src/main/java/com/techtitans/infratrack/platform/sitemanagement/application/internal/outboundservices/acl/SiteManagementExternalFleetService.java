package com.techtitans.infratrack.platform.sitemanagement.application.internal.outboundservices.acl;

import com.techtitans.infratrack.platform.fleet.interfaces.acl.FleetContextFacade;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SiteManagementExternalFleetService {

    private final FleetContextFacade fleetContextFacade;

    public SiteManagementExternalFleetService(FleetContextFacade fleetContextFacade) {
        this.fleetContextFacade = fleetContextFacade;
    }

    public boolean machineryExists(Long machineryId) {
        return machineryId != null && machineryId > 0 && fleetContextFacade.machineryExists(machineryId);
    }

    public Optional<FleetContextFacade.FleetMachineryView> fetchMachineryView(Long machineryId) {
        if (machineryId == null || machineryId <= 0) {
            return Optional.empty();
        }
        return fleetContextFacade.fetchMachineryView(machineryId);
    }

    public Optional<String> fetchNodeIdentifierByMachineryId(Long machineryId) {
        if (machineryId == null || machineryId <= 0) {
            return Optional.empty();
        }
        return fleetContextFacade.fetchNodeIdentifierByMachineryId(machineryId);
    }

    public Long fetchMachineryIdByPlateNumber(String plateNumber) {
        return fleetContextFacade.fetchMachineryIdByPlateNumber(plateNumber);
    }
}
