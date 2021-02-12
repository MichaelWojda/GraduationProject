package pl.mw.san.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.mw.san.model.Item;

import pl.mw.san.repositories.ItemRepository;
import pl.mw.san.security.payload.ApiResponse;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
public class ItemRestController {

    Logger logger = LoggerFactory.getLogger(ItemRestController.class);

    private ItemRepository itemRepository;

    @Autowired
    public ItemRestController(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @GetMapping("/items")
    public List<Item> getAllItems() {
        return itemRepository.getAll();

    }

    @GetMapping("/item/{id}")
    public ResponseEntity<?> getOneItem(@PathVariable Long id) {
        return itemRepository.getOne(id).map(item -> ResponseEntity.ok().body(item)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping(value = "/item", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createItem(@RequestBody Item item) throws URISyntaxException {


        Item savedItem = itemRepository.transactionalCreateItem(item);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/item/{id}")
                .buildAndExpand(savedItem.getId()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse("Item Created Successfully", true));


    }

    @PutMapping("/item/{id}")
    public ResponseEntity<Item> updateItem(@RequestBody Item item) {
        return ResponseEntity.ok().body(itemRepository.transactionalUpdateItem(item));

    }

    @DeleteMapping("/item/{id}")
    public ResponseEntity<?> deleteItem(@PathVariable Long id) {
        Item item = itemRepository.getOne(id).orElseThrow(()-> new IllegalArgumentException("No user of given id found"));
        itemRepository.transactionalDeleteItem(item);
        return ResponseEntity.ok().build();
    }
}
