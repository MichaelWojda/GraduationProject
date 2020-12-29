package pl.mw.san.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.mw.san.model.Item;
import pl.mw.san.repositories.ItemRepository;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ItemRestController {

    private ItemRepository itemRepository;

    @Autowired
    public ItemRestController(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @GetMapping("/items")
    public List<Item> getAllItems() {
        return itemRepository.findAll();

    }

    @GetMapping("/item/{id}")
    public ResponseEntity<?> getOneItem(@PathVariable Long id) {
        return itemRepository.findById(id).map(item -> ResponseEntity.ok().body(item)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/item")
    public ResponseEntity<Item> createItem(@RequestBody Item item) throws URISyntaxException {
        Item savedItem = itemRepository.save(item);
        return item.getId() == null ?
                ResponseEntity.created(new URI("/api/item/" + savedItem.getId())).body(savedItem) :
                ResponseEntity.status(HttpStatus.CONFLICT).build();


    }

    @PutMapping("/item/{id}")
    public ResponseEntity<Item> updateItem(@RequestBody Item item) {
        return ResponseEntity.ok().body(itemRepository.save(item));

    }

    @DeleteMapping("/item/{id}")
    public ResponseEntity<?> deleteItem(@PathVariable Long id){
        itemRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
