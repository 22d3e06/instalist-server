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

package org.noorganization.instalist.server.model.generic;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * Created by damihe on 17.02.16.
 */
@MappedSuperclass
public class NamedBaseItem<T extends NamedBaseItem> extends BaseItem<T> {
    private String mName;

    public NamedBaseItem() {
        super();
    }

    @Column(name = "name", nullable = false)
    public String getName() {
        return mName;
    }

    public void setName(String _name) {
        mName = _name;
    }

    public T withName(String _name) {
        setName(_name);
        return (T) this;
    }
}
