package app.credit.jms;//package app.credit.jms;
//
//
//import app.credit.model.Credit;
//import app.credit.model.CreditType;
//import org.springframework.jms.annotation.JmsListener;
//import org.springframework.messaging.handler.annotation.Header;
//import org.springframework.stereotype.Component;
//
//@Component
//public class JmsConsumer {
//
//
//	@JmsListener(destination = "${jsa.activemq.queue.consumer}", containerFactory="jsaFactory")
//	public void appleReceive(Credit credit, @Header(value = "user",required = false) String type) {
//        if (type != null){
//            if (type.equals(CreditType.NEW.name())) {
//                System.out.println("credit not paid " + credit);
//            } else if (type.equals(CreditType.END.name())) {
//                System.out.println("credit paid: " + credit);
//            }
//    }
//	}
//}
