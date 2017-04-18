/*
 * Copyright 2016 Tino Siegmund, Michael Wodniok
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

package org.noorganization.instalist.comm.message;

import com.fasterxml.jackson.annotation.*;

/**
 * Created by damihe on 29.01.16.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "groupid", "secret", "name" })
public class DeviceRegistration extends EntityObject {

    private String mGroupAuth;

    private String mSecret;

    private String mName;

    @JsonProperty("groupid")
    public String getGroupAuth() {
        return mGroupAuth;
    }

    @JsonProperty("name")
    public String getName() {
        return mName;
    }

    @JsonProperty("secret")
    public String getSecret() {
        return mSecret;
    }

    @JsonProperty("groupid")
    public void setGroupAuth(String groupAuth) {
        mGroupAuth = groupAuth;
    }

    public DeviceRegistration withGroupAuth(String groupAuth) {
        mGroupAuth = groupAuth;
        return this;
    }

    @JsonProperty("name")
    public void setName(String name) {
        mName = name;
    }

    public DeviceRegistration withName(String name) {
        mName = name;
        return this;
    }

    @JsonProperty("secret")
    public void setSecret(String secret) {
        mSecret = secret;
    }

    public DeviceRegistration withSecret(String secret) {
        mSecret = secret;
        return this;
    }
}
