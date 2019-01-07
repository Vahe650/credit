package app.credit.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ErrorHandler;

@Service
@Slf4j
public class SomeHandler implements ErrorHandler {

    @Override
    public void handleError(Throwable t) {
        log.error("Error in listener", t);
    }
}