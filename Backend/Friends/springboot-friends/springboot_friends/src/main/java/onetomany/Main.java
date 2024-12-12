package onetomany;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import onetomany.Friends.Friend;
import onetomany.Friends.FriendRepository;
import onetomany.Users.User;
import onetomany.Users.UserRepository;

@SpringBootApplication
class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    CommandLineRunner initUser(UserRepository userRepository,  FriendRepository friendRepository) {
        return args -> {
            //User user1 = new User("Jimothy");
            //User user2 = new User("Bobert");
            //User user3 = new User("Jombert");
            //userRepository.save(user1);
            //userRepository.save(user2);
            //userRepository.save(user3);
            //friendRepository.save(new Friend(user1, user2));
            
        };
    }

}