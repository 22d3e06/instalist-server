package org.noorganization.instalist.server.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "lists")
public class ShoppingList {
    private int         mId;
    private UUID        mUUID;
    private Category    mCategory;
    private String      mName;
    private Instant     mUpdated;
    private DeviceGroup mGroup;

    public ShoppingList() {
        mUpdated = Instant.now();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    public int getId() {
        return mId;
    }

    public void setId(int _id) {
        mId = _id;
    }

    public ShoppingList withId(int _id) {
        setId(_id);
        return this;
    }

    @Column(name = "uuid", nullable = false, columnDefinition = "BINARY(16)")
    public UUID getUUID() {
        return mUUID;
    }

    public void setUUID(UUID _UUID) {
        mUUID = _UUID;
    }

    public ShoppingList withUUID(UUID _uuid) {
        setUUID(_uuid);
        return this;
    }

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = true)
    public Category getCategory() {
        return mCategory;
    }

    public void setCategory(Category _category) {
        mCategory = _category;
    }

    public ShoppingList withCategrory(Category _category) {
        setCategory(_category);
        return this;
    }

    @Column(name = "name", nullable = false)
    public String getName() {
        return mName;
    }

    public void setName(String _name) {
        mName = _name;
    }

    public ShoppingList withName(String _name) {
        setName(_name);
        return this;
    }

    //@Temporal(TemporalType.TIMESTAMP)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "updated", columnDefinition = "TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP(3)",
            nullable = false)
    public Instant getUpdated() {
        /*if (mUpdated != null && mUpdated.getClass() == Timestamp.class) {
            Timestamp current = (Timestamp) mUpdated;
            return new Date(current.getTime() + (current.getNanos() / 1000000));
        }*/
        return mUpdated;
    }

    public void setUpdated(Instant _updated) {
        mUpdated = _updated;
    }

    public ShoppingList withUpdated(Instant _updated) {
        setUpdated(_updated);
        return this;
    }

    @ManyToOne
    @JoinColumn(name = "devicegroup_id", nullable = false)
    public DeviceGroup getGroup() {
        return mGroup;
    }

    public void setGroup(DeviceGroup _group) {
        mGroup = _group;
    }

    public ShoppingList withGroup(DeviceGroup _group) {
        setGroup(_group);
        return this;
    }
}
