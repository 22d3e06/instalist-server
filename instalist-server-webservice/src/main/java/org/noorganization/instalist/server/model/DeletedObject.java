package org.noorganization.instalist.server.model;

import org.noorganization.instalist.server.model.generic.BaseItem;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "deletion_log")
public class DeletedObject extends BaseItem<DeletedObject> {

    public enum Type {
        CATEGORY,
        INGREDIENT,
        LIST,
        LISTENTRY,
        PRODUCT,
        RECIPE,
        TAG,
        TAGGEDPRODUCT,
        UNIT
    }

    private int         mId;
    private Type        mType;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    public int getId() {
        return mId;
    }

    public void setId(int _id) {
        mId = _id;
    }

    public DeletedObject withId(int _id) {
        setId(_id);
        return this;
    }

    @Enumerated
    @Column(name = "type", nullable = false)
    public Type getType() {
        return mType;
    }

    public void setType(Type _type) {
        mType = _type;
    }

    public DeletedObject withType(Type _type) {
        setType(_type);
        return this;
    }
}
