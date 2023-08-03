package it.finanze.sanita.fse2.dr.dataquality.controllers.base;

import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static it.finanze.sanita.fse2.dr.dataquality.utility.RouteUtility.API_REFRESH_SCHEDULER;
import static it.finanze.sanita.fse2.dr.dataquality.utility.RouteUtility.API_STATUS_HEALTH;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public final class MockRequests {

    public static MockHttpServletRequestBuilder refreshSchedulerReq() {
        return post(API_REFRESH_SCHEDULER);
    }

    public static MockHttpServletRequestBuilder livenessReq() {
        return get(API_STATUS_HEALTH);
    }

}
