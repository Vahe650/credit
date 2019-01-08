//package app.credit.jms;
//
//
//import javax.jms.JMSException;
//import javax.jms.Message;
//
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.jms.core.JmsTemplate;
//import org.springframework.jms.core.MessagePostProcessor;
//import org.springframework.stereotype.Component;
//
//@Component
//public class JmsConsumer {
//    @Autowired
//    JmsTemplate jmsTemplate;
//    @Value("${jsa.activemq.queue.consumer}")
//    String queue2;
//
//
//    public void sending(String text,String userName) {
//        jmsTemplate.convertAndSend(queue2, text, new MessagePostProcessor() {
//            public Message postProcessMessage(Message message) throws JMSException {
//                message.setStringProperty("user", userName);
//                return message;
//            }
//        });
//    }
//
//
//}