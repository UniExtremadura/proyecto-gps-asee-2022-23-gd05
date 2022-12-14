package es.unex.dcadmin.command;

import android.content.Context;
import android.content.Intent;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.listener.message.MessageCreateListener;
import org.javacord.api.util.event.ListenerManager;

import java.util.HashMap;

import es.unex.dcadmin.commandRecord.CommandRecord;
import es.unex.dcadmin.roomdb.AppDatabase;


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
    DiscordApi discordApi;
    @Ignore
    HashMap<String, ListenerManager<MessageCreateListener>> mapMessageCreated;
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
        this.discordApi = null;
    }

    @Ignore//Ejecutarcomando
    public Command(long id, String name, String trigger_text, String action_text, DiscordApi discordApi, HashMap<String, ListenerManager<MessageCreateListener>> mapMessageCreated){
        this.id = id;
        this.name = name;
        this.trigger_text = trigger_text;
        this.action_text = action_text;
        this.discordApi = discordApi;
        this.mapMessageCreated = mapMessageCreated;
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

    public DiscordApi getDiscordApi() {
        return discordApi;
    }

    public void setDiscordApi(DiscordApi discordApi) {
        this.discordApi = discordApi;
    }

    public HashMap<String, ListenerManager<MessageCreateListener>> getMapMessageCreated() {
        return mapMessageCreated;
    }

    public void setMapMessageCreated(HashMap<String, ListenerManager<MessageCreateListener>> mapMessageCreated) {
        this.mapMessageCreated = mapMessageCreated;
    }

    public void construir(DiscordApi api, HashMap<String, ListenerManager<MessageCreateListener>> mapaMessageCreated, Context context){
        this.discordApi = api;
        this.mapMessageCreated = mapaMessageCreated;
        ListenerManager<MessageCreateListener> listenerManager = api.addMessageCreateListener(event -> {
            if(event.getMessageContent().equals(trigger_text) && !event.getMessageAuthor().isBotUser())
            {
                // Definir aqui el parametro que necesitara la accion.
                TextChannel canal = event.getChannel();
                canal.sendMessage(action_text);

                //Esto es para el historial de comandos, modificar el usuario
                //Ahora añadimos a la BD si no estaba ya, para ello, comprobamos si estaba, si estaba, actualizamos sumando 1 en
                CommandRecord cr = AppDatabase.getInstance(context).getCommandRecordDao().get(getName(),event.getMessageAuthor().getDisplayName());
                if(cr != null){
                    cr.setNumExecutions(cr.getNumExecutions()+1);
                    AppDatabase.getInstance(context).getCommandRecordDao().update(cr);
                }
                else{
                    cr = new CommandRecord(getName(),1,event.getMessageAuthor().getDisplayName());
                    AppDatabase.getInstance(context).getCommandRecordDao().insert(cr);
                }

            }
        });

        mapaMessageCreated.put(trigger_text, listenerManager);
        discordApi.addListener(mapaMessageCreated.get(trigger_text).getListener());
    }

    public boolean isConstruido(){ //Es para construir los comandos al iniciar la aplicacion (Ejecutar comando), lo siguiente esta en loaditems de commandactivity(en addcomand y updatecommand), en addcommandfragment y commandDetail, ambos en el boton la parte de no añadir si existe uno con ese trigger
        if(discordApi != null) return true;//Tambien en command activity al actualizar el comando para construirlo y en commandDetaul en para destruirlo
        else return false;
    }

}
