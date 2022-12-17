package es.unex.dcadmin.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import es.unex.dcadmin.commandRecord.CommandRecord;
import es.unex.dcadmin.repositories.CommandRecordRepository;
import es.unex.dcadmin.repositories.MemberRepository;
import es.unex.dcadmin.users.Member;

public class MemberViewModel extends ViewModel {
    private final MemberRepository mRepository;
    private final LiveData<List<Member>> mMembers;

    public MemberViewModel(MemberRepository repository) {
        mRepository = repository;
        mMembers = mRepository.getCurrentUsers();
    }

    public LiveData<List<Member>> getMembers() {
        return mMembers;
    }
}
