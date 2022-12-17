package es.unex.dcadmin.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import es.unex.dcadmin.command.Command;

public class MasterDetailCommandViewModel extends ViewModel {
    private final MutableLiveData<Command> commandLiveData = new MutableLiveData<>();
    MasterDetailCommandViewModel(){

    }

    public LiveData<Command> getSelected() {
        return commandLiveData;
    }

    public void select(Command command) {
        commandLiveData.setValue(command);
    }
}

