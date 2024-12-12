package onetomany.Friends;

import java.util.List;

import javax.persistence.Table;
import javax.websocket.server.PathParam;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import onetomany.Users.User;
import onetomany.Users.UserRepository;

@RestController
public class FriendController {

    @Autowired
    FriendRepository friendRepository;

    @Autowired
    UserRepository userRepository;

    @GetMapping(path = "/friends")
    List<Friend> getAllFriends(){
        return friendRepository.findAll();
    }
    @ApiOperation(value = "Returns a user's list of friends", response = Friend.class, tags = "getUserFriends")
    @GetMapping(path = "/allFriends/{username}")
    List<String> getUserFriends(@PathVariable String username){
        List<User> listOfUsers = userRepository.findAll();
        int id = (User.findByName(listOfUsers, username)).getId();
        List<Friend> fList = friendRepository.findAll();
        List<String> uList = new ArrayList<>();
        for(Friend f : fList){
            if(f.getUser1().getId() == id){
                uList.add(f.getUser2().getUsername());
            }
            else if(f.getUser2().getId() == id){
                uList.add(f.getUser1().getUsername());
            }
        }
        return uList;
    }

    @GetMapping(path = "/friends/{id}")
    Friend getFriendById( @PathVariable int id){
        return friendRepository.findById(id);
    }
    @ApiOperation(value = "Adds a friend to user's list of friends", response = Friend.class, tags = "createFriend")
    @PostMapping(path = "/addFriend")
    String createFriend(@RequestParam String u1, @RequestParam String u2){
        List<Friend> fList = friendRepository.findAll();
        String r = null;
        for(Friend f : fList){
            String check1 = f.getUser1().getUsername();
            String check2 = f.getUser2().getUsername();
            if((u1.equals(check1) && u2.equals(check2) ) || (u1.equals(check2) && u2.equals(check1) || (u1.equals(u2)))){
                r = "Users already friends";
                break;
            }
        }
        if(r == null){
            List<User> uList = userRepository.findAll();
            User user1 = User.findByName(uList, u1);
            User user2 = User.findByName(uList, u2);
            if(user1 == null || user2 == null){
                return "User not found";
            }
            Friend f = new Friend(user1, user2);
            friendRepository.save(f);
            r = "Success";
        }
        return r;
    }
      
}
