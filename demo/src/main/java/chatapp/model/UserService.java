package chatapp.model;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service("userService")
public class UserService {
    private List<User> users;

    public UserService(){
        this.users = new ArrayList<>();
        User rafael = new User("rafael","password",Status.ONLINE);
        User jonas = new User("jonas","password",Status.AWAY);
        User thibault = new User("thibault","password",Status.OFFLINE);
        User dieter = new User("dieter","password", Status.OFFLINE);
        User thijs = new User("thijs","password",Status.OFFLINE);
        this.addUser(dieter);
        this.addUser(thijs);
        this.addUser(rafael);
        this.addUser(jonas);
        this.addUser(thibault);
    }

    public List<User> getUsers(){
        return this.users;
    }

    public void addUser(User u){
        this.users.add(u);
    }

    public void removeUser(User u){
        this.users.remove(u);
    }

    public User getLoggedIn(String username,String password){
        for (User u :getUsers()){
            if (u.getName().equalsIgnoreCase(username) && u.getPassword().equals(password)){
                return u;
            }
        }
        return null;
    }

    public User getUser(String name){
        for (User u:users){
            if (u.getName().equalsIgnoreCase(name)){
                return u;
            }
        }
        return null;
    }
}
