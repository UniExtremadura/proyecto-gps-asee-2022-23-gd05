package es.unex.dcadmin;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;

import es.unex.dcadmin.users.Member;

public class CU13 {

    @Test
    public void memberConstructor_isCorrect() throws MalformedURLException {
        Member member = new Member(333333,"memberTest1", new URL("https://cdn.discordapp.com/avatars/239811274815438849/e22f01db62fc33500404209a5f50ecf4.png?size=4096"),"testServer1");
    }

    @Test
    public void getIdIsCorrect() throws MalformedURLException, NoSuchFieldException, IllegalAccessException {
        Member member = new Member(333333,"memberTest1", new URL("https://cdn.discordapp.com/avatars/239811274815438849/e22f01db62fc33500404209a5f50ecf4.png?size=4096"),"testServer1");
        final Field field = member.getClass().getDeclaredField("id");
        field.setAccessible(true);
        field.set(member,333333L);

        final Long result = member.getId();
        assertEquals("Field wasn't retrieve properly",result,new Long(333333));
    }

    @Test
    public void setIdIsCorrect() throws MalformedURLException, NoSuchFieldException, IllegalAccessException {
        Member member = new Member(-1,"memberTest1", new URL("https://cdn.discordapp.com/avatars/239811274815438849/e22f01db62fc33500404209a5f50ecf4.png?size=4096"),"testServer1");
        member.setId(333333L);
        final Field field = member.getClass().getDeclaredField("id");
        field.setAccessible(true);

        assertEquals("Fields didn't match", field.get(member), 333333L);
    }

    @Test
    public void getNameIsCorrect() throws MalformedURLException, NoSuchFieldException, IllegalAccessException {
        Member member = new Member(333333,"memberTest1", new URL("https://cdn.discordapp.com/avatars/239811274815438849/e22f01db62fc33500404209a5f50ecf4.png?size=4096"),"testServer1");
        member.setName("memberTest1");
        final Field field = member.getClass().getDeclaredField("name");
        field.setAccessible(true);
        field.set(member,"memberTest1");

        final String result = member.getName();
        assertEquals("Field wasn't retrieve properly",result,"memberTest1");
    }

    @Test
    public void setNameIsCorrect() throws MalformedURLException, NoSuchFieldException, IllegalAccessException {
        Member member = new Member(333333,"memberTest1", new URL("https://cdn.discordapp.com/avatars/239811274815438849/e22f01db62fc33500404209a5f50ecf4.png?size=4096"),"testServer1");
        member.setName("memberTest1");
        final Field field = member.getClass().getDeclaredField("name");
        field.setAccessible(true);

        assertEquals("Fields didn't match", field.get(member), "memberTest1");
    }

    @Test
    public void getAvatarIsCorrect() throws MalformedURLException, NoSuchFieldException, IllegalAccessException {
        Member member = new Member(333333,"memberTest1", new URL("https://cdn.discordapp.com/avatars/239811274815438849/e22f01db62fc33500404209a5f50ecf4.png?size=4096"),"testServer1");
        final Field field = member.getClass().getDeclaredField("avatar");
        field.setAccessible(true);
        field.set(member,new URL("https://cdn.discordapp.com/avatars/239811274815438849/e22f01db62fc33500404209a5f50ecf4.png?size=4096"));

        final URL result = member.getAvatar();
        assertEquals("Field wasn't retrieve properly",result,new URL("https://cdn.discordapp.com/avatars/239811274815438849/e22f01db62fc33500404209a5f50ecf4.png?size=4096"));
    }

    @Test
    public void setAvatarIsCorrect() throws MalformedURLException, NoSuchFieldException, IllegalAccessException {
        Member member = new Member(333333,"memberTest1", new URL("https://cdn.discordapp.com/avatars/239811274815438849/e22f01db62fc33500404209a5f50ecf4.png?size=4096"),"testServer1");
        member.setAvatar(new URL("https://cdn.discordapp.com/avatars/239811274815438849/e22f01db62fc33500404209a5f50ecf4.png?size=4096"));
        final Field field = member.getClass().getDeclaredField("avatar");
        field.setAccessible(true);

        assertEquals("Fields didn't match", field.get(member), new URL("https://cdn.discordapp.com/avatars/239811274815438849/e22f01db62fc33500404209a5f50ecf4.png?size=4096"));
    }

    @Test
    public void getServerIsCorrect() throws MalformedURLException, NoSuchFieldException, IllegalAccessException {
        Member member = new Member(333333,"memberTest1", new URL("https://cdn.discordapp.com/avatars/239811274815438849/e22f01db62fc33500404209a5f50ecf4.png?size=4096"),"testServer1");
        final Field field = member.getClass().getDeclaredField("server");
        field.setAccessible(true);
        field.set(member,"testServer1");

        final String result = member.getServer();
        assertEquals("Field wasn't retrieve properly",result,"testServer1");
    }

    @Test
    public void setServerIsCorrect() throws MalformedURLException, NoSuchFieldException, IllegalAccessException {
        Member member = new Member(333333,"memberTest1", new URL("https://cdn.discordapp.com/avatars/239811274815438849/e22f01db62fc33500404209a5f50ecf4.png?size=4096"),"testServer1");
        member.setServer("testServer1");
        final Field field = member.getClass().getDeclaredField("server");
        field.setAccessible(true);

        assertEquals("Fields didn't match", field.get(member), "testServer1");
    }
}
