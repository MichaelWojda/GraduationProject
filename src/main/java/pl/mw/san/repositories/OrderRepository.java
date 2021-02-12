package pl.mw.san.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.mw.san.model.CustomerOrder;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<CustomerOrder,Long> {


}
