package es.unex.dcadmin.repositories;

import android.util.Log;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.unex.dcadmin.AppExecutors;
import es.unex.dcadmin.discord.discordApiManager;
import es.unex.dcadmin.roomdb.MemberDao;
import es.unex.dcadmin.users.Member;

public class MemberRepository {
    private static MemberRepository sInstance;
    private final MemberDao mMemberDao;
    private final AppExecutors mExecutors = AppExecutors.getInstance();
    private LiveData<List<Member>> memberLiveData = new MutableLiveData<>();
    private final discordApiManager api;
    private Long lastUpdateTimeMillisLong = null;
    private static final long MIN_TIME_FROM_LAST_FETCH_MILLIS = 30000;


    private MemberRepository(MemberDao memberDao, discordApiManager api) {
        mMemberDao = memberDao;
        this.api = api;
        // LiveData that fetches members from network
        memberLiveData = api.getCurrentUsers();
        // As long as the repository exists, observe the network LiveData.
        // If that LiveData changes, update the database.
        memberLiveData.observeForever(newUsersFromNetwork -> {
            mExecutors.diskIO().execute(() -> {
                // Deleting cached repos of user
                if (newUsersFromNetwork.size() > 0){
                    mMemberDao.deleteAll();
                }
                // Insert our new repos into local database
                mMemberDao.bulkInsert(newUsersFromNetwork);
            });
        });
    }

    public synchronized static MemberRepository getInstance(MemberDao dao, discordApiManager api) {
        if (sInstance == null) {
            sInstance = new MemberRepository(dao, api);
        }
        return sInstance;
    }

    public void doFetchMembers(){
        AppExecutors.getInstance().diskIO().execute(() -> {
            mMemberDao.deleteAll();
            api.getUsers();
            lastUpdateTimeMillisLong = System.currentTimeMillis();
        });
    }

    public LiveData<List<Member>> getCurrentUsers() {
        mExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                if (isFetchNeeded()) {
                    doFetchMembers();
                }
            }
        });

        return memberLiveData;
        //return mMemberDao.getAll();
    }

    private boolean isFetchNeeded() {
        Long lastFetchTimeMillis = lastUpdateTimeMillisLong;
        lastFetchTimeMillis = lastFetchTimeMillis == null ? 0L : lastFetchTimeMillis;
        long timeFromLastFetch = System.currentTimeMillis() - lastFetchTimeMillis;
        return mMemberDao.getNumberOfMembers() == 0 || timeFromLastFetch > MIN_TIME_FROM_LAST_FETCH_MILLIS;
    }
}
