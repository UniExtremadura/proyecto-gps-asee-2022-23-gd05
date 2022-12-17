package es.unex.dcadmin;

import android.content.Context;

import es.unex.dcadmin.discord.discordApiManager;
import es.unex.dcadmin.repositories.CommandRecordRepository;
import es.unex.dcadmin.repositories.CommandRepository;
import es.unex.dcadmin.repositories.MemberRepository;
import es.unex.dcadmin.roomdb.AppDatabase;
import es.unex.dcadmin.viewModels.ViewModelFactory;

/**
 * Provides static methods to inject the various classes needed for the app
 */
public class InjectorUtils {

    public static CommandRepository provideCommandRepository(Context context) {
        AppDatabase database = AppDatabase.getInstance(context.getApplicationContext());
        return CommandRepository.getInstance(database.getCommandDao());
    }

    public static CommandRecordRepository provideCommandRecordRepository(Context context) {
        AppDatabase database = AppDatabase.getInstance(context.getApplicationContext());
        return CommandRecordRepository.getInstance(database.getCommandRecordDao());
    }

    public static MemberRepository provideMemberRepository(Context context) {
        AppDatabase database = AppDatabase.getInstance(context.getApplicationContext());
        discordApiManager api = discordApiManager.getSingleton();
        return MemberRepository.getInstance(database.getMemberDao(),api);
    }

    public static ViewModelFactory provideViewModelFactory(Context context) {
        CommandRepository commandRepository = provideCommandRepository(context.getApplicationContext());
        CommandRecordRepository commandRecordRepository = provideCommandRecordRepository(context.getApplicationContext());
        MemberRepository memberRepository = provideMemberRepository(context.getApplicationContext());
        return new ViewModelFactory(memberRepository,commandRepository,commandRecordRepository);
    }

}