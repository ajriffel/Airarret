package onetomany.Worlds;

import java.io.IOException;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import onetomany.Player.CustomizationRepository;
import onetomany.Player.PlayerCusomization;
import onetomany.Users.User;
import onetomany.Users.UserRepository;

//username and character should be strings, world should be the world's integer ID
@Controller
@ServerEndpoint(value = "/worldsocket/{username}/{character}/{world}")
public class WorldSocket {
    private static WorldRepository worldRepository;
    private static UserRepository userRepository;
    private static CustomizationRepository playerRepository;

    @Autowired
    public void setWorldRepository(WorldRepository repo){
        worldRepository = repo;
    }
    @Autowired
    public void setUserRepository(UserRepository repo){
        userRepository = repo;
    }
    @Autowired
    public void setPlayerRepository(CustomizationRepository repo){
        playerRepository = repo;
    }

    private static Map<Session, String> sessionUsernameMap = new Hashtable<>();
	private static Map<String, Session> usernameSessionMap = new Hashtable<>();
    private static Map<Session, Integer> sessionWorldnameMap = new Hashtable<>();
    private static Map<Integer, Session> worldnameSessionMap = new Hashtable<>();
    private static Map<Session, String> sessionCharacterMap = new Hashtable<>();
    

    private final Logger logger = LoggerFactory.getLogger(WorldSocket.class);

    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username, @PathParam("character") String character, @PathParam("world") int world){
        sessionUsernameMap.put(session, username);
        usernameSessionMap.put(username, session);
        sessionWorldnameMap.put(session, world);
        worldnameSessionMap.put(world, session);
        sessionCharacterMap.put(session, character);
        //result string not needed or dispalyed, purely for testing
        String result = playerJoin(world, username, character);
    }

    @OnClose
    public void onClose(Session session) throws IOException{
        String username = sessionUsernameMap.get(session);
        int world = sessionWorldnameMap.get(session);
        String playername = sessionCharacterMap.get(session);
        //result string not needed or dispalyed, purely for testing
        String result = playerLeave(world, username, playername);
		sessionUsernameMap.remove(session);
		usernameSessionMap.remove(username);
        sessionWorldnameMap.remove(session);
        worldnameSessionMap.remove(world);
        //System.out.println("LEFT");
    }
    @OnMessage
    public void onMessasge(Session session, String message) throws IOException{
        //String content = message.substring(1);
        String username = sessionUsernameMap.get(session);
        int wid = sessionWorldnameMap.get(session);
        String playername = sessionCharacterMap.get(session);
        if(message.startsWith("P")){
            int mid = message.indexOf(",");
            int end = message.indexOf(".");
            int x = Integer.valueOf(message.substring(1,mid));
            int y = Integer.valueOf(message.substring(mid+1, end));
            String val = "(" + x + ", " + y + ")"; 
            characterUpdate(val, username, playername, wid);
            
            
        }
        else if(message.startsWith("B")){
            int comma = message.indexOf(",");
            int colon = message.indexOf(":");
            int x = Integer.valueOf(message.substring(1,comma));
            int y = Integer.valueOf(message.substring(comma+1, colon));
            int index = (x * 1000) + y;
            char c = message.charAt(colon+1);
            blockUpdate(index, username, wid, c);

            
        }
        sendUpdate(message, username);

    }

    @OnError
    public void onError(Session session, Throwable throwable){
        logger.info("Entered into Error");
		throwable.printStackTrace();
    }

    private void sendUpdate(String message, String exclude){
        /* 
        sessionUsernameMap.forEach((session, username) -> {
			try {
                if(!username.contentEquals(exclude)){
				    session.getAsyncRemote().sendText(message);
                }
			} 
      catch (IOException e) {
				logger.info("Exception: " + e.getMessage().toString());
				e.printStackTrace();
			}

		});
        */
        sessionUsernameMap.forEach((session, username) -> {
            if(!username.contentEquals(exclude)){
                session.getAsyncRemote().sendText(message);
            }
        });
    }

    private void blockUpdate(int index, String username, int wid, char c){
        List<User> all = userRepository.findAll();
            User user1 = null;
            for(User u : all){
                if(u.getUsername() != null && u.getUsername().equals(username)){
                    user1 = u;
                }
            }
            String result = "Failed to find world with specificied id";
            World w = worldRepository.findById(wid);
            if(w != null){
                if(index > 500000){
                    result = "Coordinates out of bounds";
                }
                else{
                    w.updateState(index, c);
                    //need some method here to send updates to all other players in world
                    worldRepository.save(w);
                }
            }
    }

    private void characterUpdate(String val, String username, String playername, int wid){
        List<User> all = userRepository.findAll();
        int uid = -1;
        for(User u : all){
            if(u.getUsername() != null && u.getUsername().equals(username)){
                uid = u.getId();
            }
        }
        int pid = -1;
        List<PlayerCusomization> players = playerRepository.findAll();
        for(PlayerCusomization p : players){
            if(p.getCharacterName() != null && p.getCharacterName().equals(playername)){
                pid = p.getId();
            }
        }
        World w = worldRepository.findById(wid);
        w.updatePlayer(pid, val);
        worldRepository.save(w);
    }

    private String playerJoin(int world, String username, String playername){
        List<User> all = userRepository.findAll();
        int uid = -1;
        for(User u : all){
            if(u.getUsername() != null && u.getUsername().equals(username)){
                uid = u.getId();
            }
        }
        if(uid == -1){
            return null;
        }
        PlayerCusomization u = null;
        List<PlayerCusomization> players = playerRepository.findAll();
        for(PlayerCusomization p : players){
            if(p.getCharacterName() != null && p.getCharacterName().equals(playername)){
                u = p;
            }
        }
        if(u == null){
            return null;
        }
        World w = worldRepository.findById(world);
        
        boolean able = w.addPlayer(u);
        if(able){
            worldRepository.save(w);
            return "Player succesfully added";
        } 
        return "Server full, cannot join";
    }

    private String playerLeave(int world, String username, String playername){
        List<User> all = userRepository.findAll();
        int uid = -1;
        for(User u : all){
            String name = u.getUsername();
            if(u.getUsername() != null && u.getUsername().equals(username)){
                uid = u.getId();
            }
        }
        if(uid == -1){
            return null;
        }
        int player = -1;
        List<PlayerCusomization> players = playerRepository.findAll();
        for(PlayerCusomization p : players){
            if(p.getCharacterName() != null && p.getCharacterName().equals(playername) && p.getUsername().equalsIgnoreCase(username)){
                player = p.getId();
            }
        }
        if(player == -1){
            return null;
        }
        
        World w = worldRepository.findById(world);
        
                w.removePlayer(player);
                worldRepository.save(w);
                return "Player removed succesfully";
           
        //return "User not on specificied world";
    }

}