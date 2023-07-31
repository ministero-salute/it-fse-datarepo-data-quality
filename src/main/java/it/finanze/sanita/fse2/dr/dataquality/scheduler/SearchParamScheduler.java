/*
 * SPDX-License-Identifier: AGPL-3.0-or-later
 * 
 * Copyright (C) 2023 Ministero della Salute
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.
 */
package it.finanze.sanita.fse2.dr.dataquality.scheduler;

import it.finanze.sanita.fse2.dr.dataquality.dto.SearchParamsResponseDTO;
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
        try {
            service.refresh();
        } catch (Exception e) {
            log.warn("Unable to retrieve search-params from FHIR", e);
        }
        // Log me
        log.info("[GTW] Updating process completed");
        // Reset run flag
        running = false;
    }

    public boolean isRunning() {
        return running;
    }

    public SearchParamsResponseDTO status() {
        return service.getResponse();
    }
}
