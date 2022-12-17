package es.unex.dcadmin.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import es.unex.dcadmin.command.Command;
import es.unex.dcadmin.repositories.CommandRepository;

public class CommandViewModel extends ViewModel {
    private final CommandRepository mRepository;
    private final LiveData<List<Command>> mCommands;

    public CommandViewModel(CommandRepository repository) {
        mRepository = repository;
        mCommands = mRepository.getCurrentCommands();
    }

    public void deleteCommands(){
        mRepository.deleteAll();
    }

    public int deleteCommand(Command command){
        return mRepository.deleteCommand(command);
    }

    public long insertCommand(Command command){
        return mRepository.insertCommand(command);
    }

    public int updateCommand(Command command){
        return mRepository.updateCommand(command);
    }

    public LiveData<List<Command>> getCommands() {
        return mCommands;
    }
}
