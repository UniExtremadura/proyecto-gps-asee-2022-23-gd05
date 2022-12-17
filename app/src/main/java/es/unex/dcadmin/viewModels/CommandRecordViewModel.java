package es.unex.dcadmin.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import es.unex.dcadmin.commandRecord.CommandRecord;
import es.unex.dcadmin.repositories.CommandRecordRepository;

public class CommandRecordViewModel extends ViewModel {
    private final CommandRecordRepository mRepository;
    private final LiveData<List<CommandRecord>> mCommandRecords;

    public CommandRecordViewModel(CommandRecordRepository repository) {
        mRepository = repository;
        mCommandRecords = mRepository.getCurrentCommandRecords();
    }

    public void deleteCommandRecords(){
        mRepository.deleteAll();
    }

    public long insertCommand(CommandRecord command){
        return mRepository.insertCommandRecord(command);
    }

    public int updateCommandRecord(CommandRecord commandRecord){
        return mRepository.updateCommandRecord(commandRecord);
    }

    public LiveData<List<CommandRecord>> getCommandRecords() {
        return mCommandRecords;
    }
}
