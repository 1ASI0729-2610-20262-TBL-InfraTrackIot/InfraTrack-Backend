package com.techtitans.infratrack.platform.fleet.application.acl;

import com.techtitans.infratrack.platform.fleet.application.queryservices.IotNodeQueryService;
import com.techtitans.infratrack.platform.fleet.application.queryservices.MachineryQueryService;
import com.techtitans.infratrack.platform.fleet.domain.model.aggregates.IotNode;
import com.techtitans.infratrack.platform.fleet.domain.model.queries.GetIotNodeByIdQuery;
import com.techtitans.infratrack.platform.fleet.domain.model.queries.GetMachineryByIdQuery;
import com.techtitans.infratrack.platform.fleet.domain.repositories.IotNodeRepository;
import com.techtitans.infratrack.platform.fleet.domain.repositories.MachineryRepository;
import com.techtitans.infratrack.platform.fleet.interfaces.acl.FleetContextFacade;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FleetContextFacadeImpl implements FleetContextFacade {

    private final MachineryQueryService machineryQueryService;
    private final IotNodeQueryService iotNodeQueryService;
    private final MachineryRepository machineryRepository;
    private final IotNodeRepository iotNodeRepository;

    public FleetContextFacadeImpl(
            MachineryQueryService machineryQueryService,
            IotNodeQueryService iotNodeQueryService,
            MachineryRepository machineryRepository,
            IotNodeRepository iotNodeRepository) {
        this.machineryQueryService = machineryQueryService;
        this.iotNodeQueryService = iotNodeQueryService;
        this.machineryRepository = machineryRepository;
        this.iotNodeRepository = iotNodeRepository;
    }

    @Override
    public boolean machineryExists(Long machineryId) {
        return machineryQueryService.handle(new GetMachineryByIdQuery(machineryId)).isPresent();
    }

    @Override
    public String fetchPlateNumberByMachineryId(Long machineryId) {
        return machineryQueryService.handle(new GetMachineryByIdQuery(machineryId))
                .map(m -> m.getPlateNumber())
                .orElse("");
    }

    @Override
    public Long fetchMachineryIdByPlateNumber(String plateNumber) {
        return machineryRepository.findByPlateNumber(plateNumber)
                .map(m -> m.getId())
                .orElse(0L);
    }

    @Override
    public boolean iotNodeExists(Long iotNodeId) {
        return iotNodeQueryService.handle(new GetIotNodeByIdQuery(iotNodeId)).isPresent();
    }

    @Override
    public Long fetchMachineryIdByIotNodeId(Long iotNodeId) {
        return iotNodeQueryService.handle(new GetIotNodeByIdQuery(iotNodeId))
                .map(n -> n.getMachineryId())
                .orElse(0L);
    }

    @Override
    public Long fetchIotNodeIdByNodeIdentifier(String nodeIdentifier) {
        return iotNodeRepository.findByNodeIdentifier(nodeIdentifier)
                .map(IotNode::getId)
                .orElse(0L);
    }

    @Override
    public Optional<FleetMachineryView> fetchMachineryView(Long machineryId) {
        return machineryQueryService.handle(new GetMachineryByIdQuery(machineryId))
                .map(m -> new FleetMachineryView(
                        m.getId(),
                        m.getPlateNumber(),
                        m.getModel(),
                        m.getBrand(),
                        m.getCurrentStatus().toApiValue()));
    }

    @Override
    public Optional<String> fetchNodeIdentifierByMachineryId(Long machineryId) {
        return iotNodeRepository.findByMachineryId(machineryId)
                .map(IotNode::getNodeIdentifier);
    }
}
