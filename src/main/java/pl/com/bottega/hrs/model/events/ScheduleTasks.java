package pl.com.bottega.hrs.model.events;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ScheduleTasks {


    @Scheduled(cron = "0 44 15 * * *")
    public void reportCurrentTime(){
        Logger.getLogger(ScheduleTasks.class).info("Today is ===>>> " + LocalDateTime.now() + " <<<===");
    }


}
