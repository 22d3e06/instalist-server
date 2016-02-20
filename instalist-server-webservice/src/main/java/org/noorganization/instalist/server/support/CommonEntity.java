package org.noorganization.instalist.server.support;

import org.noorganization.instalist.comm.message.Error;

/**
 * This class was made for extracting common entities for responses.
 * Created by Michael Wodniok on 2016-01-31.
 */
public class CommonEntity {
    public static final Error NOT_AUTHORIZED;
    public static final Error NO_DATA_RECVD;
    public static final Error INVALID_DATA;
    public static final Error INVALID_UUID;
    public static final Error INVALID_CHANGEDATE;

    static {
        NOT_AUTHORIZED = new Error().withMessage("Not authorized.");
        NO_DATA_RECVD = new Error().withMessage("No data was sent.");
        INVALID_DATA = new Error().withMessage("Sent data was invalid.");
        INVALID_UUID = new Error().withMessage("UUID was in wrong format.");
        INVALID_CHANGEDATE = new Error().withMessage("Change date was either not in expected " +
                "format or in future.");
    }
}
