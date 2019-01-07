package app.credit.jms;

import app.credit.dto.CreditDto;
import app.credit.model.CreditType;
import app.credit.repository.CreditRepository;
import app.credit.repository.UserRepository;
import org.hibernate.type.EnumType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import java.util.LinkedList;
import java.util.List;


@Service
public class JmsReplyConsumer {
    @Autowired
    private CreditRepository creditRepository;
    @Autowired
    JmsProducer producer;
    @Autowired
    UserRepository userRepository;



    @JmsListener(destination = "${jsa.activemq.queue.listen}", containerFactory = "jsaFactory")
    @SendTo("${jsa.activemq.queue.sendto}")
    public Message<CreditDto> receive(CreditDto creditDto,  @Header("user") String userName) {
        Message<CreditDto> mesage;
            mesage = MessageBuilder
                    .withPayload(creditDto)
                    .setHeader("user", creditDto.getUserName())
                    .build();
            System.out.println("Recieved Message: " + creditDto);
        return mesage;
    }


}