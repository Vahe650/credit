package app.credit;

import app.credit.dto.CreditDto;
import app.credit.jms.JmsProducer;
import app.credit.jms.JmsReplyConsumer;
import app.credit.model.Credit;
import app.credit.repository.CreditRepository;
import app.credit.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.messaging.Message;
import org.springframework.messaging.converter.MessageConversionException;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@SpringBootApplication

public class CreditApplication extends WebMvcConfigurerAdapter   {
    @Autowired
    JmsProducer producer;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CreditRepository creditRepository;
    @Autowired
    private JmsReplyConsumer jmsReplyConsumer;


    public static void main(String[] args) {
        SpringApplication.run(CreditApplication.class, args);
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Bean
    public ViewResolver internalResourceViewResolver() {
        InternalResourceViewResolver bean = new InternalResourceViewResolver();
        bean.setViewClass(JstlView.class);
        bean.setPrefix("/WEB-INF/");
        bean.setSuffix(".jsp");
        return bean;
    }

}
