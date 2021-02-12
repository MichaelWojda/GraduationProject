package pl.mw.san.repositories;

import org.springframework.expression.spel.ast.Operator;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.thymeleaf.templateresolver.FileTemplateResolver;
import pl.mw.san.model.Item;

import javax.persistence.*;
import java.util.List;
import java.util.function.*;

@Repository
public class ItemManualRepository {

    @PersistenceUnit
    private EntityManagerFactory  entityManagerFactory;



    private UnaryOperator<BiFunction<Item,EntityManager,Item>> runInTransaction = function -> (item,em) -> {
        EntityTransaction tx = em.getTransaction();
        Item result = new Item();
        try {
            tx.begin();
            result = function.apply(item,em);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            System.out.println("An error has occurred ");
            e.printStackTrace();
        }

        return result;
    };

    private BiFunction<Item,EntityManager,Item> createItemFunction = (item,entityManager) -> {
        entityManager.persist(item);
        return item;
    };

//    private UnaryOperator<Item> updateItemFunction = item -> {
//        EntityManager entityManager = entityManagerFactory.createEntityManager();
//        Item newItem = entityManager.find(Item.class, item.getId());
//        newItem.setId(item.getId());
//        newItem.setItemName(item.getItemName());
//        newItem.setItemDescription(item.getItemDescription());
//        newItem.setImageUrl(item.getImageUrl());
//        newItem.setAvailable(item.isAvailable());
//        newItem.setPrice(item.getPrice());
//
//        return newItem;
//
//
//    };
//
//    private UnaryOperator<Item> deleteItemFunction = item -> {
//        EntityManager entityManager = entityManagerFactory.createEntityManager();
//        entityManager.remove(item);
//        return null;
//
//    };
//
//    public List<Item> getAll() {
//        EntityManager entityManager = entityManagerFactory.createEntityManager();
//        Query query = entityManager.createQuery("SELECT e FROM Item e");
//        return query.getResultList();
//
//    }
//
//    public Item getOne(Long id) {
//        EntityManager entityManager = entityManagerFactory.createEntityManager();
//        return entityManager.find(Item.class, id);
//    }

    public Item transactionalCreateItem(Item item) {

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        return runInTransaction.apply(createItemFunction).apply(item,entityManager);

    }

//    ;
//
//    public Item transactionalUpdateItem(Item item) {
//
//        return runInTransaction.apply(updateItemFunction).apply(item);
//
//    }
//
//    ;
//
//    public void transactionalDeleteItem(Item item) {
//        runInTransaction.apply(deleteItemFunction).apply(item);
//    }

//    private EntityManager loadEntityManager() {
//        return entityManagerFactory.createEntityManager();
//    }


}
