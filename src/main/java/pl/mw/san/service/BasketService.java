package pl.mw.san.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.mw.san.model.ApplicationUser;
import pl.mw.san.model.Basket;
import pl.mw.san.model.Item;
import pl.mw.san.repositories.ItemRepository;
import pl.mw.san.repositories.UserRepository;

import java.time.LocalDate;
import java.util.Collections;

@Service
public class BasketService {

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    UserRepository userRepository;

    public Basket addItemToBasket(ApplicationUser applicationUser, Item item){
        item.setAvailable(false);

        //item = itemRepository.transactionalUpdateItem(item);

        Basket basket = applicationUser.getBasket();

        if(basket!=null){
            basket.getItemsInBasket().add(item);
        }
        else {
            basket = new Basket();
            basket.setCreationDate(LocalDate.now());
            basket.setItemsInBasket(Collections.singletonList(item));
        }

        applicationUser.setBasket(basket);
        userRepository.save(applicationUser);

       return basket;

    };

    public Basket removeItemFromBasket(ApplicationUser applicationUser, Item item){
        Basket basket = applicationUser.getBasket();
        basket.getItemsInBasket().remove(item);
        applicationUser.setBasket(basket);
        userRepository.save(applicationUser);
        item.setAvailable(true);
        itemRepository.transactionalUpdateItem(item);

        return basket;
    }





}
