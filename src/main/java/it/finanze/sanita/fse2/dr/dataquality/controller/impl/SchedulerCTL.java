package it.finanze.sanita.fse2.dr.dataquality.controller.impl;

import it.finanze.sanita.fse2.dr.dataquality.controller.ISchedulerCTL;
import it.finanze.sanita.fse2.dr.dataquality.dto.SearchParamsResponseDTO;
import it.finanze.sanita.fse2.dr.dataquality.dto.tools.RunSchedulerDTO;
import it.finanze.sanita.fse2.dr.dataquality.exceptions.SchedulerRunningException;
import it.finanze.sanita.fse2.dr.dataquality.scheduler.SearchParamScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import static it.finanze.sanita.fse2.dr.dataquality.config.Constants.Logs.DTO_RUN_TASK_QUEUED;
import static it.finanze.sanita.fse2.dr.dataquality.config.Constants.Logs.ERR_SCH_RUNNING;

@Controller
public class SchedulerCTL extends AbstractCTL implements ISchedulerCTL {

    @Autowired
    private SearchParamScheduler scheduler;

    @Override
    public RunSchedulerDTO refresh() {
        // Throw exception if trying to run and already executing
        if(scheduler.isRunning()) throw new SchedulerRunningException(ERR_SCH_RUNNING);
        // Put in queue, as soon as the executor is free, task will start
        scheduler.asyncAction();
        // Meanwhile return response
        return new RunSchedulerDTO(getLogTraceInfo(), DTO_RUN_TASK_QUEUED);
    }

    @Override
    public SearchParamsResponseDTO status() {
        return scheduler.status();
    }
}
