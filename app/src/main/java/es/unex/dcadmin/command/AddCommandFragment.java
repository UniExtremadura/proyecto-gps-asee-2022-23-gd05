package es.unex.dcadmin.command;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import es.unex.dcadmin.AppExecutors;
import es.unex.dcadmin.R;
import es.unex.dcadmin.roomdb.AppDatabase;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddCommandFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddCommandFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private EditText mTitleText;
    private EditText mTriggerText;
    private EditText mActionText;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    OnCallbackReceivedAdd mCallback; //Un objeto para que desde el fragment podamos llamar a un método de la activity (para guardar los datos)

    public interface OnCallbackReceivedAdd { //La interfaz para pasar los datos, los parámetros son los datos
        public void AddCommand(String name, String trigger, String action); //Esto llama al método AddCommand que se implementa en la Activity
    }

    public AddCommandFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddCommandFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddCommandFragment newInstance(String param1, String param2) {
        AddCommandFragment fragment = new AddCommandFragment();
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
    public void onPrepareOptionsMenu(Menu menu) {

        MenuItem item=(MenuItem) menu;
        if(item!=null)
            item.setVisible(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.activity_add_command, container, false);
        mTitleText = (EditText) v.findViewById(R.id.command_name);
        mActionText = (EditText) v.findViewById(R.id.command_action);
        mTriggerText = (EditText) v.findViewById(R.id.command_trigger);


        // OnClickListener for the Submit Button
        // Implement onClick().

        try {
            mCallback = (OnCallbackReceivedAdd) getActivity(); //Se inicializa el callback
        } catch (ClassCastException e) {

        }

        FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.add_command);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Hay que implementar la funcionalidad del formulario, en este caso, este es el boton de confirmar
                // Gather ToDoItem data
                //Obtendremos los datos del formularios para enviarselos a la activity anterior

                //TODO -  Title
                String name = mTitleText.getText().toString(); //Coge los datos
                String trigger = mTriggerText.getText().toString();
                String action = mActionText.getText().toString();

                //Ejecutarcomando
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        if(AppDatabase.getInstance(getActivity()).getCommandDao().getByTrigger(trigger) == 0){

                            //Esta linea y el getActivityonbackpressed son para el CU AñadirComando
                            mCallback.AddCommand(name,trigger,action); //Los manda a la activity

                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    getActivity().onBackPressed();//Cierra el fragment
                                }
                            });

                        }
                        else{
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Snackbar snackbar = Snackbar.make(v,R.string.InsertErr,Snackbar.LENGTH_SHORT);
                                    snackbar.show();
                                }
                            });

                        }
                    }
                });
            }
        });

        return v;
    }

}