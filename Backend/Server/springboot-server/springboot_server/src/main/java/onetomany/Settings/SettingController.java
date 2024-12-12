
package onetomany.Settings;

import java.util.List;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import onetomany.Users.User;
import onetomany.Users.UserRepository;
@RestController
public class SettingController {

    @Autowired
    SettingsRepository settingRepository;

    @Autowired
    UserRepository userRepository;

    @GetMapping(path = "/settings")
    List<Setting> getAllSettings(){
        return settingRepository.findAll();
    }
    /* 
    @GetMapping(path = "/color/{name}")
    String getColor(@PathVariable String name){
        List<User> uList = userRepository.findAll();
        for(User s : uList){
            if(s.getUsername().equals(name)){
                if(s.getSettings() == null){
                    Setting newSet = new Setting(s);
                    newSet.setName(name);
                    s.addSettings(newSet);
                    settingRepository.save(newSet);
                }
                return s.getSettings().getColor();
            }
        }
        return "User not found";
    }
    @GetMapping(path = "/resolution/{name}")
    String getResolution(@PathVariable String name){
        List<User> uList = userRepository.findAll();
        for(User s : uList){
            if(s.getUsername().equals(name)){
                if(s.getSettings() == null){
                    Setting newSet = new Setting(s);
                    s.addSettings(newSet);
                    settingRepository.save(newSet);
                }
                return s.getSettings().getResolution();
            }
        }
        return "User not found";
    }
    */
    /*
    @PostMapping(path = "/editResolution/{name}/{val}")
    String setResolution(@PathVariable String name, @PathVariable String val){
        List<Setting> setList = settingRepository.findAll();
        for(Setting s : setList){
            User u = s.getUser();
            if(u.getName().equals(name)){
                s.setResolution(val);
                settingRepository.save(s);
                return "Resolution set";
            }
        }
        return "User not found";
    }*/
    @ApiOperation(value = "updates a settings values ", response = Setting.class, tags = "setValues")
    @PostMapping(path = "/editValues")
    Setting setValues(@RequestBody Setting oldSet){
        System.out.println(oldSet.getName());
        List<Setting> setList = settingRepository.findAll();
        for(Setting s : setList){
            if(oldSet.getName() != null && s.getName() != null && oldSet.getName().equals(s.getName())){
                s.setColor(oldSet.getColor());
                s.setResolution(oldSet.getResolution());
                settingRepository.save(s);
                return s;
            }
        }
        return null;
    }
}
