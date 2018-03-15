package pl.com.bottega.hrs.ui.scheduled;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SchedulingTest {

    @Scheduled(cron="0 41 15 * * *")
    public void doIt() {
        Logger.getLogger(SchedulingTest.class).info("Hey hey !!!!");
    }

}
