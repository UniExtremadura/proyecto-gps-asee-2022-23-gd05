package es.unex.dcadmin.users;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.net.MalformedURLException;
import java.net.URL;

import es.unex.dcadmin.AppExecutors;
import es.unex.dcadmin.R;


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
        loadItems(); //Para cargar los datos
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.users_list, container, false);

        mRecyclerView = v.findViewById(R.id.commandRecyclerView);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new UsersListAdapter(new UsersListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Member item) {
                UsersDetail fragment = new UsersDetail();

                Bundle bundle = new Bundle();
                bundle.putString(UsersDetail.ARG_PARAM1, item.getName());
                bundle.putString(UsersDetail.ARG_PARAM2, item.getServer());
                bundle.putLong(UsersDetail.ARG_PARAM3, item.getId());
                bundle.putString(UsersDetail.ARG_PARAM4, item.getAvatar().toString());

                fragment.setArguments(bundle);

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_to_do_manager, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        mRecyclerView.setAdapter(mAdapter);

        AppExecutors.getInstance().networkIO().execute(new Runnable() {
            @Override
            public void run() {

            }
        });

        try {
            URL _url = new URL("https://cdn.discordapp.com/avatars/239811274815438849/e22f01db62fc33500404209a5f50ecf4.png");
            mAdapter.add(new Member(123456789, "UserTest1", _url, "ServerTest1"));
            mAdapter.add(new Member(987654321, "UserTest2", _url, "ServerTest2"));
            mAdapter.add(new Member(192837465, "UserTest3", _url, "ServerTest3"));
            mAdapter.add(new Member(918273645, "UserTest4", _url, "ServerTest4"));
            mAdapter.add(new Member(111222333, "UserTest5", _url, "ServerTest5"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


        return v;
    }

    private void loadItems() {

    }
}