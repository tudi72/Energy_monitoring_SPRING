package ro.tuc.ds2020.dtos.builders;

import ro.tuc.ds2020.dtos.DeviceDTO;
import ro.tuc.ds2020.entities.Device;
import ro.tuc.ds2020.entities.EnergyConsumption;

import java.util.*;

public class DeviceBuilder {

    public static DeviceDTO toDeviceDTO(Device device){
        return new DeviceDTO(
                device.getId(),
                device.getDescription(),
                device.getMaxHourlyEnergyCons(),
                device.getAddress()
        );
    }
    public static Map.Entry<String,Double> toDeviceConsumptionDTO(EnergyConsumption energy, Date day){
            Calendar dateCal = Calendar.getInstance();
            dateCal.setTime(energy.getDate());
            Date endDay = dateCal.getTime();
            int hours = day.getHours();
            String hours2 = Integer.toString(hours);
            if(day.equals(dateCal.getTime())){
                return new AbstractMap.SimpleEntry<String,Double>(hours2,energy.getKWh());

            }
            else return new AbstractMap.SimpleEntry<String,Double>(hours2,0.0);
    }
    public static Device toEntity(DeviceDTO deviceDTO){
        return new Device(0l,
                deviceDTO.getDescription(),
                deviceDTO.getAddress(),
                deviceDTO.getMaxHourlyEnergyCons(),
                new ArrayList<>());
    }

}
