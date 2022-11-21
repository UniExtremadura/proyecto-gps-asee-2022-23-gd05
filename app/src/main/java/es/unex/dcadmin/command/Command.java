package es.unex.dcadmin.command;

import android.content.Intent;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.text.ParseException;
        import java.text.SimpleDateFormat;
        import java.util.Date;
        import java.util.Locale;



// Do not modify
@Entity(tableName="command")
public class Command {
    @Ignore
    public final static String NAME = "name";
    @ColumnInfo(name = "name")
    private String name = new String();
    @PrimaryKey(autoGenerate = true)
    private long id;
    @ColumnInfo(name = "trigger_text")
    String trigger_text;
    @ColumnInfo(name = "action_text")
    String action_text;
    @Ignore
    public Command(String name, String trigger_text, String action_text) {
        this.name = name;
        this.trigger_text = trigger_text;
        this.action_text = action_text;
    }

    // Create a new Command from data packaged in an Intent
    @Ignore
    public Command(Intent intent) {//Esto crea un item a partir de un intent para que sea mejor, en lugar de añadir este código en el manager, se crea aquí

        name = intent.getStringExtra(Command.NAME);
    }

    public Command(long id, String name, String trigger_text, String action_text){
        this.id = id;
        this.name = name;
        this.trigger_text = trigger_text;
        this.action_text = action_text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Take a set of String data values and
    // package them for transport in an Intent

    public static void packageIntent(Intent intent, String name) {
        //Esto tambien es para ahorrar código en la otra activity
        intent.putExtra(Command.NAME, name);

    }

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

    public String getTrigger_text(){
        return trigger_text;
    }

    public void setTrigger_text(String trigger_text) {
        this.trigger_text = trigger_text;
    }

    public String getAction_text() {
        return action_text;
    }

    public void setAction_text(String action_text) {
        this.action_text = action_text;
    }
}
