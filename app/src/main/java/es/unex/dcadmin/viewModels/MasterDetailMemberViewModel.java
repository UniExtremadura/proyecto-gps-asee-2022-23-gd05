package es.unex.dcadmin.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import es.unex.dcadmin.users.Member;

public class MasterDetailMemberViewModel extends ViewModel {
    private final MutableLiveData<Member> memberLiveData = new MutableLiveData<>();
    MasterDetailMemberViewModel(){

    }

    public LiveData<Member> getSelected() {
        return memberLiveData;
    }

    public void select(Member member) {
        memberLiveData.setValue(member);
    }
}
