package org.noorganization.instalist.server;

import org.noorganization.instalist.server.message.Error;

/**
 * Created by damihe on 31.01.16.
 */
public class CommonEntity {
    public static final Error sNotAuthorized;
    public static final Error sNoData;
    public static final Error sInvalidData;
    public static final Error INVALID_UUID;
    public static final Error INVALID_DATE;

    static {
        sNotAuthorized = new Error().withMessage("Not authorized.");
        sNoData = new Error().withMessage("No data was sent.");
        sInvalidData = new Error().withMessage("Sent data was invalid.");
        INVALID_UUID = new Error().withMessage("Requested UUID was in wrong format.");
        INVALID_DATE = new Error().withMessage("Change date was either not in expected format or " +
                "in future.");
    }
}
