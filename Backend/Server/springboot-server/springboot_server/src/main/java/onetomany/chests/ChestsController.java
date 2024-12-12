package onetomany.chests;

import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.ApiOperation;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import onetomany.*;
import onetomany.Player.CustomizationRepository;
import onetomany.Users.UserRepository;


@RestController
public class ChestsController {

	
@Autowired
CustomizationRepository playerCusomizationRepository;

@Autowired
UserRepository userRepository;

@Autowired
ChestRepository chestRepository;
	
@ApiOperation(value = "adds a chest to the game ", response = Chests.class, tags = "addChest")
@PostMapping(path = "/chests/addChest")
String addChest(@RequestBody Chests newChest) {
	String Success = "Success";
	chestRepository.save(newChest);
	return Success;
}

@ApiOperation(value = "removes a chest from the game ", response = Chests.class, tags = "removeChest")
@PostMapping(path = "/chests/removeChest")
String removeChest(@RequestBody Chests newChest) {
	String Success = "Success";
	List<Chests> chest = chestRepository.findAll();
	for (Chests other : chest) {
        if (other.getX() == newChest.getX()
        		&& other.getY() == newChest.getY()
        			&& other.getWorldID() == newChest.getWorldID()) {
        	int id = other.getId();
        	System.out.println(id);
        	chestRepository.deleteById(id);
        	return Success;
        }
        }
	return null;
        
}

@ApiOperation(value = "adds an item to a chest to the game ", response = Chests.class, tags = "addItem")
@PostMapping(path = "/chests/addItem")
String addItem(@RequestBody Chests newChest) {
	String Success = "Success";
	List<Chests> chest = chestRepository.findAll();
	for (Chests other : chest) {
        if (other.getX() == newChest.getX()
        		&& other.getY() == newChest.getY()
        			&& other.getWorldID() == newChest.getWorldID()) {
        	other.setItem1(newChest.getItem1());
        	other.setItem2(newChest.getItem2());
        	other.setItem3(newChest.getItem3());
        	other.setItem4(newChest.getItem4());
        	other.setItem5(newChest.getItem5());
        	other.setItem6(newChest.getItem6());
        	other.setItem7(newChest.getItem7());
        	other.setItem8(newChest.getItem8());
        	other.setItem9(newChest.getItem9());
        	other.setItem10(newChest.getItem10());
        	other.setItem11(newChest.getItem11());
        	other.setItem12(newChest.getItem12());
        	other.setItem13(newChest.getItem13());
        	other.setItem14(newChest.getItem14());
        	other.setItem15(newChest.getItem15());
        	other.setItem16(newChest.getItem16());
        	other.setItem17(newChest.getItem17());
        	other.setItem18(newChest.getItem18());
        	other.setItem19(newChest.getItem19());
        	other.setItem20(newChest.getItem20());
        	other.setItem21(newChest.getItem21());
        	other.setItem22(newChest.getItem22());
        	other.setItem23(newChest.getItem23());
        	other.setItem24(newChest.getItem24());
        	other.setItem25(newChest.getItem25());
        	other.setItem26(newChest.getItem26());
        	other.setItem27(newChest.getItem27());
        	other.setItem28(newChest.getItem28());
        	other.setItem29(newChest.getItem29());
        	other.setItem30(newChest.getItem30());
        	System.out.println(other.getItem22());
        	chestRepository.save(other);
        	
        	return Success;
        }
	}
	return null;
}


@ApiOperation(value = "removes an item from a chest from the game ", response = Chests.class, tags = "removeItem")
@PostMapping(path = "/chests/removeItem")
String removeItem(@RequestBody Chests newChest) {
	String Success = "Success";
	List<Chests> chest = chestRepository.findAll();
	for (Chests other : chest) {
        if (other.getX() == newChest.getX()
        		&& other.getY() == newChest.getY()
        			&& other.getWorldID() == newChest.getWorldID()) {
        	other.setItem1(newChest.getItem1());
        	other.setItem2(newChest.getItem2());
        	other.setItem3(newChest.getItem3());
        	other.setItem4(newChest.getItem4());
        	other.setItem5(newChest.getItem5());
        	other.setItem6(newChest.getItem6());
        	other.setItem7(newChest.getItem7());
        	other.setItem8(newChest.getItem8());
        	other.setItem9(newChest.getItem9());
        	other.setItem10(newChest.getItem10());
        	other.setItem11(newChest.getItem11());
        	other.setItem12(newChest.getItem12());
        	other.setItem13(newChest.getItem13());
        	other.setItem14(newChest.getItem14());
        	other.setItem15(newChest.getItem15());
        	other.setItem16(newChest.getItem16());
        	other.setItem17(newChest.getItem17());
        	other.setItem18(newChest.getItem18());
        	other.setItem19(newChest.getItem19());
        	other.setItem20(newChest.getItem20());
        	other.setItem21(newChest.getItem21());
        	other.setItem22(newChest.getItem22());
        	other.setItem23(newChest.getItem23());
        	other.setItem24(newChest.getItem24());
        	other.setItem25(newChest.getItem25());
        	other.setItem26(newChest.getItem26());
        	other.setItem27(newChest.getItem27());
        	other.setItem28(newChest.getItem28());
        	other.setItem29(newChest.getItem29());
        	other.setItem30(newChest.getItem30());
        	chestRepository.save(other);
        	return Success;
        }
	}
	return null;
}

















}
