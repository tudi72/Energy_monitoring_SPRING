package ro.tuc.ds2020.controllers;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.apache.log4j.Logger;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.tuc.ds2020.dtos.DeviceConsumptionDTO;
import ro.tuc.ds2020.dtos.DeviceDTO;
import ro.tuc.ds2020.security.JWTTokenHelper;
import ro.tuc.ds2020.services.ClientService;
import java.security.Principal;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping(value = "/client")
public class ClientController {

    private final ClientService clientService;
    private final JWTTokenHelper jWTTokenHelper;

    @GetMapping()
    public ResponseEntity<List<DeviceDTO>> getClientDevices(Principal principal) {
        List<DeviceDTO> deviceDTOS = clientService.getClientDevices(principal.getName());
        return new ResponseEntity<>(deviceDTOS, HttpStatus.OK); 
    }

    @GetMapping(value = "/{device}/{date}")
    public ResponseEntity<Map<String,Double>> getClientDeviceConsumptions(
            @PathVariable("date")
            @DateTimeFormat(pattern = "yyyy-MM-dd")Date day,
            @PathVariable("device") String description) throws ParseException {
        Map<String,Double> deviceConsumptionDTOS = clientService
                .getClientDeviceConsumption(description, day);

        deviceConsumptionDTOS.values().stream().forEach(System.out::println);
        return new ResponseEntity<>(deviceConsumptionDTOS,HttpStatus.OK);
    }
}
