package app.credit.jms;


import app.credit.dto.CreditDto;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;


@Service
public class JmsReplyProducer {


    @JmsListener(destination = "${jsa.activemq.queue.listen}", containerFactory = "jsaFactory")
    @SendTo("${jsa.activemq.queue.sendto}")
    public Message<CreditDto> appleReceive(CreditDto dto, @Header("user") String userName) {
        Message<CreditDto> mesage= MessageBuilder
                .withPayload(dto)
                .setHeader("user",userName)
                .build();
        System.out.println("Recieved Message: " + dto);
        return mesage;


    }
}
