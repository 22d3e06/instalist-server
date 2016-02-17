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
