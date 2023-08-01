package it.finanze.sanita.fse2.dr.dataquality.utility;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class RouteUtility {

    public static final String API_VERSION = "v1";

    public static final String API_REFRESH = "refresh";
    public static final String API_STATUS = "status";

    public static final String API_REFRESH_SCHEDULER = "/" + API_VERSION + "/" + API_REFRESH;
    public static final String API_STATUS_PARAMS = "/" + API_VERSION + "/" + API_STATUS;


}
