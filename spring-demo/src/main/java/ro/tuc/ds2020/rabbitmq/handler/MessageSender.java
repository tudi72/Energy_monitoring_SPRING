package ro.tuc.ds2020.rabbitmq.handler;
import lombok.extern.slf4j.Slf4j;
import ro.tuc.ds2020.rabbitmq.config.RabbitmqConfig;
import ro.tuc.ds2020.rabbitmq.domain.Message;
import java.io.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.TimerTask;

import org.springframework.amqp.rabbit.core.RabbitTemplate;

@Slf4j
public class MessageSender 
extends TimerTask
{
    public RabbitTemplate rabbitTemplate;
    private BufferedReader reader = null;
    private Message message;

    public MessageSender(RabbitTemplate rabbitTemplate,Message message){
        this.message = message;
        this.rabbitTemplate = rabbitTemplate;
        try{
            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            InputStream stream = classloader.getResourceAsStream("sensor.csv");
            this.reader = new BufferedReader(new InputStreamReader(stream));
        }
        catch(Exception ex){
            log.error("[MessageHandler.ContextExcpetion] : " + ex.getMessage());
        }
    }

    @Override
    public void run(){
        String measurementValueFile = null;
        try{
            measurementValueFile = reader.readLine();
            if(measurementValueFile!= null){
                message.setTimestamp(new Timestamp((new Date()).getTime()));
                message.setMeasurementValue(measurementValueFile);

                rabbitTemplate.convertAndSend(RabbitmqConfig.EXCHANGE_NAME, RabbitmqConfig.ROUTING_KEY, message);
                log.info("[MessageHandler] : sending message ... {}",message.toString());

            }
            
        }
        catch(IOException ex){
            log.error("[MessageHandler.IOException] : " + ex.getMessage());
            try{
                reader.close();
            } catch(Exception e){
                log.error("[MessageHandler.Exception] : error about buffered reader " + e.getMessage());
            }
        }
        catch(Exception ex){
            log.error("[MessageHandler.ERROR] : " + ex.getMessage());
        }       
    }

}
