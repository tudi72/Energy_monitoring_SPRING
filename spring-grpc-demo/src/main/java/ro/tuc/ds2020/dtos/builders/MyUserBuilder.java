package ro.tuc.ds2020.dtos.builders;

import lombok.NoArgsConstructor;
import ro.tuc.ds2020.dtos.MyUserDTO;
import ro.tuc.ds2020.dtos.MyUserDetailsDTO;
import ro.tuc.ds2020.entities.MyUser;

import java.util.ArrayList;

@NoArgsConstructor
public class MyUserBuilder {

    public static MyUserDTO toMyUserDTO(MyUser user){
        return new MyUserDTO(user.getId(),
                user.getName(),
                user.getUsername(),
                user.getPassword(),
                user.getAddress(),
                user.getAge()
        );
    }

    public static MyUserDetailsDTO toMyUserDetailsDTO(MyUser user){
        return new MyUserDetailsDTO(
                user.getId(),
                user.getName(),
                user.getUsername(),
                user.getAddress(),
                user.getAge());
    }

    public static MyUser toEntity(MyUserDTO myUserDTO){
        return new MyUser(0L,
                myUserDTO.getName(),
                myUserDTO.getUsername(),
                    "pass1234",
                myUserDTO.getAddress(),
                myUserDTO.getAge(),
                new ArrayList<>(),
                new ArrayList<>());
    }

    public static MyUser toEntity(MyUserDetailsDTO myUserDetailsDTO){
        return new MyUser(0L,
                myUserDetailsDTO.getName(),
                myUserDetailsDTO.getUsername(),
                "password",
                myUserDetailsDTO.getAddress(),
                myUserDetailsDTO.getAge(),null,null);
    }
}
