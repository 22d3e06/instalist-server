
/*
 * Copyright 2016 Tino Siegmund, Michael Wodniok
 *
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

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.util.StdDateFormat;

import java.util.Date;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "uuid", "name", "unituuid", "defaultamount", "stepamount", "lastchanged",
        "deleted" })
public class ProductInfo extends EntityObject {

    private String mUUID;
    private String mName;
    private String mUnitUUID;
    private Float mDefaultAmount;
    private Float mStepAmount;
    private Date mLastChanged;
    private Boolean mDeleted;
    private Boolean mRemoveUnit;

    @JsonProperty("uuid")
    public String getUUID() {
        return mUUID;
    }

    @JsonProperty("uuid")
    public void setUUID(String id) {
        this.mUUID = id;
    }

    public void setUUID(UUID uuid) {
        setUUID(uuid != null ? uuid.toString() : null);
    }

    public ProductInfo withUUID(String uuid) {
        setUUID(uuid);
        return this;
    }

    public ProductInfo withUUID(UUID uuid) {
        setUUID(uuid);
        return this;
    }

    @JsonProperty("name")
    public String getName() {
        return mName;
    }

    @JsonProperty("name")
    public void setName(String name) {
        mName = name;
    }

    public ProductInfo withName(String name) {
        setName(name);
        return this;
    }

    @JsonProperty("defaultamount")
    public Float getDefaultAmount() {
        return mDefaultAmount;
    }

    @JsonProperty("defaultamount")
    public void setDefaultAmount(Float defaultAmount) {
        mDefaultAmount = defaultAmount;
    }

    public ProductInfo withDefaultAmount(Float defaultAmount) {
        setDefaultAmount(defaultAmount);
        return this;
    }

    @JsonProperty("stepamount")
    public Float getStepAmount() {
        return mStepAmount;
    }

    @JsonProperty("stepamount")
    public void setStepAmount(Float stepAmount) {
        mStepAmount = stepAmount;
    }

    public ProductInfo withStepAmount(Float stepAmount) {
        setStepAmount(stepAmount);
        return this;
    }

    @JsonProperty("unituuid")
    public String getUnitUUID() {
        return mUnitUUID;
    }

    @JsonProperty("unituuid")
    public void setUnitUUID(String uuid) {
        mUnitUUID = uuid;
    }

    public void setUnitUUID(UUID uuid) {
        setUnitUUID(uuid != null ? uuid.toString() : null);
    }

    public ProductInfo withUnitUUID(String uuid) {
        setUnitUUID(uuid);
        return this;
    }

    public ProductInfo withUnitUUID(UUID uuid) {
        setUnitUUID(uuid);
        return this;
    }

    @JsonProperty("removeunit")
    public Boolean getRemoveUnit() {
        return mRemoveUnit;
    }

    @JsonProperty("removeunit")
    public void setRemoveUnit(Boolean removeUnit) {
        mRemoveUnit = removeUnit;
    }

    public ProductInfo withRemoveUnit(Boolean removeUnit) {
        setRemoveUnit(removeUnit);
        return this;
    }

    @JsonProperty("lastchanged")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = StdDateFormat.DATE_FORMAT_STR_ISO8601)
    public Date getLastChanged() {
        return mLastChanged;
    }

    @JsonProperty("lastchanged")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = StdDateFormat.DATE_FORMAT_STR_ISO8601)
    public void setLastChanged(Date lastChanged) {
        mLastChanged = lastChanged;
    }

    public ProductInfo withLastChanged(Date lastChanged) {
        setLastChanged(lastChanged);
        return this;
    }

    @JsonProperty("deleted")
    public Boolean getDeleted() {
        return mDeleted;
    }

    @JsonProperty("deleted")
    public void setDeleted(Boolean deleted) {
        mDeleted = deleted;
    }

    public ProductInfo withDeleted(Boolean deleted) {
        setDeleted(deleted);
        return this;
    }

}
