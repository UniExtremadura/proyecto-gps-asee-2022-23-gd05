package es.unex.dcadmin.users;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.net.URL;

import es.unex.dcadmin.roomdb.URLConverter;

@Entity(tableName="members")
public class Member {
    @PrimaryKey(autoGenerate = true)
    private long dbId;
    @ColumnInfo(name = "id")
    private long id;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "avatar")
    @TypeConverters(URLConverter.class)
    private URL avatar;
    @ColumnInfo(name = "server")
    private String server;

    public Member(long dbId, long id, String name, URL avatar, String server) {
        this.dbId = dbId;
        this.id = id;
        this.name = name;
        this.avatar = avatar;
        this.server = server;
    }
    @Ignore
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

    public long getDbId() {
        return dbId;
    }

    public void setDbId(long dbId) {
        this.dbId = dbId;
    }
}