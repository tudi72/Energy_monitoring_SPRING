package ro.tuc.ds2020.services;

import ro.tuc.ds2020.dtos.DeviceDTO;
import ro.tuc.ds2020.entities.Device;
import ro.tuc.ds2020.entities.EnergyConsumption;
import ro.tuc.ds2020.entities.MyUser;

import java.util.List;

/**
 * Interface for CRUD operations on device with respect to user
 */
public interface DeviceService {

    /**
     * Inserting a device inside the database
     * @param device
     * @return the saved device
     */
    Device saveDevice(Device device);

    /**
     * Fetching from database the device based on ID
     * @param description
     * @return
     */
    Device getDevice(String description);

    /**
     * Fetching a device based on its id
     * @param deviceID
     * @return
     */
    Device getDevice(Long deviceID);

    /**
     * Fetching all the devices existent in the database
     * @return list of devices
     */
    List<Device> getDevices();


    /**
     * Updating some attributes of device
     * @param device
     * @return the updated device
     */
    Device updateDevice(Device device, DeviceDTO deviceDTO);

    /**
     * Delete a device entirely from the database
     * @param description
     * @return the ID of the device deleted
     */
    Long deleteDevice(String description);

    /**
     * Adds an instance of an energy consumption given a day
     * @param device the device
     * @param en the total consumption
     * @return the device updated
     */
    Device addEnergyToDevice(Device device, EnergyConsumption en);
}
