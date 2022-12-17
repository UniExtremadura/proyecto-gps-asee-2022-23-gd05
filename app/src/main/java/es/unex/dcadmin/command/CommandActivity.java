package es.unex.dcadmin.command;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import es.unex.dcadmin.AppContainer;
import es.unex.dcadmin.AppExecutors;
import es.unex.dcadmin.MainActivity;
import es.unex.dcadmin.DCAdmin;
import es.unex.dcadmin.R;
import es.unex.dcadmin.commandRecord.CommandRecordList;
import es.unex.dcadmin.discord.discordApiManager;
import es.unex.dcadmin.roomdb.AppDatabase;
import es.unex.dcadmin.users.UsersList;
import es.unex.dcadmin.viewModels.CommandViewModel;
import es.unex.dcadmin.viewModels.MasterDetailCommandViewModel;


public class CommandActivity extends AppCompatActivity implements AddCommandFragment.OnCallbackReceivedAdd, CommandDetail.OnCallbackReceivedUpdate {
    //La activity implementa el callback

    private static final int ADD_TODO_ITEM_REQUEST = 0;

    private static final String FILE_NAME = "TodoManagerActivityData.txt";
    private static final String TAG = "Lab-UserInterface";

    // IDs for menu items
    private static final int MENU_DELETE = Menu.FIRST;
    private static final int MENU_DUMP = Menu.FIRST + 1;

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private CommandAdapter mAdapter;
    private CommandViewModel mViewModel;
    private MasterDetailCommandViewModel sharedViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_command);

        AppContainer appContainer = ((DCAdmin) getApplication()).appContainer;
        mViewModel = new ViewModelProvider(this, (ViewModelProvider.Factory) appContainer.mFactory).get(CommandViewModel.class);

        sharedViewModel = new ViewModelProvider(this, (ViewModelProvider.Factory) appContainer.mFactory).get(MasterDetailCommandViewModel.class);

        mViewModel.getCommands().observe(this, this::onCommandsLoaded);

        mRecyclerView = findViewById(R.id.commandRecyclerView);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        //Este layoutmanager es para manejar las rejillas del recyclerview
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);//Le asignamos el layoutmanager al recycler view para que así podamos ir por cada fila de las actividades creadas

        //Adapter adapta los datos del modelo a una vista, mostrará los datos Java de la forma que se hayan definido en el adapter y así se verán en el recyclerview
        mAdapter = new CommandAdapter(new CommandAdapter.OnItemClickListener() {//Cuando se clicke el elemento haga algo
            @Override
            public void onItemClick(Command item) {//Cuando se clicke mostrará el detalle

                CommandDetail fragment = new CommandDetail();

                /*Bundle bundle = new Bundle();
                bundle.putLong(CommandDetail.ARG_PARAM2, item.getId());
                bundle.putString(CommandDetail.ARG_PARAM1, item.getName());
                bundle.putString(CommandDetail.ARG_PARAM3, item.getTrigger_text());
                bundle.putString(CommandDetail.ARG_PARAM4, item.getAction_text());
                //Todos los datos del comando

                fragment.setArguments(bundle);*/

                sharedViewModel.select(item);

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_to_do_manager, fragment)
                        .addToBackStack(null)
                        .commit();

            }
        }, new CommandAdapter.OnDeleteClickListener() { //Esto es para el listener de borrar
            @Override
            public void onDeleteClick(Command item) {
                mViewModel.deleteCommand(item);
                runOnUiThread(() -> mAdapter.delete(item));
            }
        }, getApplicationContext());




        ImageView command_list = findViewById(R.id.command_record_list); //Lista de comandos
        command_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommandRecordList fragment = new CommandRecordList();

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_to_do_manager, fragment)
                        .addToBackStack(null)
                        .commit();

            }
        });

        mRecyclerView.setAdapter(mAdapter);

        ImageView imageView = findViewById(R.id.deleteAllCommands); //Borrar comando
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                runOnUiThread(() -> mAdapter.clear());
                mViewModel.deleteCommands();
            }
        });


        BottomNavigationView bottomNavigationView;
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(this::onOptionsItemSelected);

        AppDatabase.getInstance(this);
    }

    @Override
    public void AddCommand(String name, String trigger, String action) {//El método que llama el fragment antes de morir pasando los datos
        Context context = this; //Esto es para EjecutarComando, para poder obtener la BD en el listener del comando y así añadir elementos al historial

        Command command = new Command(name, trigger, action);//Creamos un objeto con los datos de la tarea

        command.setId(mViewModel.insertCommand(command));

        AppExecutors.getInstance().networkIO().execute(new Runnable() {//Porque operaciones de DB no se pueden hacer en el hilo principal
            @Override
            public void run() {
                //Ejecutar comando (la destruccion está en el adapter en delete y clear)
                command.construir(discordApiManager.getSingleton().getApi(null),discordApiManager.getSingleton().getMapaMessageCreated(), context);
            }
        });

        runOnUiThread(() -> mAdapter.add(command));

    }

    @Override
    protected void onDestroy(){

        //Para poder cerrar la conexion con la api (Ejecutar comando)
        AppExecutors.getInstance().networkIO().execute(new Runnable() {
            @Override
            public void run() {

                discordApiManager.getSingleton().apagar();

            }
        });

        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.page_1:
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = prefs.edit();
                editor.remove("token");
                editor.commit();


                Intent i = new Intent(CommandActivity.this, MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);

                this.finish();

                return true;
            case R.id.page_2:
                AddCommandFragment fragment = new AddCommandFragment();
                getSupportFragmentManager().beginTransaction() .replace(R.id.content_to_do_manager, fragment)
                        .addToBackStack(null)
                        .commit();
                return true;

            case R.id.page_3:
                UsersList listFragment = new UsersList();
                getSupportFragmentManager().beginTransaction() .replace(R.id.content_to_do_manager, listFragment)
                        .addToBackStack(null)
                        .commit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void UpdateCommand(Command command){
        Context context = this;//Esto es para EjecutarComando
        mViewModel.updateCommand(command);
        AppExecutors.getInstance().networkIO().execute(new Runnable() {
            @Override
            public void run() {
                command.construir(discordApiManager.getSingleton().getApi(null),discordApiManager.getSingleton().getMapaMessageCreated(), context);
            }
        });
        runOnUiThread(() -> mAdapter.update(command));
    }

    private void onCommandsLoaded(List<Command> commands){
        runOnUiThread(() -> mAdapter.swap(commands));
        loadBotCommands(commands);
    }

    public void loadBotCommands(List<Command> items){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Context context = this;
        if(items != null) {
            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    for (Command c : items) {
                        if (!c.isConstruido()) {
                            c.construir(discordApiManager.getSingleton().getApi(null), discordApiManager.getSingleton().getMapaMessageCreated(), context);

                            if (prefs.getLong("default", -1) == c.getId()) {
                                Command def = new Command(c.getName(), "!", c.getAction_text());
                                def.construir(discordApiManager.getSingleton().getApi(null), discordApiManager.getSingleton().getMapaMessageCreated(), context);
                            }

                        }
                    }
                }
            });
        }
    }
}
