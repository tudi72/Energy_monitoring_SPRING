package ro.tuc.ds2020.rabbitmq.domain;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Message implements Serializable{
   private Timestamp timestamp;
   private String deviceID;
   private String measurementValue;
}

