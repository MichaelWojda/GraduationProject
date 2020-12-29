package pl.mw.san.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.mw.san.model.Item;

public interface ItemRepository extends JpaRepository<Item,Long> {
}
