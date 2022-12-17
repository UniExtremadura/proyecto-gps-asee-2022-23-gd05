package es.unex.dcadmin.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import es.unex.dcadmin.AppExecutors;
import es.unex.dcadmin.command.Command;
import es.unex.dcadmin.discord.discordApiManager;
import es.unex.dcadmin.roomdb.CommandDao;
import es.unex.dcadmin.roomdb.MemberDao;
import es.unex.dcadmin.users.Member;

public class CommandRepository {
    private static CommandRepository sInstance;
    private final CommandDao mCommandDao;
    private final AppExecutors mExecutors = AppExecutors.getInstance();
    private LiveData <List<Command>> commandLiveData = new MutableLiveData<>();
    private Long lastUpdateTimeMillisLong = null;
    private final long MIN_TIME_FROM_LAST_FETCH_MILLIS = 30000;

    private CommandRepository(CommandDao commandDao) {
        mCommandDao = commandDao;
        // LiveData that fetches members from network
        commandLiveData = mCommandDao.getAll();
    }

    public synchronized static CommandRepository getInstance(CommandDao dao) {
        if (sInstance == null) {
            sInstance = new CommandRepository(dao);
        }
        return sInstance;
    }

    public void doFetchCommands(){
        AppExecutors.getInstance().diskIO().execute(() -> {
            mCommandDao.getAll();
            lastUpdateTimeMillisLong = System.currentTimeMillis();
        });
    }

    public LiveData<List<Command>> getCurrentCommands() {
        mExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                if (isFetchNeeded()) {
                    doFetchCommands();
                }
            }
        });

        return commandLiveData;
        //return mCommandDao.getAll();
    }

    public long insertCommand(Command command){
        final long[] id = new long[1];
        mExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                id[0] = mCommandDao.insert(command);
            }
        });
        return id[0];
    }

    public int deleteCommand(Command command){
        final int[] num = new int[1];
        mExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                num[0] = mCommandDao.delete(command);
            }
        });
        return num[0];
    }

    public int updateCommand(Command command){
        final int[] num = new int[1];
        mExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                num[0] = mCommandDao.update(command);
            }
        });
        return num[0];
    }

    public void deleteAll(){
        mExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mCommandDao.deleteAll();
            }
        });
    }

    private boolean isFetchNeeded() {
        Long lastFetchTimeMillis = lastUpdateTimeMillisLong;
        lastFetchTimeMillis = lastFetchTimeMillis == null ? 0L : lastFetchTimeMillis;
        long timeFromLastFetch = System.currentTimeMillis() - lastFetchTimeMillis;
        return timeFromLastFetch > MIN_TIME_FROM_LAST_FETCH_MILLIS;
    }
}
