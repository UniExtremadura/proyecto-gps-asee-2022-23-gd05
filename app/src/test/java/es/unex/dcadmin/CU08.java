package es.unex.dcadmin;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.Icon;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.listener.message.MessageCreateListener;
import org.javacord.api.util.event.ListenerManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import es.unex.dcadmin.command.Command;
import es.unex.dcadmin.discord.discordApiManager;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

public class CU08 {

    @Mock
    DiscordApi api;
    @Mock
    HashMap<String, ListenerManager<MessageCreateListener>> mapaMessageCreated;
    @Mock
    Context context;

    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    @Test
    public void getUsersIsCorrect() throws MalformedURLException {
        /**
        assertNotEquals(discordApiManager.getUsers().size(),0); //Al menos se debe encontrar a sí mismo

        Server s = Mockito.spy(Server.class);
        User u = Mockito.spy(User.class);

        List<User> ulist = new ArrayList<>();
        ulist.add(u);
        List<Server> slist = new ArrayList<>();
        slist.add(s);

        DiscordApi testApi = Mockito.mock(DiscordApi.class);
        discordApiManager.setApi(testApi);

        when(testApi.getServers()).thenReturn(slist);
        when(s.getMembers()).thenReturn(ulist);
        when(u.getId()).thenReturn(1L);
        when(u.getName()).thenReturn("test");
        URL url = new URL("http://");
        Icon avatar = Mockito.spy(Icon.class);
        when(u.getAvatar()).thenReturn(avatar);
        when(avatar.getUrl()).thenReturn(url);
        when(s.getName()).thenReturn("testServer");

        assertEquals(discordApiManager.getUsers().size(),1);
        assertEquals(discordApiManager.getUsers().get(0).getName(),"test");
        assertEquals(discordApiManager.getUsers().get(0).getId(),1L);
        assertEquals(discordApiManager.getUsers().get(0).getAvatar(),url);
        assertEquals(discordApiManager.getUsers().get(0).getServer(),"testServer");
        **/
    }

    @Test
    public void getDestruirIsCorrect(){
        /**
        discordApiManager.destruir("!");
        assertEquals(api.getListeners().size(),0);

        ListenerManager<MessageCreateListener> listenerManager = api.addMessageCreateListener(event -> {

        });
        discordApiManager.getMapaMessageCreated().put("!", listenerManager);
        api.addListener(discordApiManager.getMapaMessageCreated().get("!").getListener());

        assertEquals(api.getListeners().size(),1);

        discordApiManager.destruir("!");
        assertEquals(api.getListeners().size(),0);
        **/
    }

    @Test
    public void getExistePredeterminadoIsCorrect(){
        /**
        assertFalse(discordApiManager.existePredeterminado());

        ListenerManager<MessageCreateListener> listenerManager = api.addMessageCreateListener(event -> {

        });
        discordApiManager.getMapaMessageCreated().put("!", listenerManager);
        api.addListener(discordApiManager.getMapaMessageCreated().get("!").getListener());
        assertTrue(discordApiManager.existePredeterminado());
        **/
    }

    @Test
    public void getCommandConstruidoIsCorrect(){
        /**
        Command command = new Command(1, "name", "trigger", "action");

        command.construir(api,mapaMessageCreated,context);
        assertEquals(command.getDiscordApi(),api);
        assertEquals(command.getMapMessageCreated(),mapaMessageCreated);
        assertEquals(discordApiManager.getSingleton(),command.getDiscordApi());

        assertEquals(api.getListeners().size(),1);
        **/
    }

    @Test
    public void getTokenIsCorrect() throws NoSuchFieldException, IllegalAccessException {
        /**
        discordApiManager instance = new discordApiManager();
        final Field field = instance.getClass().getDeclaredField("token");
        field.setAccessible(true);
        field.set(instance, "testToken");

        final String result = instance.getToken();

        assertEquals("field wasn't retrieved properly", result, "testToken");
        **/
    }

    @Test
    public void setTokenIsCorrect() throws NoSuchFieldException, IllegalAccessException {
        /**
        discordApiManager instance = new discordApiManager();
        instance.setToken("testToken");
        final Field field = instance.getClass().getDeclaredField("token");
        field.setAccessible(true);
        assertEquals("Fields didn't match", field.get(instance), "testToken");
        **/
    }

    @Test
    public void setApiIsCorrect() throws NoSuchFieldException, IllegalAccessException {
        /**
        discordApiManager instance = new discordApiManager();
        instance.setApi(api);
        final Field field = instance.getClass().getDeclaredField("api");
        field.setAccessible(true);
        assertEquals("Fields didn't match", field.get(instance), api);
        **/
    }

    @Test
    public void setMapaIsCorrect() throws NoSuchFieldException, IllegalAccessException {
        /**
        discordApiManager instance = new discordApiManager();
        instance.setMapaMessageCreated(mapaMessageCreated);
        final Field field = instance.getClass().getDeclaredField("mapaMessageCreated");
        field.setAccessible(true);
        assertEquals("Fields didn't match", field.get(instance), mapaMessageCreated);
        **/
    }

    @Before
    public void setup() throws ExecutionException, InterruptedException {
        /**
        MockitoAnnotations.openMocks(this);
        mapaMessageCreated = new HashMap<>();
        api = new DiscordApiBuilder().setToken("<TOKEN_AQUI>").setAllIntents().login().join();
        Thread.sleep(7000); //Para que le de tiempo a cargar la API
        discordApiManager.setApi(api);
        discordApiManager.setMapaMessageCreated(mapaMessageCreated);
        **/
    }

    @After
    public void end(){
        /**
        api.disconnect();
        discordApiManager.apagar();
        **/
    }

}
