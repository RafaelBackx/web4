package be.ucll.demo.model;

import java.util.HashSet;
import java.util.Set;

public class User {
    private String name;
    private Status status;
    private String password;
    private String statusname;
    private Set<User> friendlist;

    public User(String name,String password, Status status) {
        this.name = name;
        this.password = password;
        this.status = status;
        this.statusname = status.getName();
        friendlist = new HashSet<>();
    }

    public String getName() {
        return name;
    }

    public Status getStatus() {
        return status;
    }

    public String getPassword(){return password;}

    public String getStatusname() {
        return statusname;
    }

    public void addFriend(User u){
        if (u != null){
            this.friendlist.add(u);
        }
    }

    public Set<User> getFriendlist(){
        return friendlist;
    }

    public String toString(){
        return name + " - "  + statusname;
    }

    public void setStatus(Status s){
        this.status = s;
        this.statusname = s.getName();
    }
}
