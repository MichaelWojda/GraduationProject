package pl.mw.san.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.List;

@Entity
public class CustomerOrder {


    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true, nullable = false)
    private String orderNumber;

    @JsonIgnoreProperties({"orderList"})
    @ManyToOne
    @JoinColumn(name = "user_id")
    private ApplicationUser applicationUser;

//    @ManyToMany(fetch = FetchType.EAGER ,cascade = CascadeType.ALL)
//    @JoinTable(name = "customer_order_items", joinColumns = {@JoinColumn(name = "cutomer_order_id", referencedColumnName = "id")},
//            inverseJoinColumns = {@JoinColumn(name = "item_id", referencedColumnName = "id")})
//    private List<Item> orderedItems;

    private double orderValue;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public ApplicationUser getApplicationUser() {
        return applicationUser;
    }

    public void setApplicationUser(ApplicationUser applicationUser) {
        this.applicationUser = applicationUser;
    }

//    public List<Item> getOrderedItems() {
//        return orderedItems;
//    }
//
//    public void setOrderedItems(List<Item> orderedItems) {
//        this.orderedItems = orderedItems;
//    }

    public double getOrderValue() {
        return orderValue;
    }

    public void setOrderValue(double orderValue) {
        this.orderValue = orderValue;
    }


}
