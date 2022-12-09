package es.unex.dcadmin;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

import java.lang.reflect.Field;

import es.unex.dcadmin.command.Command;
import es.unex.dcadmin.commandRecord.CommandRecord;

public class CU09 {

    @Test
    public void commandRecordConstructors_IsCorrect(){
        CommandRecord commandRecord = new CommandRecord("name", 1, "userTest");

        assertFalse(commandRecord.getName() == null);
        assertFalse(commandRecord.getNumExecutions() == 0);
        assertFalse(commandRecord.getUserId() == null);
        assertEquals(commandRecord.getName(),"name");
        assertEquals(commandRecord.getNumExecutions(),1);
        assertEquals(commandRecord.getUserId(),"userTest");

        CommandRecord commandRecord2 = new CommandRecord(192, "testname", 2, "userTest2");
        assertFalse(commandRecord2.getId() == 0);
        assertFalse(commandRecord2.getName() == null);
        assertFalse(commandRecord2.getName() == "name");

        assertFalse(commandRecord2.getNumExecutions() == 1);

        assertFalse(commandRecord2.getUserId() == null);
        assertFalse(commandRecord2.getUserId() == "userTest");

        assertEquals(commandRecord2.getId(), 192);
        assertEquals(commandRecord2.getName(), "testname");
        assertEquals(commandRecord2.getNumExecutions(), 2);
        assertEquals(commandRecord2.getUserId(), "userTest2");

        assertFalse(commandRecord == commandRecord2);

    }

    @Test
    public void commandRecordSetId_IsCorrect() throws NoSuchFieldException, IllegalAccessException {
        CommandRecord instance = new CommandRecord(0,null,0,null);
        instance.setId(1);
        final Field field = instance.getClass().getDeclaredField("id");
        field.setAccessible(true);
        assertEquals("Fields didn't match", field.get(instance), 1L);
    }

    @Test
    public void commandRecordGetId_IsCorrect() throws NoSuchFieldException, IllegalAccessException {
        CommandRecord instance = new CommandRecord(0,null,0,null);
        final Field field = instance.getClass().getDeclaredField("id");
        field.setAccessible(true);
        field.set(instance, 0);

        final Long result = instance.getId();

        assertEquals("field wasn't retrieved properly", result, new Long(0));
    }

    @Test
    public void commandRecordSetName_IsCorrect() throws NoSuchFieldException, IllegalAccessException {
        CommandRecord instance = new CommandRecord(0,null,0,null);
        instance.setName("testName");
        final Field field = instance.getClass().getDeclaredField("name");
        field.setAccessible(true);
        assertEquals("Fields didn't match", field.get(instance), "testName");
    }

    @Test
    public void commandRecordGetName_IsCorrect() throws NoSuchFieldException, IllegalAccessException {
        CommandRecord instance = new CommandRecord(0,null,0,null);
        final Field field = instance.getClass().getDeclaredField("name");
        field.setAccessible(true);
        field.set(instance, "testName");

        final String result = instance.getName();

        assertEquals("field wasn't retrieved properly", result, "testName");
    }

    @Test
    public void commandRecordSetNumExecutions_IsCorrect() throws NoSuchFieldException, IllegalAccessException {
        CommandRecord instance = new CommandRecord(0,null,0,null);
        instance.setNumExecutions(15);
        final Field field = instance.getClass().getDeclaredField("numExecutions");
        field.setAccessible(true);
        assertEquals("Fields didn't match", field.get(instance), 15);
    }

    @Test
    public void commandRecordGetNumExecutions_IsCorrect() throws NoSuchFieldException, IllegalAccessException {
        CommandRecord instance = new CommandRecord(0,null,0,null);
        final Field field = instance.getClass().getDeclaredField("numExecutions");
        field.setAccessible(true);
        field.set(instance, 15);

        final Integer result = instance.getNumExecutions();

        assertEquals("field wasn't retrieved properly", result, new Integer(15));
    }

    @Test
    public void commandRecordSetUserId_IsCorrect() throws NoSuchFieldException, IllegalAccessException {
        CommandRecord instance = new CommandRecord(0,null,0,null);
        instance.setUserId("testUserId");
        final Field field = instance.getClass().getDeclaredField("userId");
        field.setAccessible(true);
        assertEquals("Fields didn't match", field.get(instance), "testUserId");
    }

    @Test
    public void commandRecordGetUserId_IsCorrect() throws NoSuchFieldException, IllegalAccessException {
        CommandRecord instance = new CommandRecord(0,null,0,null);
        final Field field = instance.getClass().getDeclaredField("userId");
        field.setAccessible(true);
        field.set(instance, "testUserId");

        final String result = instance.getUserId();

        assertEquals("field wasn't retrieved properly", result, "testUserId");
    }
}
