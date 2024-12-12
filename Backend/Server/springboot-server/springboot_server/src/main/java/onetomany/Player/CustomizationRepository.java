package onetomany.Player;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CustomizationRepository extends JpaRepository<PlayerCusomization, Long>  {
	
	@Query(value = "SELECT * FROM customization WHERE username = :Username", nativeQuery=true)
	public List<PlayerCusomization> findByUser(@Param("Username")String Username);
	
	@Transactional
	void deleteById(int id);

	PlayerCusomization findById(int id);

}
