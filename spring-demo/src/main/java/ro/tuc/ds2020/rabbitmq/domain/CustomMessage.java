package ro.tuc.ds2020.rabbitmq.domain;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CustomMessage {

    private final Timestamp timestamp;
    private final String deviceID;
    private final String measurementValue;

    public CustomMessage(
            @JsonProperty("timestamp") final Timestamp timestamp,
            @JsonProperty("deviceID") final String deviceID,
            @JsonProperty("measurementValue")  final String measurementValue){
        this.timestamp = timestamp;
        this.deviceID = deviceID;
        this.measurementValue = measurementValue;
                        
    }

}
