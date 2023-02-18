package ro.tuc.ds2020.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.tuc.ds2020.dtos.DeviceConsumptionDTO;
import ro.tuc.ds2020.dtos.DeviceDTO;
import ro.tuc.ds2020.dtos.builders.DeviceBuilder;
import ro.tuc.ds2020.entities.Device;
import ro.tuc.ds2020.entities.MyUser;
import ro.tuc.ds2020.repositories.DeviceRepository;
import ro.tuc.ds2020.repositories.MyUserRepository;
import ro.tuc.ds2020.security.JWTTokenHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ClientServiceImp implements ClientService{

    private final DeviceRepository deviceRepository;
    private final MyUserRepository userRepository;


    @Override
    public List<DeviceDTO> getClientDevices(String username) {

        MyUser user = userRepository.findByUsername(username);
        if(user != null && user.getDevices() != null)
            return  user.getDevices().stream()
                        .map(DeviceBuilder::toDeviceDTO)
                        .collect(Collectors.toList());
        else return null;
    }

    @Override
    public Map<String,Double> getClientDeviceConsumption(String description, Date day) throws ParseException {


        Device device = deviceRepository.findDeviceByDescription(description);
        Map<String, Double> map = new HashMap<>();
        try{
            device.getConsumptions()
                    .stream()
                    .forEach(x -> {
                        map.put((new SimpleDateFormat("HH:mm").format(x.getDate())), x.getKWh());
                    });
        }catch(Exception ex){
            log.info("[ClientServiceImp]  : " + ex.getMessage());
        }
        return map;
    }
    @Override
    public MyUser addDeviceToUser(MyUser user, Device device) {

        try{
            Collection<Device> devices = user.getDevices();
            if(devices == null) {
                devices = new ArrayList<Device>();
            }
            else if(devices.contains(device)){
                return user;
            }
            devices.add(device);
            user.setDevices(devices);
            user = userRepository.saveAndFlush(user);
        }
        catch(Exception ex){
            log.info("[ClientServiceImp] : ",ex.getMessage());
        }

        return user;

    }
}
