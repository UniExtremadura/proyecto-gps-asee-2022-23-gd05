package es.unex.dcadmin.discord;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.listener.message.MessageCreateListener;
import org.javacord.api.util.event.ListenerManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import es.unex.dcadmin.AppExecutors;
import es.unex.dcadmin.MainActivity;
import es.unex.dcadmin.command.AddCommandFragment;
import es.unex.dcadmin.users.Member;

public class discordApiManager {
    private DiscordApi api = null;
    private String token = "";
    private HashMap<String, ListenerManager<MessageCreateListener>> mapaMessageCreated;
    private MutableLiveData<List<Member>> memberList;
    public static discordApiManager discordApi = null;

    private callbackSetCorrectData mCorrectCallback;
    private callbackSetIncorrectData mIncorrectCallback;

    public interface callbackSetCorrectData {
        public void setCorrectCallback();
    }

    public interface callbackSetIncorrectData {
        public void setIncorrectCallback();
    }

    public discordApiManager(){
        memberList = new MutableLiveData<>();
        mapaMessageCreated = new HashMap<>();
    }

    public static discordApiManager getSingleton() {
        if(discordApi == null){
            discordApi = new discordApiManager();
        }
        return discordApi;
    }

    public void getUsers() {
        List<Member> members = new ArrayList<>();
        AppExecutors.getInstance().networkIO().execute(new Runnable() {
            @Override
            public void run() {
                List<Server> servers = new ArrayList<>(getSingleton().getApi(null).getServers());
                for(Server s: servers) {
                    for(User u: s.getMembers()) {
                        Member m = new Member(u.getId(), u.getName(), u.getAvatar().getUrl(), s.getName());
                        members.add(m);
                    }
                }
            }
        });

        memberList.postValue(members);
    }

    public DiscordApi getApi(Context context){
        if (api == null) {
            if (mapaMessageCreated == null) mapaMessageCreated = new HashMap<>();
            AppExecutors.getInstance().networkIO().execute(new Runnable() {
                @Override
                public void run() {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        try {
                            api = new DiscordApiBuilder().setToken(token).setAllIntents().login().join();
                        }catch(Exception ex)
                        {
                            ex.printStackTrace();
                            api = null;
                        }
                        AppExecutors.getInstance().mainThread().execute(new Runnable() {
                            @Override
                            public void run() {
                                //Cambiar la interfaz
                                //Ocultar el spinner y desocultar el boton
                                if(api != null) {
                                    mCorrectCallback = (callbackSetCorrectData) context;
                                    mCorrectCallback.setCorrectCallback();
                                }
                                else
                                {
                                    mIncorrectCallback = (callbackSetIncorrectData) context;
                                    mIncorrectCallback.setIncorrectCallback();
                                }
                            }
                        });
                    }
                }

            });
        }


        return api;
    }

    public LiveData<List<Member>> getCurrentUsers(){
        return memberList;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void apagar(){
        if(api != null) api.disconnect();
        api = null;
        mapaMessageCreated = null;
    }
    public HashMap<String, ListenerManager<MessageCreateListener>> getMapaMessageCreated() {
        return mapaMessageCreated;
    }

    public void destruir(String trigger_text){
        if(mapaMessageCreated.get(trigger_text) != null) {
            api.removeListener(mapaMessageCreated.get(trigger_text).getListener());
            mapaMessageCreated.remove(trigger_text);
        }
    }

    public boolean existePredeterminado()
    {
        return (mapaMessageCreated.get("!") != null);
    }

    public boolean isConstruido(String trigger_text){
        return mapaMessageCreated.get(trigger_text) != null;
    }
}