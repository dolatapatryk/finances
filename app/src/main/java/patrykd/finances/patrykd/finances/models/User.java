package patrykd.finances.patrykd.finances.models;

import lombok.Data;

public @Data
class User {

    private int id;
    private String login;
    private String password;

    public User(int id, String login, String password){
        this.id = id;
        this.login = login;
        this.password = password;
    }

    public User(String login, String password){
        this.login = login;
        this.password = password;
    }

    public User(){
        this.login = "";
        this.password = "";
    }
}
