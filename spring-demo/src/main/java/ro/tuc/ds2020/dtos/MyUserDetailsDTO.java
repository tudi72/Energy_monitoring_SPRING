package ro.tuc.ds2020.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ro.tuc.ds2020.dtos.validators.annotation.AgeLimit;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MyUserDetailsDTO {

    private Long id;
    @NotNull
    private String name;
    @NotNull
    private String username;
    @NotNull
    private String address;
    @AgeLimit(limit = 18)
    private int age;
}
