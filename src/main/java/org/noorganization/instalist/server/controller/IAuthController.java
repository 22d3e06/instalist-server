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

package org.noorganization.instalist.server.controller;

import org.noorganization.instalist.server.model.Device;

import javax.persistence.EntityManager;

/**
 * Controller interface for authenticating devices. Note that this is a special controller since
 * it maintains data in memory for purpose of speed. Advantage is, that accepting or refusing
 * authorization via tokens is faster. So it does only need db-connection for some operations.
 * Created by damihe on 06.02.16.
 */
public interface IAuthController {

    /**
     * Getter for already authenticated devices.
     * @param _token The token used for auth.
     * @return Either a device or null, if no device was found for this token, or token is invalid.
     */
    Device getDeviceByToken(String _token);

    /**
     * Generates a token if authentication data is correct.
     * @param _manager A database connection for verifying the authentication data.
     * @param _device The device-id to authenticate.
     * @param _secret The cleartext secret for verification.
     * @return Either a token-string or null if authentication data was incorrect.
     */
    String getTokenByHttpAuth(EntityManager _manager, int _device, String _secret);

    /**
     * Reloads a device without logging it out, if not deleted.
     * @param _manager A manager for reloading the data from database.
     * @param _device The device's id for reloading.
     */
    void revalidateDevice(EntityManager _manager, int _device);
}
