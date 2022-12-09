package es.unex.dcadmin;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.spy;

import org.javacord.api.DiscordApi;
import org.javacord.api.listener.message.MessageCreateListener;
import org.javacord.api.util.event.ListenerManager;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.lang.reflect.Field;
import java.util.HashMap;

import es.unex.dcadmin.command.Command;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

public class CU02 {

    @Mock
    DiscordApi api;

    @Mock
    HashMap<String, ListenerManager<MessageCreateListener>> mapMessageCreated;

    @Test
    public void commandConstructors_areCorrect() {
        Command c1 = new Command("testName", "testTrigger_text", "testAction_text");

        /*Intent intent = new Intent();
        intent.putExtra("name", "intentName");
        Command c2 = new Command(intent);*/

        Command c3 = new Command(1, "testName", "testTrigger_text", "testAction_text");

        assertEquals(c1.getName(),"testName");
        assertEquals(c1.getTrigger_text(),"testTrigger_text");
        assertEquals(c1.getAction_text(),"testAction_text");

        //assertEquals(c2.getName(),"intentName");

        assertEquals(c3.getId(),1);
        assertEquals(c3.getName(),"testName");
        assertEquals(c3.getTrigger_text(),"testTrigger_text");
        assertEquals(c3.getAction_text(),"testAction_text");
    }

    @Test
    public void commandSetIdMethodIsCorrect() throws IllegalAccessException, NoSuchFieldException {
        Command instance = new Command(0,null,null,null,null,null);
        instance.setId(1);
        final Field field = instance.getClass().getDeclaredField("id");
        field.setAccessible(true);
        assertEquals("Fields didn't match", field.get(instance), 1L);
    }

    @Test
    public void commandGetIdMethodIsCorrect() throws NoSuchFieldException, IllegalAccessException {
        Command instance = new Command(0,null,null,null,null,null);
        final Field field = instance.getClass().getDeclaredField("id");
        field.setAccessible(true);
        field.set(instance, 0);

        final Long result = instance.getId();

        assertEquals("field wasn't retrieved properly", result, new Long(0));
    }

    @Test
    public void commandSetNameMethodIsCorrect() throws NoSuchFieldException, IllegalAccessException {
        Command instance = new Command(0,null,null,null,null,null);
        instance.setName("testName");
        final Field field = instance.getClass().getDeclaredField("name");
        field.setAccessible(true);
        assertEquals("Fields didn't match", field.get(instance), "testName");
    }

    @Test
    public void commandGetNameMethodIsCorrect() throws NoSuchFieldException, IllegalAccessException {
        Command instance = new Command(0,null,null,null,null,null);
        final Field field = instance.getClass().getDeclaredField("name");
        field.setAccessible(true);
        field.set(instance, "testName");

        final String result = instance.getName();

        assertEquals("field wasn't retrieved properly", result, "testName");
    }

    @Test
    public void commandSetTriggerTextMethodIsCorrect() throws NoSuchFieldException, IllegalAccessException {
        Command instance = new Command(0,null,null,null,null,null);
        instance.setTrigger_text("testTrigger");
        final Field field = instance.getClass().getDeclaredField("trigger_text");
        field.setAccessible(true);
        assertEquals("Fields didn't match", field.get(instance), "testTrigger");
    }

    @Test
    public void commandGetTriggerTextMethodIsCorrect() throws NoSuchFieldException, IllegalAccessException {
        Command instance = new Command(0,null,null,null,null,null);
        final Field field = instance.getClass().getDeclaredField("trigger_text");
        field.setAccessible(true);
        field.set(instance, "testTrigger");

        final String result = instance.getTrigger_text();

        assertEquals("field wasn't retrieved properly", result, "testTrigger");
    }

    @Test
    public void commandSetActionTextMethodIsCorrect() throws NoSuchFieldException, IllegalAccessException {
        Command instance = new Command(0,null,null,null,null,null);
        instance.setAction_text("testAction");
        final Field field = instance.getClass().getDeclaredField("action_text");
        field.setAccessible(true);
        assertEquals("Fields didn't match", field.get(instance), "testAction");
    }

    @Test
    public void commandGetActionTextMethodIsCorrect() throws NoSuchFieldException, IllegalAccessException {
        Command instance = new Command(0,null,null,null,null,null);
        final Field field = instance.getClass().getDeclaredField("action_text");
        field.setAccessible(true);
        field.set(instance, "testAction");

        final String result = instance.getAction_text();

        assertEquals("field wasn't retrieved properly", result, "testAction");
    }

    @Test
    public void commandSetDiscordAPIMethodIsCorrect() throws NoSuchFieldException, IllegalAccessException {
        Command instance = new Command(0,null,null,null,null,null);
        instance.setDiscordApi(api);
        final Field field = instance.getClass().getDeclaredField("discordApi");
        field.setAccessible(true);
        assertEquals("Fields didn't match", field.get(instance), api);
    }

    @Test
    public void commandGetDiscordAPIMethodIsCorrect() throws IllegalAccessException, NoSuchFieldException {
        Command instance = new Command(0,null,null,null,null,null);
        final Field field = instance.getClass().getDeclaredField("discordApi");
        field.setAccessible(true);
        field.set(instance, api);

        final DiscordApi result = instance.getDiscordApi();

        assertEquals("field wasn't retrieved properly", result, api);
    }

    @Test
    public void commandSetMapMethodIsCorrect() throws NoSuchFieldException, IllegalAccessException {
        Command instance = new Command(0,null,null,null,null,null);
        instance.setMapMessageCreated(mapMessageCreated);
        final Field field = instance.getClass().getDeclaredField("mapMessageCreated");
        field.setAccessible(true);
        assertEquals("Fields didn't match", field.get(instance), mapMessageCreated);
    }

    @Test
    public void commandGetMapMethodIsCorrect() throws NoSuchFieldException, IllegalAccessException {
        Command instance = new Command(0,null,null,null,null,null);
        final Field field = instance.getClass().getDeclaredField("mapMessageCreated");
        field.setAccessible(true);
        field.set(instance, api);

        final HashMap<String, ListenerManager<MessageCreateListener>> result = instance.getMapMessageCreated();

        assertEquals("field wasn't retrieved properly", result, mapMessageCreated);
    }

    @Test
    public void commandIsConstruidoMethodIsCorrect(){
        Command instance = new Command(0,null,null,null,null,null);
        instance.setDiscordApi(null);
        assertEquals(false, instance.isConstruido());

        DiscordApi dcApi = Mockito.spy(DiscordApi.class);

        instance.setDiscordApi(dcApi);
        assertEquals(true,instance.isConstruido());
    }
}