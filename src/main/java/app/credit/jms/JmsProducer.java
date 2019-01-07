package app.credit.jms;

import javax.jms.JMSException;
import javax.jms.Message;

import app.credit.dto.CreditDto;
import app.credit.model.Credit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessagePostProcessor;
import org.springframework.stereotype.Component;


@Component
public class JmsProducer {
	@Autowired
	JmsTemplate jmsTemplate;
	@Value("${jsa.activemq.queue.producer}")
	String queue;

	
	public void send( CreditDto creditDto, String userName){
			jmsTemplate.convertAndSend(queue, creditDto, new MessagePostProcessor() {
				public Message postProcessMessage(Message message) throws JMSException {
					message.setStringProperty("user", userName);
					return message;
				}
			});
	}
}