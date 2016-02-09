package org.noorganization.instalist.server.model;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "lists")
public class ShoppingList {
    private int         mId;
    private UUID        mUUID;
    private Category    mCategory;
    private String      mName;
    private Date        mUpdated;
    private DeviceGroup mGroup;

    public ShoppingList() {
        mUpdated = new Date(System.currentTimeMillis());
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

    @Column(name = "uuid", nullable = false, columnDefinition = "BINARY(16)")
    public UUID getUUID() {
        return mUUID;
    }

    public void setUUID(UUID _UUID) {
        mUUID = _UUID;
    }

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = true)
    public Category getCategory() {
        return mCategory;
    }

    public void setCategory(Category _category) {
        mCategory = _category;
    }

    @Column(name = "name", nullable = false)
    public String getName() {
        return mName;
    }

    public void setName(String _name) {
        mName = _name;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "updated", columnDefinition = "TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP(3)",
            nullable = false)
    public Date getUpdated() {
        return mUpdated;
    }

    public void setUpdated(Date _updated) {
        mUpdated = _updated;
    }

    @ManyToOne
    @JoinColumn(name = "devicegroup_id", nullable = false)
    public DeviceGroup getGroup() {
        return mGroup;
    }

    public void setGroup(DeviceGroup _group) {
        mGroup = _group;
    }
}
