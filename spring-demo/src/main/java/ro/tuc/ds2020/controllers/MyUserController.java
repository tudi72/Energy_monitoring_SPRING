package ro.tuc.ds2020.controllers;


import com.mysql.cj.x.protobuf.Mysqlx;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.tuc.ds2020.dtos.DeviceDTO;
import ro.tuc.ds2020.dtos.MyUserDTO;
import ro.tuc.ds2020.dtos.MyUserDetailsDTO;
import ro.tuc.ds2020.dtos.builders.DeviceBuilder;
import ro.tuc.ds2020.dtos.builders.MyUserBuilder;
import ro.tuc.ds2020.entities.Device;
import ro.tuc.ds2020.entities.MyUser;
import ro.tuc.ds2020.entities.Role;
import ro.tuc.ds2020.services.UserService;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping(value = "/user")
public class MyUserController {

    private final UserService userService;

    @GetMapping()
    public ResponseEntity<List<MyUserDTO>> getUsers() {
        List<MyUserDTO> dtos = userService
                .getUsers()
                .stream()
                .map(MyUserBuilder::toMyUserDTO)
                .collect(Collectors.toList());

        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @GetMapping(value = "/devices/{id}")
    public ResponseEntity<Collection<DeviceDTO>> getUserDevices(@PathVariable("id") Long id){

        MyUser user = userService.getUser(id);
        Collection<DeviceDTO> deviceDTOs = user.getDevices()
                .stream()
                .map(DeviceBuilder::toDeviceDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(deviceDTOs, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<Long> insertUser(@RequestBody MyUserDTO myUserDTO) {

        /* creating the client object */
        MyUser userClientObj = MyUserBuilder.toEntity(myUserDTO);

        /* saving the object to database */
        MyUser userClientInst = userService.saveUser(userClientObj);

        /*  retrieving the client role */
        Role roleClientInst = userService.getRole("ROLE_CLIENT");

        /* adding the role of client to our new user */
        userService.addRoleToUser(userClientInst,roleClientInst);

        Long userID = userClientInst.getId();

        return new ResponseEntity<>(userID, HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<MyUserDTO> getUser(@PathVariable("id") Long userID) {
        MyUser user = userService.getUser(userID);
        MyUserDTO dto =MyUserBuilder.toMyUserDTO(user);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PostMapping(value = "/{name}")
    public ResponseEntity<MyUserDetailsDTO> updateUser(@PathVariable("name") String name, @Valid @RequestBody MyUserDetailsDTO myUserDTO){
        MyUser user = userService.getUser(name);
        MyUser updatedUser = userService.updateUser(user,myUserDTO);
        MyUserDetailsDTO updatedUserDetails = MyUserBuilder.toMyUserDetailsDTO(updatedUser);
        return new ResponseEntity<>(updatedUserDetails,HttpStatus.OK);
    }

    @DeleteMapping(value = "/{name}")
    public ResponseEntity<Long> deleteUser(@PathVariable("name") String name){

        Long userID = userService.deleteUser(name);
        return new ResponseEntity<>(userID,HttpStatus.ACCEPTED);
    }



}
