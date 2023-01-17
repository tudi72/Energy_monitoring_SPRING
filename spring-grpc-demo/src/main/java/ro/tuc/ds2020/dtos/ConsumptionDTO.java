package ro.tuc.ds2020.dtos;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ConsumptionDTO {

    private Double kWh;
    private Long deviceID;
}
