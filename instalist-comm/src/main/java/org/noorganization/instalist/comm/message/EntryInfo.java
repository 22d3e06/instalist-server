
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

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.util.StdDateFormat;

import java.util.Date;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "uuid", "listuuid", "productuuid", "amount", "priority", "struck",
                           "lastchanged", "deleted" })
public class EntryInfo extends EntityObject {

    private String  mUUID;
    private String  mListUUID;
    private String  mProductUUID;
    private Float   mAmount;
    private Integer mPriority;
    private Boolean mStruck;
    private Date    mLastChanged;
    private Boolean mDeleted;

    @JsonProperty("uuid")
    public String getUUID() {
        return mUUID;
    }

    @JsonProperty("uuid")
    public void setUUID(String id) {
        this.mUUID = id;
    }

    public void setUUID(UUID uuid) {
        mUUID = (uuid != null ? uuid.toString() : null);
    }

    public EntryInfo withUUID(String uuid) {
        setUUID(uuid);
        return this;
    }

    public EntryInfo withUUID(UUID uuid) {
        setUUID(uuid);
        return this;
    }

    @JsonProperty("listuuid")
    public String getListUUID() {
        return mListUUID;
    }

    @JsonProperty("listuuid")
    public void setListUUID(String listUUID) {
        mListUUID = listUUID;
    }

    public void setListUUID(UUID listUUID) {
        mListUUID = (listUUID != null ? listUUID.toString() : null);
    }

    public EntryInfo withListUUID(String listUUID) {
        setListUUID(listUUID);
        return this;
    }

    public EntryInfo withListUUID(UUID listUUID) {
        setListUUID(listUUID);
        return this;
    }

    @JsonProperty("productuuid")
    public String getProductUUID() {
        return mProductUUID;
    }

    @JsonProperty("productuuid")
    public void setProductUUID(String productUUID) {
        mProductUUID = productUUID;
    }

    public void setProductUUID(UUID productUUID) {
        mProductUUID = (productUUID != null ? productUUID.toString() : null);
    }

    public EntryInfo withProductUUID(String productUUID) {
        setProductUUID(productUUID);
        return this;
    }

    public EntryInfo withProductUUID(UUID productUUID) {
        setProductUUID(productUUID);
        return this;
    }

    @JsonProperty("amount")
    public Float getAmount() {
        return mAmount;
    }

    @JsonProperty("amount")
    public void setAmount(Float amount) {
        mAmount = amount;
    }

    public EntryInfo withAmount(Float amount) {
        setAmount(amount);
        return this;
    }

    @JsonProperty("priority")
    public Integer getPriority() {
        return mPriority;
    }

    @JsonProperty("priority")
    public void setPriority(Integer priority) {
        mPriority = priority;
    }

    public EntryInfo withPriority(Integer priority) {
        setPriority(priority);
        return this;
    }

    @JsonProperty("struck")
    public Boolean getStruck() {
        return mStruck;
    }

    @JsonProperty("struck")
    public void setStruck(Boolean struck) {
        mStruck = struck;
    }

    public EntryInfo withStruck(Boolean struck) {
        setStruck(struck);
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

    public EntryInfo withLastChanged(Date lastChanged) {
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

    public EntryInfo withDeleted(Boolean deleted) {
        setDeleted(deleted);
        return this;
    }

}
