package ro.tuc.ds2020.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ro.tuc.ds2020.dtos.ConsumptionDTO;
import ro.tuc.ds2020.dtos.DeviceDTO;
import ro.tuc.ds2020.dtos.builders.DeviceBuilder;
import ro.tuc.ds2020.dtos.builders.EnergyConsumptionBuilder;
import ro.tuc.ds2020.entities.Device;
import ro.tuc.ds2020.entities.EnergyConsumption;
import ro.tuc.ds2020.services.DeviceService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping(value = "/device")
public class DeviceController {

    private final DeviceService deviceService;

    @GetMapping()
    public ResponseEntity<List<DeviceDTO>> getDevices() {
        List<DeviceDTO> dtos = deviceService
                .getDevices()
                .stream()
                .map(DeviceBuilder::toDeviceDTO)
                .collect(Collectors.toList());

        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<Long> insertDevice(@Valid @RequestBody DeviceDTO deviceDTO) {

        System.out.print("Description" + deviceDTO.getDescription());
        System.out.print("Address " + deviceDTO.getAddress());
        Device devicetoInsertObj = DeviceBuilder.toEntity(deviceDTO);
        Device deviceInserted = deviceService.saveDevice(devicetoInsertObj);
        Long deviceID = deviceInserted.getId();
        return new ResponseEntity<>(deviceID, HttpStatus.CREATED);
    }

    @GetMapping(value = "/{name}")
    public ResponseEntity<DeviceDTO> getDevice(@PathVariable("name") String description) {

        Device device = deviceService.getDevice(description);
        DeviceDTO deviceDTO = DeviceBuilder.toDeviceDTO(device);
        return new ResponseEntity<>(deviceDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/kwh/{name}")
    public ResponseEntity<String> getDeviceKWh(@PathVariable("name") Long deviceID) {
        String hehe = "";
        try{
            log.info("[DeviceController.getDevice] : ID "  + deviceID);
            Device device = deviceService.getDevice(deviceID);
            hehe = Double.toString(device.getMaxHourlyEnergyCons());
            // hehe = device.getMaxHourlyEnergyCons();
        }
        catch(Exception e){
            log.error("[DeviceController.getDevice] : " + e.getMessage());
        }
        return new ResponseEntity<>(hehe, HttpStatus.OK);
    }

    @PostMapping(value = "/kwh")
    public ResponseEntity<EnergyConsumption> updateEnergyConsumption(@RequestBody ConsumptionDTO dto){

        EnergyConsumption consumption = EnergyConsumptionBuilder.toEntity(dto);
        Device device = deviceService.getDevice(dto.getDeviceID());
        consumption.setDevice(device);

        deviceService.addEnergyToDevice(device, consumption);

        log.info("[DeviceController] : Device " + device.getDescription()+" received consumption");

        return new ResponseEntity<EnergyConsumption>(consumption, HttpStatus.OK);
    }

    @PostMapping(value = "/{name}")
    public ResponseEntity<DeviceDTO> updateDevice(@PathVariable("name") String description, @Valid @RequestBody DeviceDTO deviceDTO){
        Device device = deviceService.getDevice(description);
        Device updatedDevice = deviceService.updateDevice(device,deviceDTO);
        DeviceDTO updatedDeviceDTO = DeviceBuilder.toDeviceDTO(updatedDevice);
        return new ResponseEntity<>(updatedDeviceDTO,HttpStatus.OK);
    }

    @DeleteMapping(value = "/{description}")
    public ResponseEntity<Long> deleteDevice(@PathVariable("description") String description){
        Long deviceID = deviceService.deleteDevice(description);
        return new ResponseEntity<>(deviceID,HttpStatus.ACCEPTED);
    }

}
