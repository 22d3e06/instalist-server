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

package org.noorganization.instalist.server.controller.impl;

import org.mindrot.jbcrypt.BCrypt;
import org.noorganization.instalist.server.controller.IAuthController;
import org.noorganization.instalist.server.model.Device;
import org.noorganization.instalist.server.model.DeviceGroup;
import org.noorganization.instalist.server.support.DatabaseHelper;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.security.SecureRandom;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

class AuthController implements IAuthController {

    private static SecureRandom sRandom;
    private static HashMap<String, AuthInfo> sClients;

    /**
     * Check whether authenticated device is authorized to group.
     * @param _token The token for identifying authenticated device.
     * @return Whether authorized or not. False, if token is invalid.
     */
    public boolean getIsAuthorizedToGroup(String _token) {
        if (!sClients.containsKey(_token))
            return false;
        return sClients.get(_token).device.getAuthorized();
    }

    /**
     * Searches a group by authenticated client.
     * @param _token The token identifying the client.
     * @return Either a positive group id or a negative number, if no group was found for the
     * token.
     */
    public DeviceGroup getDeviceGroupByToken(String _token) {
        if (_token == null || !sClients.containsKey(_token))
            return null;
        return sClients.get(_token).device.getGroup();
    }

    /**
     * Searches the device-id for the given token.
     * @param _token The token identifying the client.
     * @return Either a positive device id or a negative number, if no authentication was found.
     */
    public Device getDeviceByToken(String _token) {
        if (_token == null || !sClients.containsKey(_token))
            return null;

        return sClients.get(_token).device;
    }

    public String getTokenByHttpAuth(EntityManager _manager, int _device, String _secret) {
        TypedQuery<Device> deviceQuery = _manager.createQuery("select d from Device d where d.id " +
                "= :id", Device.class);
        deviceQuery.setParameter("id", _device);
        List<Device> foundDevices = deviceQuery.getResultList();
        if (foundDevices.size() == 1) {
            Device foundDevice = foundDevices.get(0);
            if (BCrypt.checkpw(_secret, foundDevice.getSecret())){
                String currentToken = getTokenForDevice(_device);
                if (currentToken != null)
                    sClients.remove(currentToken);

                String token = generateToken();
                AuthInfo authenticated = new AuthInfo();
                authenticated.authenticated = new Date();
                authenticated.device = foundDevice;
                sClients.put(token, authenticated);
                return token;
            } else
                return null;
        } else
            return null;
    }

    public void revalidateDevice(EntityManager _manager, int _device) {
        String currentToken = getTokenForDevice(_device);
        if (currentToken == null)
            return;

        Device updatedDevice = _manager.find(Device.class, _device);
        if (updatedDevice == null)
            sClients.remove(currentToken);
        else
            sClients.get(currentToken).device = updatedDevice;
    }

    AuthController() {
        if (sRandom == null)
            sRandom = new SecureRandom();
        if (sClients == null)
            sClients = new HashMap<String, AuthInfo>();
    }

    private String getTokenForDevice(int _device) {
        for (String token: sClients.keySet()) {
            if (sClients.get(token).device.getId() == _device) {
                return token;
            }
        }
        return null;
    }

    private String generateToken() {
        String foundToken = random32();
        while (sClients.containsKey(foundToken))
            foundToken = random32();
        return  foundToken;
    }

    private String random32() {
        byte randomData[] = new byte[16];
        sRandom.nextBytes(randomData);
        StringBuilder rtn = new StringBuilder(32);
        for (byte currentByte : randomData)
            rtn.append(String.format("%02x", currentByte & 0xff));
        return rtn.toString();
    }

    private static class AuthInfo {
        public Device device;
        public Date   authenticated;
    }
}
