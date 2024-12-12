package onetomany.Items;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface ItemRepository extends JpaRepository<Item, Long> {
    Item findById(int id);

    @Transactional
    void deleteById(int id);
}
