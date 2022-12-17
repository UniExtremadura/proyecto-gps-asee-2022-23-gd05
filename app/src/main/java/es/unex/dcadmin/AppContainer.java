package es.unex.dcadmin;

import android.content.Context;

import es.unex.dcadmin.discord.discordApiManager;
import es.unex.dcadmin.repositories.CommandRecordRepository;
import es.unex.dcadmin.repositories.CommandRepository;
import es.unex.dcadmin.repositories.MemberRepository;
import es.unex.dcadmin.roomdb.AppDatabase;
import es.unex.dcadmin.viewModels.ViewModelFactory;

public class AppContainer {

    private AppDatabase database;
    private discordApiManager networkDataSource;
    public CommandRepository commandRepository;
    public CommandRecordRepository commandRecordRepository;
    public MemberRepository memberRepository;
    public ViewModelFactory mFactory;

    public AppContainer(Context context){
        database = AppDatabase.getInstance(context);
        networkDataSource = discordApiManager.getSingleton();
        commandRepository = commandRepository.getInstance(database.getCommandDao());
        commandRecordRepository = commandRecordRepository.getInstance(database.getCommandRecordDao());
        memberRepository = memberRepository.getInstance(database.getMemberDao(),networkDataSource);
        mFactory = new ViewModelFactory(memberRepository,commandRepository,commandRecordRepository);
    }
}

