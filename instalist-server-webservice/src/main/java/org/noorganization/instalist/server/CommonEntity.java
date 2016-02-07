package org.noorganization.instalist.server;

import org.noorganization.instalist.server.message.Error;

/**
 * Created by damihe on 31.01.16.
 */
public class CommonEntity {
    public static final Error sNotAuthorized;
    public static final Error sNoData;
    public static final Error sInvalidData;

    static {
        sNotAuthorized = new Error().withMessage("Not authorized.");
        sNoData = new Error().withMessage("No data was sent.");
        sInvalidData = new Error().withMessage("Sent data was invalid.");

    }
}
