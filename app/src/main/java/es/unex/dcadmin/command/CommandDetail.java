package es.unex.dcadmin.command;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import es.unex.dcadmin.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CommandDetail#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CommandDetail extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static final String ARG_PARAM1 = "param1";
    public static final String ARG_PARAM2 = "param2";
    public static final String ARG_PARAM3 = "param3";
    public static final String ARG_PARAM4 = "param4";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String mParam3;
    private String mParam4;

    private EditText mTitleText;
    private EditText mTriggerText;
    private EditText mActionText;

    private Command command;

    OnCallbackReceivedUpdate mCallback; //Un objeto para que desde el fragment podamos llamar a un método de la activity (para guardar los datos)

    public interface OnCallbackReceivedUpdate { //La interfaz para pasar los datos, los parámetros son los datos
        public void UpdateCommand(Command command); //Esto llama al método Update que se implementa en la Activity
    }

    public CommandDetail() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CommandDetail.
     */
    // TODO: Rename and change types and number of parameters
    public static CommandDetail newInstance(String param1, String param2, String param3, String param4) {
        CommandDetail fragment = new CommandDetail();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putString(ARG_PARAM3, param3);
        args.putString(ARG_PARAM4, param4);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            command = new Command(bundle.getLong(ARG_PARAM2),bundle.getString(ARG_PARAM1), bundle.getString(ARG_PARAM3), bundle.getString(ARG_PARAM4));
        }
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            mParam3 = getArguments().getString(ARG_PARAM3);
            mParam4 = getArguments().getString(ARG_PARAM4);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.command_detail, container, false);
        mTitleText = (EditText) v.findViewById(R.id.command_name);

        mTriggerText = (EditText) v.findViewById(R.id.command_trigger);

        mActionText = (EditText) v.findViewById(R.id.command_action);

        mTitleText.setText(command.getName());

        mTriggerText.setText(command.getTrigger_text());

        mActionText.setText(command.getAction_text());

        try {
            mCallback = (OnCallbackReceivedUpdate) getActivity(); //Se inicializa el callback
        } catch (ClassCastException e) {

        }

        ImageView imageView = v.findViewById(R.id.saveView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = mTitleText.getText().toString(); //Coge los datos
                command.setName(mTitleText.getText().toString());
                command.setAction_text(mActionText.getText().toString());
                command.setTrigger_text(mTriggerText.getText().toString());
                mCallback.UpdateCommand(command); //Los manda a la activity

                getActivity().onBackPressed();//Cierra el fragment

            }
        });

        return v;
    }
}