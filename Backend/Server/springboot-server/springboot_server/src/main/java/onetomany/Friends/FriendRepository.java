package onetomany.Friends;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendRepository extends JpaRepository<Friend, Long> {
    Friend findById(int id);
}
