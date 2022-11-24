package es.unex.dcadmin.users;

import java.net.URL;

public class Member {
    private long id;
    private String name;
    private URL avatar;
    private String server;


    public Member(long id, String name, URL avatar, String server) {
        this.id = id;
        this.name = name;
        this.avatar = avatar;
        this.server = server;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public URL getAvatar() {
        return avatar;
    }

    public void setAvatar(URL avatar) {
        this.avatar = avatar;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }
}