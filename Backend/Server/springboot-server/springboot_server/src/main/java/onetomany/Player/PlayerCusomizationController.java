package onetomany.Player;


import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import onetomany.Users.User;
import onetomany.Users.UserRepository;


@RestController
public class PlayerCusomizationController {
	
	
				
	@Autowired
    CustomizationRepository playerCusomizationRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@ApiOperation(value = "Saving a players character customization ", response = PlayerCusomization.class, tags = "saveCustomization")
	@PostMapping(path = "/customization/save")
    PlayerCusomization saveCustomization(@RequestBody PlayerCusomization player) {
		System.out.println("New character: " + player.toString());
		List<User> user = userRepository.findAll();
		for (User other : user) {
            if (other.getUsername().equals(player.getUsername())){
            	System.out.println(player.getUsername());
            	System.out.println(other.getUsername());
            	List<PlayerCusomization> playa = playerCusomizationRepository.findByUser(player.getUsername());
            	for (PlayerCusomization player1 : playa) {
            		if (player1.getCharacterName().equals(player.getCharacterName())) {
            			System.out.println(player1.getCharacterName());
            			System.out.println(player.getCharacterName());
            			return null;
            		}
            		else {
            			playerCusomizationRepository.save(player);
                        return player;
            		}
            	}
            	playerCusomizationRepository.save(player);
                return player;
            }
		}
				return null;
    }
	
	@ApiOperation(value = "getting a specific username from the front end and sending the users information back ", response = PlayerCusomization.class, tags = "userList")
	@GetMapping(path = "/customization/{username}")
	List<String> userList(@PathVariable String username) {
		String [] userL = new String[5];
		List<String> userLis = new ArrayList<>();
		int counter = 0;
		List<User> user = userRepository.findAll();
		for (User other : user) {
            if (other.getUsername().equals(username)){
            	System.out.println(other.getUsername());
            	List<PlayerCusomization> playa = playerCusomizationRepository.findByUser(username);
            	for (int j = 0; j < playa.size(); j++) {
            		PlayerCusomization player1 = new PlayerCusomization();
            		player1 = playa.get(j);
            		System.out.println(player1.getCharacterName());
            		userL[counter] = player1.getCharacterName();
            		counter++;
            	}
            	for(int i = 0; i < 5; i++) {
            		userLis.add(userL[i]);
            	}
            }
		}
		return userLis;
	}
	
	@ApiOperation(value = "delete a character from a users character list ", response = PlayerCusomization.class, tags = "deleteCharacter")
	@DeleteMapping(path = "deleteCharacter/{characterName}/{username}")
    String deleteCharacter(@PathVariable String characterName, @PathVariable String username) {
        System.out.println(characterName);
        System.out.println(username);
        String Success = "Success";
        List<User> user = userRepository.findAll();
        for (User other : user) {
            if (other.getUsername().equals(username)){
                List<PlayerCusomization> playa = playerCusomizationRepository.findByUser(username);
                for (PlayerCusomization player1 : playa) {
                    System.out.println(player1.getCharacterName());
                    if (player1.getCharacterName().equals(characterName)) {
                        int id = player1.getId();
                        System.out.println(id);
                        System.out.println(player1.getCharacterName());
                        playerCusomizationRepository.deleteById(id);
                        return Success;
                    }
                }
            }
        }
        return null;
    }
	
	@ApiOperation(value = "sends a characters information to the frontend ", response = PlayerCusomization.class, tags = "characterInfo")
	@PostMapping(path = "characterInfo/{characterName}/{username}")
    PlayerCusomization characterInfo(@PathVariable String characterName, @PathVariable String username) {
        List<User> user = userRepository.findAll();
        for (User other : user) {
            if (other.getUsername().equals(username)){
                List<PlayerCusomization> playa = playerCusomizationRepository.findByUser(username);
                for (PlayerCusomization player1 : playa) {
                    System.out.println(player1.getCharacterName());
                    if (player1.getCharacterName().equals(characterName)) {
                        return player1;
                    }
                }
            }
                    }
        return null;
    }
	
	
	
	
	
	
	
	
}


















