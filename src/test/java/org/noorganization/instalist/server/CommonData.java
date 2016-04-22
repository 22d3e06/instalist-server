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

package org.noorganization.instalist.server;

import org.mindrot.jbcrypt.BCrypt;
import org.noorganization.instalist.server.support.DatabaseHelper;

import java.io.IOException;

/**
 * Created by damihe on 31.01.16.
 */
public class CommonData {

    public String mSecret;
    public String mEncryptedSecret;

    public CommonData() throws IOException {
        mSecret = "justATest";
        mEncryptedSecret = BCrypt.hashpw(mSecret, BCrypt.gensalt(10));

        DatabaseHelper.getInstance().initialize("org.noorganization.instalist.server.test");
    }

}
