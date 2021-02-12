package pl.mw.san.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import pl.mw.san.model.Item;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;
import java.util.function.UnaryOperator;

@Repository
public class ItemRepository {


    private EntityManager entityManager;

    private PlatformTransactionManager platformTransactionManager;

    @Autowired
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Autowired
    public void setPlatformTransactionManager(PlatformTransactionManager platformTransactionManager) {
        this.platformTransactionManager = platformTransactionManager;
    }


    private DefaultTransactionDefinition setUpDefinition() {
        DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
        definition.setIsolationLevel(TransactionDefinition.ISOLATION_DEFAULT);
        return definition;
    }

    private UnaryOperator<UnaryOperator<Item>> runInTransaction = function -> (item) -> {
        Item result = new Item();
        TransactionStatus status = platformTransactionManager.getTransaction(setUpDefinition());
        try {

            result = function.apply(item);

        } catch (Exception e) {

            platformTransactionManager.rollback(status);
            System.out.println("An error has occurred ");
            e.printStackTrace();
        }
        platformTransactionManager.commit(status);

        return result;
    };

    private UnaryOperator<Item> createItemFunction = (item) -> {
        entityManager.merge(item);
        return item;
    };


    private UnaryOperator<Item> updateItemFunction = item -> {
        Item newItem = entityManager.find(Item.class, item.getId());
        newItem.setId(item.getId());
        newItem.setItemName(item.getItemName());
        newItem.setItemDescription(item.getItemDescription());
        newItem.setImageUrl(item.getImageUrl());
        newItem.setAvailable(item.isAvailable());
        newItem.setPrice(item.getPrice());

        return newItem;


    };

    private UnaryOperator<Item> deleteItemFunction = item -> {
        entityManager.remove(item);
        return null;

    };

    public List<Item> getAll() {
        Query query = entityManager.createQuery("SELECT e FROM Item e");
        return query.getResultList();

    }

    public Optional<Item> getOne(Long id) {
        return Optional.of(entityManager.find(Item.class, id));
    }

    public Item transactionalCreateItem(Item item) {
        return runInTransaction.apply(createItemFunction).apply(item);

    }


    public Item transactionalUpdateItem(Item item) {

        return runInTransaction.apply(updateItemFunction).apply(item);

    }

    ;

    public void transactionalDeleteItem(Item item) {
        runInTransaction.apply(deleteItemFunction).apply(item);
    }


}
