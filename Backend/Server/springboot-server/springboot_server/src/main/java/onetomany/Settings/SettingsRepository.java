package onetomany.Settings;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SettingsRepository extends JpaRepository<Setting, Long> {
    Setting findById(int id);
}
