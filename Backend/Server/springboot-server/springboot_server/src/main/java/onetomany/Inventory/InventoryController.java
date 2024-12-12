package onetomany.Inventory;

import java.util.List;

import onetomany.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class InventoryController {

	@Autowired
	InventoryRepository InventoryRepository;
	
	@PostMapping(path="addInventory/{characterName}/{username}")
	String addInventory(@PathVariable String characterName, @PathVariable String username) {
		String Success = "It worked";
		Inventory other = new Inventory(username,characterName, null, null, null, null, null, null, null, null, null, null,
												null, null,null,null,null,null,null,null,null,null,null,null,null,null,null,
												null, null,1);
	        InventoryRepository.save(other);
			return Success;
	        }
		
	
	
	@PostMapping(path="delInventory/{characterName}/{username}")
	String delInventory(@PathVariable String characterName, @PathVariable String username){
		String Success = "It worked";
		List<Inventory> inventory = InventoryRepository.findAll();
		for (Inventory other : inventory) {
	        if (other.getUsername().equals(username)
	        		&& other.getCharacterName().equals(characterName)){
	        InventoryRepository.deleteById(other.getId());
			return Success;
					}
		}
		return null;
	        }
	
	@PostMapping(path = "/inventory/addItem")
	String addItem(@RequestBody Inventory newSlot) {
		String Success = "It works";
		String Failed = "user not found";
		List<Inventory> inventory = InventoryRepository.findAll();
		for (Inventory other : inventory) {
	        if (other.getUsername().equals(newSlot.getUsername())
	        		&& other.getCharacterName().equals(newSlot.getCharacterName())){
	        	other.setSlot1(newSlot.getSlot1());
	        	other.setSlot2(newSlot.getSlot2());
	        	other.setSlot3(newSlot.getSlot3());
	        	other.setSlot4(newSlot.getSlot4());
	        	other.setSlot5(newSlot.getSlot5());
	        	other.setSlot6(newSlot.getSlot6());
	        	other.setSlot7(newSlot.getSlot7());
	        	other.setSlot8(newSlot.getSlot8());
	        	other.setSlot9(newSlot.getSlot9());
	        	other.setSlot10(newSlot.getSlot10());
	        	other.setSlot11(newSlot.getSlot11());
	        	other.setSlot12(newSlot.getSlot12());
	        	other.setSlot13(newSlot.getSlot13());
	        	other.setSlot14(newSlot.getSlot14());
	        	other.setSlot15(newSlot.getSlot15());
	        	other.setSlot16(newSlot.getSlot16());
	        	other.setSlot17(newSlot.getSlot17());
	        	other.setSlot18(newSlot.getSlot18());
	        	other.setSlot19(newSlot.getSlot19());
	        	other.setSlot20(newSlot.getSlot20());
	        	other.setSlot21(newSlot.getSlot21());
	        	other.setSlot22(newSlot.getSlot22());
	        	other.setSlot23(newSlot.getSlot23());
	        	other.setSlot24(newSlot.getSlot24());
	        	other.setSlot25(newSlot.getSlot25());
	        	other.setSlot26(newSlot.getSlot26());
	        	other.setSlot27(newSlot.getSlot27());
	        	other.setSelected(newSlot.getSelected());
	        	InventoryRepository.save(other);
	        	return Success;
	        }
	       }
		return Failed;
	}
	
	@PostMapping(path = "/inventory/removeItem")
	String removeItem(@RequestBody Inventory newSlot) {
	String Success = "It works";
		String Failed = "user not found";
		List<Inventory> inventory = InventoryRepository.findAll();
		for (Inventory other : inventory) {
	        if (other.getUsername().equals(newSlot.getUsername())
	        		&& other.getCharacterName().equals(newSlot.getCharacterName())){
	        	other.setSlot1(newSlot.getSlot1());
	        	other.setSlot2(newSlot.getSlot2());
	        	other.setSlot3(newSlot.getSlot3());
	        	other.setSlot4(newSlot.getSlot4());
	        	other.setSlot5(newSlot.getSlot5());
	        	other.setSlot6(newSlot.getSlot6());
	        	other.setSlot7(newSlot.getSlot7());
	        	other.setSlot8(newSlot.getSlot8());
	        	other.setSlot9(newSlot.getSlot9());
	        	other.setSlot10(newSlot.getSlot10());
	        	other.setSlot11(newSlot.getSlot11());
	        	other.setSlot12(newSlot.getSlot12());
	        	other.setSlot13(newSlot.getSlot13());
	        	other.setSlot14(newSlot.getSlot14());
	        	other.setSlot15(newSlot.getSlot15());
	        	other.setSlot16(newSlot.getSlot16());
	        	other.setSlot17(newSlot.getSlot17());
	        	other.setSlot18(newSlot.getSlot18());
	        	other.setSlot19(newSlot.getSlot19());
	        	other.setSlot20(newSlot.getSlot20());
	        	other.setSlot21(newSlot.getSlot21());
	        	other.setSlot22(newSlot.getSlot22());
	        	other.setSlot23(newSlot.getSlot23());
	        	other.setSlot24(newSlot.getSlot24());
	        	other.setSlot25(newSlot.getSlot25());
	        	other.setSlot26(newSlot.getSlot26());
	        	other.setSlot27(newSlot.getSlot27());
	        	other.setSelected(newSlot.getSelected());
	        	InventoryRepository.save(other);
	        	return Success;
	        }
	       }
		return Failed;
	}
	
	@PostMapping(path = "/inventory/selectItem")
	String selectItem(@RequestBody Inventory newSlot) {
		String Success = "It works";
		String Failed = "user not found";
		List<Inventory> inventory = InventoryRepository.findAll();
		for (Inventory other : inventory) {
	        if (other.getUsername().equals(newSlot.getUsername())
	        		&& other.getCharacterName().equals(newSlot.getCharacterName())){
	        	other.setSlot1(newSlot.getSlot1());
	        	other.setSlot2(newSlot.getSlot2());
	        	other.setSlot3(newSlot.getSlot3());
	        	other.setSlot4(newSlot.getSlot4());
	        	other.setSlot5(newSlot.getSlot5());
	        	other.setSlot6(newSlot.getSlot6());
	        	other.setSlot7(newSlot.getSlot7());
	        	other.setSlot8(newSlot.getSlot8());
	        	other.setSlot9(newSlot.getSlot9());
	        	other.setSlot10(newSlot.getSlot10());
	        	other.setSlot11(newSlot.getSlot11());
	        	other.setSlot12(newSlot.getSlot12());
	        	other.setSlot13(newSlot.getSlot13());
	        	other.setSlot14(newSlot.getSlot14());
	        	other.setSlot15(newSlot.getSlot15());
	        	other.setSlot16(newSlot.getSlot16());
	        	other.setSlot17(newSlot.getSlot17());
	        	other.setSlot18(newSlot.getSlot18());
	        	other.setSlot19(newSlot.getSlot19());
	        	other.setSlot20(newSlot.getSlot20());
	        	other.setSlot21(newSlot.getSlot21());
	        	other.setSlot22(newSlot.getSlot22());
	        	other.setSlot23(newSlot.getSlot23());
	        	other.setSlot24(newSlot.getSlot24());
	        	other.setSlot25(newSlot.getSlot25());
	        	other.setSlot26(newSlot.getSlot26());
	        	other.setSlot27(newSlot.getSlot27());
	        	other.setSelected(newSlot.getSelected());
	        	InventoryRepository.save(other);
	        	return Success;
	        }
	       }
		return Failed;
	}
	
	
	
	@PostMapping(path = "/inventory/showItems")
	Inventory showItems(@RequestBody Inventory newSlot) {
		String Success = "It works";
		String Failed = "User not found";
		List<Inventory> inventory = InventoryRepository.findAll();
		for (Inventory other : inventory) {
	        if (other.getUsername().equals(newSlot.getUsername())
	        		&& other.getCharacterName().equals(newSlot.getCharacterName())){
	        	return other;
	        }
	       }
		return null;
	}
	
	
}
