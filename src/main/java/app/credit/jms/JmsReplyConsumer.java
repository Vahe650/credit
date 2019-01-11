//package app.credit.jms;
//import app.app.dto.CreditDto;
//import org.springframework.jms.annotation.JmsListener;
//import org.springframework.messaging.Message;
//import org.springframework.messaging.handler.annotation.Header;
//import org.springframework.messaging.handler.annotation.SendTo;
//import org.springframework.messaging.support.MessageBuilder;
//import org.springframework.stereotype.Service;
//
//@Service
//public class JmsReplyConsumer {
//
//
//
//    @JmsListener(destination = "${jsa.activemq.queue.listen}", containerFactory = "jsaFactory")
//    @SendTo("${jsa.activemq.queue.sendto}")
//    public Message<CreditDto> receive(CreditDto creditDto, @Header("user") String userName) {
//        Message<CreditDto> mesage;
//        mesage = MessageBuilder
//                .withPayload(creditDto)
//                .setHeader("user", userName)
//                .build();
//        System.out.println("Recieved Message: " + creditDto);
//        return mesage;
//    }
//
//
//}