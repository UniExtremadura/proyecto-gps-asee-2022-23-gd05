package es.unex.dcadmin.commandRecord;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import es.unex.dcadmin.AppContainer;
import es.unex.dcadmin.AppExecutors;
import es.unex.dcadmin.DCAdmin;
import es.unex.dcadmin.R;
import es.unex.dcadmin.viewModels.CommandRecordViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CommandRecordList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CommandRecordList extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private CommandRecordAdapter mAdapter;
    private CommandRecordViewModel mViewModel;

    public CommandRecordList() {
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
    public static CommandRecordList newInstance(String param1, String param2) {
        CommandRecordList fragment = new CommandRecordList();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.command_record, container, false);

        mRecyclerView = v.findViewById(R.id.commandRecyclerView);

        AppContainer appContainer = ((DCAdmin) getActivity().getApplication()).appContainer;
        mViewModel = new ViewModelProvider(this, (ViewModelProvider.Factory) appContainer.mFactory).get(CommandRecordViewModel.class);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        View lay = v.findViewById(R.id.content_to_do_manager);
        lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        mAdapter = new CommandRecordAdapter(new CommandRecordAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(CommandRecord item) {

            }
        }, new CommandRecordAdapter.OnDeleteClickListener() {
            @Override
            public void onDeleteClick(CommandRecord item) {

            }
        });

        mRecyclerView.setAdapter(mAdapter);

        ImageView delImage = v.findViewById(R.id.deleteCommandRecord); //Borrar historial
        delImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        mViewModel.deleteCommandRecords();

                        getActivity().runOnUiThread(() -> mAdapter.clear());
                    }
                });
            }
        });

        mViewModel.getCommandRecords().observe(getViewLifecycleOwner(), this::onCommandRecordsLoaded);

        return v;
    }

    private void onCommandRecordsLoaded(List<CommandRecord> commandRecords) {

        getActivity().runOnUiThread(() -> mAdapter.swap(commandRecords));

    }

}