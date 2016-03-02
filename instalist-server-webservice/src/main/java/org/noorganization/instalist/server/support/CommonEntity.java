/*
 * Copyright 2016 Tino Siegmund, Michael Wodniok
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.noorganization.instalist.server.support;

import org.noorganization.instalist.comm.message.Error;

/**
 * This class was made for extracting common entities for responses.
 *
 * Created by Michael Wodniok on 2016-01-31.
 */
public class CommonEntity {
    /**
     * A response indicating the client was not authorized. It may be sent when token is wrong,
     * missing or other sent data is wrong or missing.
     */
    public static final Error NOT_AUTHORIZED;

    /**
     * Data required for change was not sent by server.
     */
    public static final Error NO_DATA_RECVD;

    /**
     * The data sent is invalid.
     */
    public static final Error INVALID_DATA;

    /**
     * A sent uuid is invalid. It has a wrong format and is not parseable to
     * {@link java.util.UUID}.
     */
    public static final Error INVALID_UUID;

    /**
     * The date sent is invalid. It could either be in wrong format or in future (for changing
     * data this is a constraint).
     */
    public static final Error INVALID_CHANGEDATE;

    static {
        NOT_AUTHORIZED = new Error().withMessage("Not authorized.");
        NO_DATA_RECVD = new Error().withMessage("No data was sent.");
        INVALID_DATA = new Error().withMessage("Sent data was invalid.");
        INVALID_UUID = new Error().withMessage("UUID was in wrong format.");
        INVALID_CHANGEDATE = new Error().withMessage("Change date was either not in expected " +
                "format or in future.");
    }

    private CommonEntity() {
    }
}
