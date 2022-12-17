package es.unex.dcadmin.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import es.unex.dcadmin.AppExecutors;
import es.unex.dcadmin.command.Command;
import es.unex.dcadmin.commandRecord.CommandRecord;
import es.unex.dcadmin.discord.discordApiManager;
import es.unex.dcadmin.roomdb.CommandRecordDao;

public class CommandRecordRepository {
    private static CommandRecordRepository sInstance;
    private final CommandRecordDao mCommandRecordDao;
    private final AppExecutors mExecutors = AppExecutors.getInstance();
    private LiveData<List<CommandRecord>> commandLiveData = new MutableLiveData<>();
    private Long lastUpdateTimeMillisLong = null;
    private final long MIN_TIME_FROM_LAST_FETCH_MILLIS = 30000;

    private CommandRecordRepository(CommandRecordDao commandRecordDao) {
        mCommandRecordDao = commandRecordDao;
        // LiveData that fetches members from network
        commandLiveData = mCommandRecordDao.getAll();
    }

    public synchronized static CommandRecordRepository getInstance(CommandRecordDao dao) {
        if (sInstance == null) {
            sInstance = new CommandRecordRepository(dao);
        }
        return sInstance;
    }

    public void doFetchCommandRecords(){
        AppExecutors.getInstance().diskIO().execute(() -> {
            mCommandRecordDao.getAll();
            lastUpdateTimeMillisLong = System.currentTimeMillis();
        });
    }

    public LiveData<List<CommandRecord>> getCurrentCommandRecords() {
        mExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                if (isFetchNeeded()) {
                    doFetchCommandRecords();
                }
            }
        });

        return commandLiveData;
        //return mCommandRecordDao.getAll();
    }

    public long insertCommandRecord(CommandRecord commandRecord){
        final long[] id = new long[1];
        mExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                id[0] = mCommandRecordDao.insert(commandRecord);
            }
        });
        return id[0];
    }

    public int updateCommandRecord(CommandRecord commandRecord){
        final int[] num = new int[1];
        mExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                num[0] = mCommandRecordDao.update(commandRecord);
            }
        });
        return num[0];
    }

    public void deleteAll(){
        mExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mCommandRecordDao.deleteAll();
            }
        });
    }

    public CommandRecord getCommandRecord(String name, String userId){
        return mCommandRecordDao.get(name,userId);
    }

    private boolean isFetchNeeded() {
        Long lastFetchTimeMillis = lastUpdateTimeMillisLong;
        lastFetchTimeMillis = lastFetchTimeMillis == null ? 0L : lastFetchTimeMillis;
        long timeFromLastFetch = System.currentTimeMillis() - lastFetchTimeMillis;
        return timeFromLastFetch > MIN_TIME_FROM_LAST_FETCH_MILLIS;
    }
}
