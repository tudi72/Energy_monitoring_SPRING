package ro.tuc.ds2020.rabbitmq.handler;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.core.MessageSendingOperations;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import ro.tuc.ds2020.entities.Device;
import ro.tuc.ds2020.entities.EnergyConsumption;
import ro.tuc.ds2020.rabbitmq.config.RabbitmqConfig;
import ro.tuc.ds2020.rabbitmq.domain.Message;
import ro.tuc.ds2020.repositories.EnergyConsumptionRepository;
import ro.tuc.ds2020.services.DeviceService;

@Slf4j
@Service
public class MessageEventListener {

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private EnergyConsumptionRepository energyConsumptionRepository;

    @Autowired
    @Qualifier("brokerMessagingTemplate")
    private MessageSendingOperations<String> operations;

    private Timestamp myTime = null;

    private int countdown = 0;

    MessageEventListener(){
        myTime = Timestamp.from(Instant.now());
    }

    @RabbitListener(queues = RabbitmqConfig.DEFAULT_PARSER_QUEUE)
    public void receiveMessage(final Message message){
        log.info("[MessageListener]: received a new message in rabbit queue... {}",message.toString());
        try{
           // get device compare its maximum value with current value
           Long deviceID = Long.parseLong(message.getDeviceID());
           Device device = deviceService.getDevice(deviceID);

           if(device == null) log.info("[MessageListener.error] : device is null");
           Double currKWh = Double.parseDouble(message.getMeasurementValue());
           Double maxKWh = device.getMaxHourlyEnergyCons();

           double diff = myTime.getTime() - message.getTimestamp().getTime();
            
           double hours = diff / 60_000.0;
        //    double hours = diff / 3_600_000.0;
           log.info("[MessageEventListener] : number of hours " + Double.toString(hours) + ".");

           //error less than 1 hour 
            if(diff  < 1){
            log.info("[MessageEventListener] : persisting energy");
           // create energy and persist
           EnergyConsumption consumption = new EnergyConsumption(null,message.getTimestamp(),currKWh,device);
           consumption = energyConsumptionRepository.saveAndFlush(consumption);
           if(consumption == null) log.info("[MessageListener.error] : consumption is null");


           if(currKWh > maxKWh){
            log.info("[MessageEventListener] : device max KWh reached");
               // send to client notification about the maximum limit reached
               operations.convertAndSend("/topic/ds2020",message);

           }
            }


       }catch (Exception ex){
        //    log.error("[MessageListener.error]");
       }
    }

}