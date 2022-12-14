package es.unex.dcadmin.discord;

import android.content.Intent;
import android.os.Build;
import android.view.View;

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
import es.unex.dcadmin.users.Member;

public class discordApiManager {
    private static DiscordApi api = null;
    private static String token = "";
    private static HashMap<String, ListenerManager<MessageCreateListener>> mapaMessageCreated = new HashMap<>();


    public static DiscordApi getSingleton() {
        if (api == null) {
            mapaMessageCreated = new HashMap<>();
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
                                    MainActivity.mensaje.setText("Pulsa en cualquier lugar para continuar");
                                    MainActivity.mensaje.setVisibility(View.VISIBLE);
                                    MainActivity.getProgressBar().setVisibility(View.INVISIBLE);
                                    MainActivity.layout.setOnClickListener(MainActivity.listener);
                                }
                                else
                                {
                                    MainActivity.mensaje.setVisibility(View.VISIBLE);
                                    MainActivity.mensaje.setText("No se ha podido iniciar sesion el Discord. ??El token es correcto?");
                                    MainActivity.getProgressBar().setVisibility(View.INVISIBLE);
                                    MainActivity.access.setClickable(true);
                                    MainActivity.tokenEditText.setClickable(true);
                                }
                            }
                        });
                    }
                }

            });
        }
        return api;
    }

    public static List<Member> getUsers() {
        List<Server> servers = new ArrayList<>(getSingleton().getServers());
        List<Member> members = new ArrayList<>();

        for(Server s: servers) {
            for(User u: s.getMembers()) {
                Member m = new Member(u.getId(), u.getName(), u.getAvatar().getUrl(), s.getName());
                members.add(m);
            }
        }
        return members;
    }

    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        discordApiManager.token = token;
    }

    public static void apagar(){
        if(api != null) api.disconnect();
        api = null;
        mapaMessageCreated = null;
    }
    public static HashMap<String, ListenerManager<MessageCreateListener>> getMapaMessageCreated() {
        return mapaMessageCreated;
    }

    public static void destruir(String trigger_text){
        if(mapaMessageCreated.get(trigger_text) != null) {
            api.removeListener(mapaMessageCreated.get(trigger_text).getListener());
            mapaMessageCreated.remove(trigger_text);
        }
    }

    public static boolean existePredeterminado()
    {
        return (mapaMessageCreated.get("!") != null);
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////
    //
    public static void setApi(DiscordApi api)
    {
       discordApiManager.api = api;
    }

    public static void setMapaMessageCreated(HashMap<String, ListenerManager<MessageCreateListener>> mapaMessageCreated)
    {
        discordApiManager.mapaMessageCreated = mapaMessageCreated;
    }
    //
    // /////////////////////////////////////////////////////////////////////////////////////////
}