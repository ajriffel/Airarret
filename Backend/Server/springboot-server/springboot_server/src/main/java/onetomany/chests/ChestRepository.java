package onetomany.chests;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChestRepository extends JpaRepository<Chests, Long>{

	@Transactional
	void deleteById(int worldID);
	

}
