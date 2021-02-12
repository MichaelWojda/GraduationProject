package pl.mw.san.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import pl.mw.san.repositories.OrderRepository;

import java.util.function.Function;

@Service
public class OrderService {

//    @Autowired
//    private ItemRepository itemRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PlatformTransactionManager platformTransactionManager;

    private Function<PlatformTransactionManager, TransactionStatus> setUpStatus = (transactionManager -> {

        DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
        definition.setIsolationLevel(TransactionDefinition.ISOLATION_REPEATABLE_READ);

        return transactionManager.getTransaction(definition);

    });









    }




