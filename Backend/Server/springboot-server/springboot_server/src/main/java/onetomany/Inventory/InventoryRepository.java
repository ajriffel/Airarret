package onetomany.Inventory;

import org.springframework.data.jpa.repository.JpaRepository;
import javax.transaction.Transactional;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
	
	@Transactional
	void deleteById(int id);

}
