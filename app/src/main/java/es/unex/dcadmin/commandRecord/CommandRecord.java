package es.unex.dcadmin.commandRecord;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName="commandRecord")
public class CommandRecord {
    @Ignore
    public final static String NAME = "name";
    @Ignore
    public final static String NUMEXECUTIONS = "numExecutions";
    @Ignore
    public final static String USERID = "userId";
    @ColumnInfo(name = "name")
    private String name = new String();
    @PrimaryKey(autoGenerate = true)
    private long id;
    @ColumnInfo(name = "numExecutions")
    private int numExecutions;
    @ColumnInfo(name = "userId")
    private String userId;
    @Ignore
    public CommandRecord(String name, int numExecutions, String userId) {

        this.name = name;
        this.numExecutions = numExecutions;
        this.userId = userId;
    }

    // Create a new Command from data packaged in an Intent
    @Ignore
    public CommandRecord(Intent intent) {//Esto crea un item a partir de un intent para que sea mejor, en lugar de añadir este código en el manager, se crea aquí

        name = intent.getStringExtra(NAME);
        numExecutions = intent.getIntExtra(NUMEXECUTIONS,0);
        userId = intent.getStringExtra(USERID);
    }

    public CommandRecord(long id, String name, int numExecutions, String userId){
        this.id = id;
        this.name = name;
        this.numExecutions = numExecutions;
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Take a set of String data values and
    // package them for transport in an Intent

    public static void packageIntent(Intent intent, String name, int numExecutions, String userId) {
        //Esto tambien es para ahorrar código en la otra activity
        intent.putExtra(NAME, name);
        intent.putExtra(NUMEXECUTIONS,numExecutions);
        intent.putExtra(USERID,userId);

    }

    @NonNull
    public String toString() {
        return name;
    }

    public String toLog() {
        return "Name:" + name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id){
        this.id = id;
    }

    public int getNumExecutions(){
        return numExecutions;
    }

    public void setNumExecutions(int numExecutions) {
        this.numExecutions = numExecutions;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}

