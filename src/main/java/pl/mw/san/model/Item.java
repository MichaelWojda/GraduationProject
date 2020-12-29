package pl.mw.san.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Item {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true, nullable = false)
    private String itemName;

    @NotNull
    private String itemDescription;

    private String imageUrl;

    private double price;

    @NotNull
    private boolean isAvailable;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private ApplicationUser applicationUser;

    public Item() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public ApplicationUser getApplicationUser() {
        return applicationUser;
    }

    public void setApplicationUser(ApplicationUser applicationUser) {
        this.applicationUser = applicationUser;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", itemName='" + itemName + '\'' +
                ", itemDescription='" + itemDescription + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", price=" + price +
                ", isAvailable=" + isAvailable +
                ", applicationUser=" + applicationUser +
                '}';
    }
}
