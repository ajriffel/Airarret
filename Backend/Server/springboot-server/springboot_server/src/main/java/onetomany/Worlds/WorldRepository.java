package onetomany.Worlds;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface WorldRepository extends JpaRepository<World, Long> {
    World findById(int id);

    @Transactional
    void deleteById(int id);
}
