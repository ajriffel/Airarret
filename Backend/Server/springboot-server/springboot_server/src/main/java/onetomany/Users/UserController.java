package onetomany.Users;

import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.ApiOperation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;

    @ApiOperation(value = "generates a list of all users in the database ", response = User.class, tags = "getAllUsers")
    @GetMapping(path = "/users")
    List<User> getAllUsers(){
        return userRepository.findAll();
    }

    @ApiOperation(value = "gets a specific user by their ID ", response = User.class, tags = "getUserById")
    @GetMapping(path = "/users/{id}")
    User getUserById( @PathVariable int id){
        return userRepository.findById(id);
    }

    @ApiOperation(value = "Creating a new user ", response = User.class, tags = "registerUser")
    @PostMapping("/users/register")
    User registerUser(@RequestBody User newUser) {
        List<User> users = userRepository.findAll();
        System.out.println("New user: " + newUser.toString());
        for (User user : users) {
            System.out.println("Registered user: " + newUser.toString());
            if (user.equals(newUser)) {
                return null;
            }
        }
        userRepository.save(newUser);

        return newUser;
    }
    
    @ApiOperation(value = "allowing a registered user to login ", response = User.class, tags = "loginUser")
    @PostMapping(path = "/users/login")
    User loginUser(@RequestBody User user) {
        List<User> users = userRepository.findAll();
        for (User other : users) {
            if (user.getUsername() != null && other.getUsername() != null && other.getUsername().equals(user.getUsername())
                    && other.getPassword().equals(user.getPassword())) {
                other.setLoggedIn(true);
                userRepository.save(other);
                return other;
            }
        }
        return null;
    }

    @ApiOperation(value = "allowing a registered user that has logged in to logout ", response = User.class, tags = "logUserOut")
    @PostMapping("/users/logout")
    User logUserOut(@RequestBody User user) {
        List<User> users = userRepository.findAll();
        for (User other : users) {
            if (other.equals(user)) {
                other.setLoggedIn(false);
                userRepository.save(other);
                return other;
            }
        }

        return null;
    }
}
