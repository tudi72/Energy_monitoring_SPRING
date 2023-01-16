package ro.tuc.ds2020.repositories;

import java.util.Collection;
import ro.tuc.ds2020.entities.EnergyConsumption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EnergyConsumptionRepository  extends JpaRepository<EnergyConsumption,Long> {

    @Query(value="select * from energy_consumption where device_id=:deviceID",nativeQuery=true)
    Collection<EnergyConsumption> findAllByDevice(Long deviceID);

}
