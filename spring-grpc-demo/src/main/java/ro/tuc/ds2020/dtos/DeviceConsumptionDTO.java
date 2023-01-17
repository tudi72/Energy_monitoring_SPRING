package ro.tuc.ds2020.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DeviceConsumptionDTO {

    String description;
    Map<Date,Double> kWh;
}
