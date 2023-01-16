package ro.tuc.ds2020.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.tuc.ds2020.entities.Device;
import ro.tuc.ds2020.entities.MyUser;
import ro.tuc.ds2020.services.ClientService;
import ro.tuc.ds2020.services.DeviceService;
import ro.tuc.ds2020.services.UserService;

@Slf4j
@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping(value = "/admin")
public class AdminController {

    private final UserService userService;
    private final ClientService clientService;
    private final DeviceService deviceService;


    @GetMapping(value = "/{name}_{device}")
    public ResponseEntity<Long> addDeviceToUser(@PathVariable("name") String name, @PathVariable("device") String description){
        Long id = null;
        Device device = null;
        try{
            MyUser user = userService.getUser(name);
            device = deviceService.getDevice(description);
            clientService.addDeviceToUser(user,device);
            id = device.getId();
            return new ResponseEntity<>(id, HttpStatus.OK);
        }
        catch(Exception ex){
            if(device != null) id = device.getId();
            log.error("[AdminController.addDeviceToUser] ", ex.getMessage());
            return new ResponseEntity<>(id, HttpStatus.OK);

        }
    }
}
