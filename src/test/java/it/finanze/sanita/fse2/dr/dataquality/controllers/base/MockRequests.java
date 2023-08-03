package it.finanze.sanita.fse2.dr.dataquality.controllers.base;

import lombok.NoArgsConstructor;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static it.finanze.sanita.fse2.dr.dataquality.utility.RouteUtility.API_REFRESH_SCHEDULER;
import static it.finanze.sanita.fse2.dr.dataquality.utility.RouteUtility.API_STATUS_HEALTH;
import static lombok.AccessLevel.PRIVATE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@NoArgsConstructor(access = PRIVATE)
public final class MockRequests {

    public static MockHttpServletRequestBuilder refreshSchedulerReq() {
        return post(API_REFRESH_SCHEDULER);
    }

    public static MockHttpServletRequestBuilder livenessReq() {
        return get(API_STATUS_HEALTH);
    }

}
