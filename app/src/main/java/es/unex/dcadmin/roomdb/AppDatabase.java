package es.unex.dcadmin.roomdb;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import es.unex.dcadmin.commandRecord.CommandRecord;
import es.unex.dcadmin.command.Command;
import es.unex.dcadmin.users.Member;

@Database(entities = {Command.class, CommandRecord.class, Member.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instance;

    public static AppDatabase getInstance(Context context){
        if(instance == null)
            instance = Room.databaseBuilder(context, AppDatabase.class,"database.db").build();

        return instance;
    }

    public abstract CommandDao getCommandDao();
    public abstract CommandRecordDao getCommandRecordDao();
    public abstract MemberDao getMemberDao();
}