package it.finanze.sanita.fse2.dr.dataquality.scheduler;

import it.finanze.sanita.fse2.dr.dataquality.service.impl.SearchParamVerifierSRV;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SearchParamScheduler {

    @Autowired
    private SearchParamVerifierSRV service;

    private volatile boolean running;

    @EventListener(ApplicationStartedEvent.class)
    public void initialize() {
        action();
    }

    @Async("single-thread-exec")
    public void asyncAction() {
        log.info("[GTW] Running on {}", Thread.currentThread().getName());
        action();
    }

    @Scheduled(cron = "${bundle.scheduler.invoke}")
    @SchedulerLock(name = "invokeGTWSearchParamsScheduler")
    public void action() {
        // Set run flag
        running = true;
        // Log me
        log.info("[GTW] Starting scheduled updating process");
        // Execute
        service.refresh();
        // Log me
        log.info("[GTW] Updating process completed");
        // Reset run flag
        running = false;
    }

    public boolean isRunning() {
        return running;
    }
}
