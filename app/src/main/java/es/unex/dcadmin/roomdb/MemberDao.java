package es.unex.dcadmin.roomdb;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import es.unex.dcadmin.command.Command;
import es.unex.dcadmin.users.Member;

@Dao
public interface MemberDao {
    @Query("SELECT * FROM members")
    public LiveData<List<Member>> getAll();

    @Insert(onConflict = REPLACE)
    void bulkInsert(List<Member> repo);

    @Query("DELETE FROM members")
    public void deleteAll();

    @Query("SELECT COUNT(*) FROM members")
    public Long getNumberOfMembers();
}
