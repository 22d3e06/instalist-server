package org.noorganization.instalist.server.controller;

import org.noorganization.instalist.server.model.Device;

/**
 * Created by damihe on 06.02.16.
 */
public interface IAuthController {

    Device getDeviceByToken(String _token);

    String getTokenByHttpAuth(int _device, String _secret);
}
