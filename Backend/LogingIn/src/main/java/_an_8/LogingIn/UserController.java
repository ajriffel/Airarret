package _an_8.LogingIn;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import _an_8.LogingIn.*;


@RestController
public class UserController {
	
    @Autowired
    UserRepository userRepository;
    
    @PostMapping("/users/register")
    String registerUser(@RequestParam String username, @RequestParam String password ) {
    	User newUser = new User();
    	newUser.setUsername(username);
    	newUser.setPassword(password);
        List<User> users = userRepository.findAll();
        System.out.println("New user: " + newUser.toString());
        for (User user : users) {
            System.out.println("Registered user: " + newUser.toString());
            if (user.equals(newUser)) {
            	return  "User Already exists!";
            }
        }
        userRepository.save(newUser);
        return "Success";
    }
    
    
    @PostMapping(path = "/users/login")
    String loginUser(@RequestParam String username, @RequestParam String password) {
    	User user = new User();
    	user.setUsername(username);
    	user.setPassword(password);
        List<User> users = userRepository.findAll();
        for (User other : users) {
            if (other.getUsername().equals(user.getUsername())
            		&& other.getPassword().equals(user.getPassword())) {
                user.setLoggedIn(true);
                userRepository.save(user);
                return "SUCCESS";
            }
        }
        return "failed";
    }
    
    
}
