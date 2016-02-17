package org.noorganization.instalist.server.model.generic;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class NamedItem<T extends NamedItem> {
    private String mName;

    @Column(name = "name", nullable = false)
    public String getName() {
        return mName;
    }

    public void setName(String _name) {
        mName = _name;
    }

    public T withName(String _name) {
        setName(_name);
        return ((T) this);
    }
}
