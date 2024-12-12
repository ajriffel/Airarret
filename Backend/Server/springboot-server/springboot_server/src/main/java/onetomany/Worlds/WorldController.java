package onetomany.Worlds;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import onetomany.Friends.Friend;
import onetomany.Friends.FriendRepository;
import onetomany.Player.CustomizationRepository;
import onetomany.Player.PlayerCusomization;
import onetomany.Users.UserRepository;
import onetomany.Users.User;


@RestController
public class WorldController {

    @Autowired
    WorldRepository worldRepository;


    @Autowired
    UserRepository userRepository;

    @Autowired
    CustomizationRepository playerRepository;

    @Autowired
    FriendRepository friendRepository;

    @ApiOperation(value = "Creates a world object belonging to a user ", response = World.class, tags = "createWorld")
    @PostMapping(path = "/createWorld")
    int createWorld(@RequestParam String name, @RequestParam char difficulty, @RequestParam String username){
        List<User> allUser = userRepository.findAll();
        User fOwner = null;
        for(User u : allUser){
            if(u.getUsername().equals(username)){
                fOwner = u;
            }
        }
        if(fOwner == null){
            return -1;
        }
        World w = new World(name, difficulty, fOwner);
        worldRepository.save(w);
        return w.getId();
    }
    @ApiOperation(value = "Fills in the worlds contents", response = World.class, tags = "addToWorld")
    @PostMapping(path = "/addWorldState")
    String addToWorld(@RequestParam String worldstate, @RequestParam int wid){
        World w = worldRepository.findById(wid);
        boolean r = w.addWorldState(worldstate);
        worldRepository.save(w);
        String s = String.valueOf(r);
        return s;
    }

    //takes world id and retrieves its info from the repo
    @GetMapping(path = "/getWorld/{id}")
    World getWorld(@PathVariable int id){
        World w = worldRepository.findById(id);
        w.clearState();
        return w;
    }
    @GetMapping(path = "/getWorldUsers/{id}")
    String getWorldUsers(@PathVariable int id){
        World w = worldRepository.findById(id);
        String s = w.getUsers();
        return s;
    }
    @GetMapping(path = "/getWorldPositions/{id}")
    String getWorldPositions(@PathVariable int id){
        World w = worldRepository.findById(id);
        String s = w.getPostitions();
        return s;
    }

    @ApiOperation(value = "Returns a section of world state from start to end", response = World.class, tags = "getWorldState")
    @GetMapping(path = "/getWorld/{id}/{start}/{end}")
    String getWorldState(@PathVariable int id, @PathVariable int start, @PathVariable int end){
        World w = worldRepository.findById(id);
        String state = w.getFrom(start, end);
        return state;
    }

    @GetMapping(path = "/getWorldInfo/{id}")
    World getWorldInfo(@PathVariable int id){
        World w = worldRepository.findById(id);
        String info[] = new String[3];
        info[0] = String.valueOf(w.getDifficulty());
        info[1] = String.valueOf(1);
        info[2] = w.getName();
        return w;
    }

    //takes user id and retrieves list of worlds belonging to that user
    @ApiOperation(value = "Gets all of users worlds and friends worlds", response = World.class, tags = "getUsersWorldsList")
    @GetMapping(path = "/getUserWorldList/{username}")
    List<String> getUsersWorldsList(@PathVariable String username){
        List<User> all = userRepository.findAll();
        int id = -1;
        for(User u : all){
            if(u.getUsername() != null && u.getUsername().equals(username)){
                id = u.getId();
            }
        }
        if(id == -1){
            return null;
        }
        List<World> worldList = worldRepository.findAll();
        String[] userWorlds = new String[50];
        int index = 0;
        for(int i = 0; i < worldList.size(); i++){
            World w = worldList.get(i);
            if(index == 5){
                break;
            }
            if(w.getOwner() != null && w.getOwner().getId() == id){
                userWorlds[index] = w.getName();
                userWorlds[index + 5] = String.valueOf(w.getId());
                index++;
            }
        }
        if(index < 5){
            for(;index < 5; index++){
                userWorlds[index] = "";
                userWorlds[index + 5] = "";
            }
        }
        List<Friend> fList = friendRepository.findAll();
        List<User> friends = new ArrayList<>();
        for(Friend f : fList){
            if(f.getUser1().getId() == id){
                friends.add(f.getUser2());
            }
            else if(f.getUser2().getId() == id){
                friends.add(f.getUser1());
            }
        }
        //List<World> active = new ArrayList<>();
        index = 10;
        for(User u : friends){
            for(World world : worldList){
                if(world.getOwner().getId() == u.getId() && world.getActive() == 1){
                    userWorlds[index] = u.getUsername();
                    userWorlds[index + 20] = String.valueOf(world.getId());
                    index++;
                }
            }
        }
        if(index < 30){
            for(;index < 30; index++){
                userWorlds[index] = "";
                userWorlds[index + 20] = "";
            }
        }
        List<String> userWorldList = new ArrayList<>();
        for(int i = 0; i < 50; i++){
            userWorldList.add(userWorlds[i]);
        }
        return userWorldList;
    }

    @GetMapping(path = "/getworldList/{id}")
    List<World> getUsersWorlds(@PathVariable int id){
        List<World> worldList = worldRepository.findAll();
        List<World> userWorlds = new ArrayList<>();
        for(World w : worldList){
            if(w.getOwner().getId() == id){
                userWorlds.add(w);
            }
        }
        return userWorlds;
    }

    @ApiOperation(value = "Deletes World ", response = World.class, tags = "deleteWorld")
    @DeleteMapping(path = "/deleteWorld/{id}")
    String deleteWorld(@PathVariable int id){
        World w = worldRepository.findById(id);
        String name = w.getName();
        w.nullOut();
        worldRepository.save(w);
        worldRepository.deleteById(id);
        return name;
    }

    @DeleteMapping(path = "/cleanWorlds")
    String cleanWorld(){
        List<World> list= worldRepository.findAll();
        for(World w : list){
            if(w.getOwner() == null){
                w.nullOut();
                worldRepository.save(w);
                worldRepository.deleteById(w.getId());
            }
        }
        return "Finished";
    }
    
}
