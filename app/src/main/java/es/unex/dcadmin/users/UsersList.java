package es.unex.dcadmin.users;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import es.unex.dcadmin.AppContainer;
import es.unex.dcadmin.DCAdmin;
import es.unex.dcadmin.R;
import es.unex.dcadmin.viewModels.MasterDetailCommandViewModel;
import es.unex.dcadmin.viewModels.MasterDetailMemberViewModel;
import es.unex.dcadmin.viewModels.MemberViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UsersList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UsersList extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private UsersListAdapter mAdapter;
    private MemberViewModel mViewModel;
    private MasterDetailMemberViewModel sharedViewModel;

    public UsersList() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CommandRecordList.
     */
    public static UsersList newInstance(String param1, String param2) {
        UsersList fragment = new UsersList();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        AppContainer appContainer = ((DCAdmin) getActivity().getApplication()).appContainer;
        sharedViewModel = new ViewModelProvider(this, (ViewModelProvider.Factory) appContainer.mFactory).get(MasterDetailMemberViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.users_list, container, false);

        mRecyclerView = v.findViewById(R.id.commandRecyclerView);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new UsersListAdapter(new UsersListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Member item) {
                UsersDetail fragment = new UsersDetail();

                /*Bundle bundle = new Bundle();
                bundle.putString(UsersDetail.ARG_PARAM1, item.getName());
                bundle.putString(UsersDetail.ARG_PARAM2, item.getServer());
                bundle.putLong(UsersDetail.ARG_PARAM3, item.getId());
                bundle.putString(UsersDetail.ARG_PARAM4, item.getAvatar().toString());

                fragment.setArguments(bundle);*/

                sharedViewModel.select(item);

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_to_do_manager, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        mRecyclerView.setAdapter(mAdapter);

        AppContainer appContainer = ((DCAdmin) getActivity().getApplication()).appContainer;
        mViewModel = new ViewModelProvider(this, (ViewModelProvider.Factory) appContainer.mFactory).get(MemberViewModel.class);
        mViewModel.getMembers().observe(getViewLifecycleOwner(), this::onUsersLoaded);

        return v;
    }

    private void onUsersLoaded(List<Member> members) {

        getActivity().runOnUiThread(() -> mAdapter.swap(members));

    }
}