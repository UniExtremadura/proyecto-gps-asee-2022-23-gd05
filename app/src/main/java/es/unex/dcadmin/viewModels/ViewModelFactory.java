package es.unex.dcadmin.viewModels;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import es.unex.dcadmin.repositories.CommandRecordRepository;
import es.unex.dcadmin.repositories.CommandRepository;
import es.unex.dcadmin.repositories.MemberRepository;

public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory{

    MemberRepository mMemberRepository;
    CommandRepository mCommandRepository;
    CommandRecordRepository mCommandRecordRepository;
    MasterDetailCommandViewModel masterDetailCommandViewModel;
    MasterDetailMemberViewModel masterDetailMemberViewModel;

    public ViewModelFactory(MemberRepository memberRepository, CommandRepository commandRepository, CommandRecordRepository commandRecordRepository) {
        this.mMemberRepository = memberRepository;
        this.mCommandRepository = commandRepository;
        this.mCommandRecordRepository = commandRecordRepository;

        this.masterDetailCommandViewModel = new MasterDetailCommandViewModel();
        this.masterDetailMemberViewModel = new MasterDetailMemberViewModel();
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        //noinspection unchecked
        if(CommandViewModel.class.isAssignableFrom(modelClass)){
            return (T) new CommandViewModel(mCommandRepository);
        }
        else if(CommandRecordViewModel.class.isAssignableFrom(modelClass)){
            return (T) new CommandRecordViewModel(mCommandRecordRepository);
        }
        else if(MemberViewModel.class.isAssignableFrom(modelClass)){
            return (T) new MemberViewModel(mMemberRepository);
        }
        else if(MasterDetailMemberViewModel.class.isAssignableFrom(modelClass)){
            return (T) masterDetailMemberViewModel;
        }
        else if(MasterDetailCommandViewModel.class.isAssignableFrom(modelClass)){
            return (T) masterDetailCommandViewModel;
        }
        else {
            throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
        }
    }
}
