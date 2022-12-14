package es.unex.dcadmin.roomdb;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import es.unex.dcadmin.command.Command;

@Dao
public interface CommandDao {
    @Query("SELECT * FROM command")
    public List<Command> getAll();

    @Query("SELECT count(*) FROM command WHERE trigger_text = :trigger")
    public int getByTrigger(String trigger);

    @Query("SELECT id FROM command WHERE trigger_text = :trigger")
    public int getIdByTrigger(String trigger);

    @Insert
    public long insert(Command item);

    @Query("DELETE FROM command")
    public void deleteAll();

    @Update
    public int update(Command item);

    @Delete
    public int delete(Command item);
}
