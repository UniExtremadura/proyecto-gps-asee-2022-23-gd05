package es.unex.dcadmin.discord;

import android.os.Build;
import android.view.View;

import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

import java.util.ArrayList;
import java.util.List;

import es.unex.dcadmin.AppExecutors;
import es.unex.dcadmin.MainActivity;
import es.unex.dcadmin.users.Member;

public class discordApiManager {
    private static DiscordApi api = null;
    private static String token = "";

    public static DiscordApi getSingleton() {
        if (api == null) {
            AppExecutors.getInstance().networkIO().execute(new Runnable() {
                @Override
                public void run() {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        try {
                            api = new DiscordApiBuilder().setToken(token).login().join();
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
                                    MainActivity.mensaje.setVisibility(View.VISIBLE);
                                    MainActivity.mensaje.setText("Pulsa en cualquier lugar para continuar");
                                    MainActivity.progressBar.setVisibility(View.INVISIBLE);
                                    MainActivity.layout.setOnClickListener(MainActivity.listener);
                                }
                                else{
                                    MainActivity.mensaje.setVisibility(View.VISIBLE);
                                    MainActivity.mensaje.setText("No se ha podido iniciar sesión en Discord. ¿El token es correcto?");
                                    MainActivity.progressBar.setVisibility(View.INVISIBLE);
                                    MainActivity.command_b.setClickable(true);
                                    MainActivity.addTokenView.setClickable(true);
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
}