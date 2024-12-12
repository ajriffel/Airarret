package onetomany.Users;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	@Autowired
	private UserRepository repo;

	public User getUserByID(int id) {
		return repo.findById(id);
	}

}