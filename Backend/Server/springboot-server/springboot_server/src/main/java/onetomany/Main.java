package onetomany;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import onetomany.Friends.FriendRepository;
import onetomany.Items.Item;
import onetomany.Items.ItemRepository;
import onetomany.Player.PlayerCusomization;
import onetomany.Users.User;
import onetomany.Users.UserRepository;
import onetomany.Worlds.WorldRepository;
import onetomany.Worlds.World;
import onetomany.Player.CustomizationRepository;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    CommandLineRunner initUser(UserRepository userRepository,  FriendRepository friendRepository, ItemRepository itemRepository, WorldRepository worldRepository,
     CustomizationRepository playerRepository) {
        return args -> {

        };
    }

    @Bean
    public Docket getAPIdocs() {
        return new Docket(DocumentationType.SWAGGER_2)  
          .select()                                  
          .apis(RequestHandlerSelectors.any())              
          .paths(PathSelectors.any())                          
          .build(); 
    }

}