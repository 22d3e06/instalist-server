package org.noorganization.instalist.server.model;

import org.noorganization.instalist.server.model.generic.NamedBaseItem;
import org.noorganization.instalist.server.model.generic.NamedItem;

import javax.persistence.*;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "units")
public class Unit extends NamedBaseItem<Unit> {
    private int         mId;

    private Set<Product> mProducts;

    public Unit() {
        super();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public int getId() {
        return mId;
    }

    public void setId(int _id) {
        mId = _id;
    }

    public Unit withId(int _id) {
        setId(_id);
        return this;
    }

    @OneToMany(mappedBy = "unit")
    public Set<Product> getProducts() {
        return mProducts;
    }

    public void setProducts(
            Set<Product> _products) {
        mProducts = _products;
    }
}
