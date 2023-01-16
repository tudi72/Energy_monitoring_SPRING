package ro.tuc.ds2020.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ro.tuc.ds2020.entities.Device;
import ro.tuc.ds2020.entities.MyUser;

public interface DeviceRepository extends JpaRepository<Device,Long> {

    Device findDeviceById(Long id);


    @Query(
            value = "SELECT * FROM device " +
                    "where LOWER(description) " +
                    "REGEXP :description",
            nativeQuery = true
    )
    Device findDeviceByDescription(@Param("description")String description);

}
