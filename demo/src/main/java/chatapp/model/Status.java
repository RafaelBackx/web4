package chatapp.model;
public enum Status {
    ONLINE("online"),
    OFFLINE("offline"),
    AWAY("away"),
    OTHER("other");

    private String name;
    Status(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }
}
