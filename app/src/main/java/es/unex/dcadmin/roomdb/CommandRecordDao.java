package es.unex.dcadmin.roomdb;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import es.unex.dcadmin.commandRecord.CommandRecord;

@Dao
public interface CommandRecordDao {
    @Query("SELECT * FROM commandRecord")
    public LiveData<List<CommandRecord>> getAll();

    @Query("SELECT * FROM commandRecord WHERE name = :commandName and userId = :commandUserId")
    public CommandRecord get(String commandName, String commandUserId);

    @Insert
    public long insert(CommandRecord item);

    @Query("DELETE FROM commandRecord")
    public void deleteAll();

    @Update
    public int update(CommandRecord item);

    @Delete
    public int delete(CommandRecord item);
}
