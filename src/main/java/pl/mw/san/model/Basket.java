package pl.mw.san.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Basket {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name="basket_items",joinColumns = {@JoinColumn(name = "basket_id", referencedColumnName = "id")},
    inverseJoinColumns = {@JoinColumn(name="item_id", referencedColumnName = "id")})
    private List<Item> itemsInBasket = new ArrayList<>();

    private LocalDate creationDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public List<Item> getItemsInBasket() {
        return itemsInBasket;
    }

    public void setItemsInBasket(List<Item> itemsInBasket) {
        this.itemsInBasket = itemsInBasket;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public String toString() {
        return "Basket{" +
                "id=" + id +
                ", itemsInBasket=" + itemsInBasket +
                ", creationDate=" + creationDate +
                '}';
    }
}
