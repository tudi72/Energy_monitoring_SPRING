package ro.tuc.ds2020.services;

import lombok.RequiredArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.tuc.ds2020.dtos.DeviceDTO;
import ro.tuc.ds2020.entities.Device;
import ro.tuc.ds2020.entities.EnergyConsumption;
import ro.tuc.ds2020.repositories.DeviceRepository;
import ro.tuc.ds2020.repositories.EnergyConsumptionRepository;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class DeviceServiceImp implements DeviceService{

    private final DeviceRepository deviceRepository;
    private final EnergyConsumptionRepository energyRepository;
    private final Logger log = Logger.getLogger(this.getClass());

    @Override
    public Device saveDevice(Device device) {
        log.info("Saving new device " + device.getDescription());
        return deviceRepository.save(device);
    }

    @Override
    public Device getDevice(Long deviceID){
        return deviceRepository.findDeviceById(deviceID);
    }

    @Override
    public Device getDevice(String description) {
        description = "[a-z]*[:space:]?" + description.toLowerCase() + "[:space:]?[a-z]*";

        log.info("Retrieving device by ID " + description);
        return deviceRepository.findDeviceByDescription(description);
    }

    @Override
    public List<Device> getDevices() {
        log.info("Fetching all devices from database");
        return deviceRepository.findAll();
    }

    @Override
    public Device updateDevice(Device device,DeviceDTO deviceDTO) {

        device.setAddress(deviceDTO.getAddress());
        device.setDescription(deviceDTO.getDescription());
        device.setMaxHourlyEnergyCons(deviceDTO.getMaxHourlyEnergyCons());

        device = deviceRepository.save(device);
        log.info("Update Device " + device.getDescription());
        return device;
    }

    @Override
    public Long deleteDevice(String description) {
        description = "[a-z]*[:space:]?" + description.toLowerCase() + "[:space:]?[a-z]*";
        Device device = deviceRepository.findDeviceByDescription(description);
        Long deviceID = null;
        if(device != null){
            deviceID = device.getId();
            deviceRepository.delete(device);
            log.info("Deleting device with ID " + deviceID);
        }
        return deviceID;
    }

    @Override
    public Device addEnergyToDevice(Device device,EnergyConsumption en){
        en = energyRepository.save(en);
        Collection<EnergyConsumption> list = device.getConsumptions();
        list.add(en);
        device.setConsumptions(list);
        deviceRepository.save(device);
        return device;
    }
}
