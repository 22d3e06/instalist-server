
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

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.util.StdDateFormat;

import java.util.Date;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "uuid", "recipeuuid", "productuuid", "amount", "lastchanged", "deleted" })
public class IngredientInfo extends EntityObject {

    private String  mUUID;
    private String  mRecipeUUID;
    private String  mProductUUID;
    private Float   mAmount;
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
        mUUID = uuid != null ? uuid.toString() : null;
    }

    public IngredientInfo withUUID(String uuid) {
        setUUID(uuid);
        return this;
    }

    public IngredientInfo withUUID(UUID uuid) {
        setUUID(uuid);
        return this;
    }

    @JsonProperty("recipeuuid")
    public String getRecipeUUID() {
        return mRecipeUUID;
    }

    @JsonProperty("recipeuuid")
    public void setRecipeUUID(String recipeUUID) {
        mRecipeUUID = recipeUUID;
    }

    public void setRecipeUUID(UUID listUUID) {
        mRecipeUUID = listUUID != null ? listUUID.toString() : null;
    }

    public IngredientInfo withRecipeUUID(String listUUID) {
        setRecipeUUID(listUUID);
        return this;
    }

    public IngredientInfo withRecipeUUID(UUID listUUID) {
        setRecipeUUID(listUUID);
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

    public IngredientInfo withProductUUID(String productUUID) {
        setProductUUID(productUUID);
        return this;
    }

    public IngredientInfo withProductUUID(UUID productUUID) {
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

    public IngredientInfo withAmount(Float amount) {
        setAmount(amount);
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

    public IngredientInfo withLastChanged(Date lastChanged) {
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

    public IngredientInfo withDeleted(Boolean deleted) {
        setDeleted(deleted);
        return this;
    }

}
