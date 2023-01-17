package ro.tuc.ds2020.dtos.builders;

import java.sql.Timestamp;
import java.time.Instant;

import lombok.extern.slf4j.Slf4j;
import ro.tuc.ds2020.dtos.ConsumptionDTO;
import ro.tuc.ds2020.entities.EnergyConsumption;

public class EnergyConsumptionBuilder {
    public static EnergyConsumption toEntity(ConsumptionDTO dto){
        Timestamp timestamp = Timestamp.from(Instant.now());        
        return new EnergyConsumption(
            0L,
            timestamp,
            dto.getKWh(),
            null
        );
    }
}
