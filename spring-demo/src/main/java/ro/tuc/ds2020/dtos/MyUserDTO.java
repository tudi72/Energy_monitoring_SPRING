package ro.tuc.ds2020.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyUserDTO extends RepresentationModel<MyUserDTO> {

    private Long id;
    private String name;
    private String username;
    private String password;
    private String address;
    private int age;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyUserDTO myUserDTO = (MyUserDTO) o;
        return age == myUserDTO.age &&
                Objects.equals(name, myUserDTO.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age);
    }
}
