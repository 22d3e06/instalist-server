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

package org.noorganization.instalist.comm.message;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.util.StdDateFormat;

import java.util.Date;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"uuid","name","categoryuuid","lastchanged","deleted"})
public class ListInfo extends EntityObject {

    private String mUUID;
    private String mName;
    private Date mLastChanged;
    private String mCategoryUUID;
    private Boolean mRemoveCategory;
    private Boolean mDeleted;

    @JsonProperty("uuid")
    public String getUUID() {
        return mUUID;
    }

    @JsonProperty("uuid")
    public void setUUID(String uuid) {
        this.mUUID = uuid;
    }

    public void setUUID(UUID uuid) {
        setUUID(uuid.toString());
    }

    public ListInfo withUUID(String uuid) {
        setUUID(uuid);
        return this;
    }

    public ListInfo withUUID(UUID uuid) {
        setUUID(uuid.toString());
        return this;
    }

    @JsonProperty("name")
    public String getName() {
        return mName;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.mName = name;
    }

    public ListInfo withName(String name) {
        this.mName = name;
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

    public ListInfo withLastChanged(Date lastChanged) {
        setLastChanged(lastChanged);
        return this;
    }

    @JsonProperty("categoryuuid")
    public String getCategoryUUID() {
        return mCategoryUUID;
    }

    @JsonProperty("categoryuuid")
    public void setCategoryUUID(String categoryUUID) {
        mCategoryUUID = categoryUUID;
    }

    public void setCategoryUUID(UUID categoryUUID) {
        mCategoryUUID = categoryUUID.toString();
    }

    public ListInfo withCategoryUUID(String uuid) {
        setCategoryUUID(uuid);
        return this;
    }

    public ListInfo withCategoryUUID(UUID uuid) {
        setCategoryUUID(uuid.toString());
        return this;
    }

    @JsonProperty("removecategory")
    public Boolean getRemoveCategory() {
        return mRemoveCategory;
    }

    @JsonProperty("removecategory")
    public void setRemoveCategory(Boolean removeCat) {
        mRemoveCategory = removeCat;
    }

    public ListInfo withRemoveCategory(Boolean removeCat) {
        setRemoveCategory(removeCat);
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

    public ListInfo withDeleted(Boolean deleted) {
        mDeleted = deleted;
        return this;
    }
}
