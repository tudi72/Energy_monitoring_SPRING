package ro.tuc.ds2020.services;


import ro.tuc.ds2020.dtos.DeviceConsumptionDTO;
import ro.tuc.ds2020.dtos.DeviceDTO;
import ro.tuc.ds2020.entities.Device;
import ro.tuc.ds2020.entities.EnergyConsumption;
import ro.tuc.ds2020.entities.MyUser;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Methods for getting different type of entries between
 * Client, Device and Energy Consumption Table
 */
public interface ClientService {

    /**
     * Retrieves for a client the list of its devices
     * @param username used for identifying the client
     * @return list of DTO devices
     */
    List<DeviceDTO> getClientDevices(String username);

    /**
     * Given the user token we map each of his device to the table of consumption
     * @param description
     * @param day when to check the overall energy consumption for each device
     * @return list of consumption for each device
     */
    Map<String,Double> getClientDeviceConsumption(String description, Date day) throws ParseException;

    /**
     * Add a device to the list controlled by some user
     * @param user
     * @param device
     * @return
     */
    MyUser addDeviceToUser(MyUser user, Device device);
}
