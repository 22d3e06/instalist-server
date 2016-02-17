package org.noorganization.instalist.server.model;

import org.noorganization.instalist.server.model.generic.BaseItem;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "list_entries")
public class ListEntry extends BaseItem<ListEntry> {

    private int mId;
    private ShoppingList mList;
    private Product mProduct;
    private float mAmount;
    private boolean mStruck;
    private int mPriority;

    public ListEntry() {
        super();
        mAmount = 1.0f;
        mStruck = false;
        mPriority = 0;
    }

    @Id
    @GeneratedValue
    @Column(name = "id")
    public int getId() {
        return mId;
    }

    public void setId(int _id) {
        mId = _id;
    }

    public ListEntry withId(int _id) {
        setId(_id);
        return this;
    }

    @ManyToOne
    @JoinColumn(name = "list_id", nullable = false)
    public ShoppingList getList() {
        return mList;
    }

    public void setList(ShoppingList _list) {
        mList = _list;
    }

    public ListEntry withList(ShoppingList __list) {
        setList(__list);
        return this;
    }

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    public Product getProduct() {
        return mProduct;
    }

    public void setProduct(Product _product) {
        mProduct = _product;
    }

    public ListEntry withProduct(Product _product) {
        setProduct(_product);
        return this;
    }

    @Column(name = "amount", nullable = false)
    public float getAmount() {
        return mAmount;
    }

    public void setAmount(float _amount) {
        mAmount = _amount;
    }

    public ListEntry withAmount(float _amount) {
        setAmount(_amount);
        return this;
    }

    @Column(name = "struck", columnDefinition = "TINYINT(1)", nullable = false)
    public boolean getStruck() {
        return mStruck;
    }

    public void setStruck(boolean _struck) {
        mStruck = _struck;
    }

    public ListEntry withStruck(boolean _struck) {
        setStruck(_struck);
        return this;
    }

    @Column(name = "priority", nullable = false)
    public int getPriority() {
        return mPriority;
    }

    public void setPriority(int _priority) {
        mPriority = _priority;
    }

    public ListEntry withPriority(int _priority) {
        setPriority(_priority);
        return this;
    }

}
