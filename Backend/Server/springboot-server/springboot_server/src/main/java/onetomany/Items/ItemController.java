package onetomany.Items;

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


@RestController
public class ItemController {

    @Autowired
    ItemRepository itemRepository;

    @GetMapping(path = "/items")
    List<Item> getAllItems(){
        return itemRepository.findAll();
    }
    @ApiOperation(value = "Gets item by specific ID", response = Item.class, tags = "getItem")
    @GetMapping(path = "/getitem/{id}")
    Item getItem(@PathVariable int id){
        Item i = itemRepository.findById(id);
        return i;
    }
    @ApiOperation(value = "Adds item using request body", response = Item.class, tags = "addItembyBody")
    @PostMapping(path = "/additem1")
    String addItembyBody(@RequestBody Item i){
        if(i == null){
            return "item null";
        }
        itemRepository.save(i);
        return "item added";
    }
    @ApiOperation(value = "Adds item using request params", response = Item.class, tags = "addItembyParam")
    @PostMapping(path = "/additem2")
    String addItembyPram(@RequestParam String name,@RequestParam String description,@RequestParam String type){
        if(name == null || description == null || type == null){
            return "item null";
        }
        Item i = new Item(name, description, type);
        itemRepository.save(i);
        return "item added";
    }
    @ApiOperation(value = "Deletes item", response = Item.class, tags = "deleteItem")
    @DeleteMapping(path = "/delete/{id}")
    String deleteItem(@PathVariable int id){
        itemRepository.deleteById(id);
        //Cannot actually delete item from repository, throws some error
        return "Item with id: " + id + " removed";
    }
}