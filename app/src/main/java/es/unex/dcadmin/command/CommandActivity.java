package es.unex.dcadmin.command;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import es.unex.dcadmin.AppExecutors;
import es.unex.dcadmin.MainActivity;
import es.unex.dcadmin.R;
import es.unex.dcadmin.commandRecord.CommandRecordList;
import es.unex.dcadmin.discord.discordApiManager;
import es.unex.dcadmin.roomdb.AppDatabase;
import es.unex.dcadmin.users.UsersList;


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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_command);

        mRecyclerView = findViewById(R.id.commandRecyclerView);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        //Este layoutmanager es para manejar las rejillas del recyclerview
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);//Le asignamos el layoutmanager al recycler view para que as?? podamos ir por cada fila de las actividades creadas

        //Adapter adapta los datos del modelo a una vista, mostrar?? los datos Java de la forma que se hayan definido en el adapter y as?? se ver??n en el recyclerview
        mAdapter = new CommandAdapter(new CommandAdapter.OnItemClickListener() {//Cuando se clicke el elemento haga algo
            @Override
            public void onItemClick(Command item) {//Cuando se clicke mostrar?? el detalle

                CommandDetail fragment = new CommandDetail();

                Bundle bundle = new Bundle();
                bundle.putLong(CommandDetail.ARG_PARAM2, item.getId());
                bundle.putString(CommandDetail.ARG_PARAM1, item.getName());
                bundle.putString(CommandDetail.ARG_PARAM3, item.getTrigger_text());
                bundle.putString(CommandDetail.ARG_PARAM4, item.getAction_text());
                //Todos los datos del comando

                fragment.setArguments(bundle);

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_to_do_manager, fragment)
                        .addToBackStack(null)
                        .commit();

            }
        }, new CommandAdapter.OnDeleteClickListener() { //Esto es para el listener de borrar
            @Override
            public void onDeleteClick(Command item) {
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        AppDatabase.getInstance(CommandActivity.this).getCommandDao().delete(item);

                        runOnUiThread(() -> mAdapter.delete(item));
                    }
                });
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
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        AppDatabase.getInstance(CommandActivity.this).getCommandDao().deleteAll();

                        runOnUiThread(() -> mAdapter.clear());
                    }
                });
            }
        });


        BottomNavigationView bottomNavigationView;
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(this::onOptionsItemSelected);

        AppDatabase.getInstance(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {//Este m??todo no hace nada ya
        super.onActivityResult(requestCode, resultCode, data);
        //Esto se ejecuta cuando le hemos dado a OK a a??adir una tarea
        log("Entered onActivityResult()");
        
        if(requestCode == ADD_TODO_ITEM_REQUEST && resultCode == RESULT_OK){//Si ha ido bien
            Command toDoItem = new Command(data);//Creamos un objeto con los datos de la tarea
            mAdapter.add(toDoItem);//A??adimos el item al adapter, as?? se podr?? guardar en el recyclerview y podr?? ver
        }
    }

    @Override
    public void AddCommand(String name, String trigger, String action) {//El m??todo que llama el fragment antes de morir pasando los datos
        Context context = this; //Esto es para EjecutarComando, para poder obtener la BD en el listener del comando y as?? a??adir elementos al historial

        AppExecutors.getInstance().diskIO().execute(new Runnable() {//Porque operaciones de DB no se pueden hacer en el hilo principal
            @Override
            public void run() {
                Command command = new Command(name, trigger, action);//Creamos un objeto con los datos de la tarea
                AppDatabase db = AppDatabase.getInstance(CommandActivity.this);
                long id = db.getCommandDao().insert(command);
                //update item ID
                command.setId(id);
                //insert into adapter list

                //No se puede actualizar la vista fuera del hilo principal por eso hacemos esto, gracias a que estamos en una activity
                runOnUiThread(() -> mAdapter.add(command));

                //Ejecutar comando (la destruccion est?? en el adapter en delete y clear)
                command.construir(discordApiManager.getSingleton(),discordApiManager.getMapaMessageCreated(), context);
            }
        });

    }


    @Override
    public void onResume() {
        super.onResume();

        if (mAdapter.getItemCount() == 0)
            loadItems();
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Save Commands

        //saveItems();

    }

    @Override
    protected void onDestroy(){
        AppDatabase.getInstance(this).close();
        AppDatabase.closeInstance();

        //Para poder cerrar la conexion con la api (Ejecutar comando)
        AppExecutors.getInstance().networkIO().execute(new Runnable() {
            @Override
            public void run() {

                discordApiManager.apagar();

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

    private void loadItems() {

        Context context = this; //Esto es para el de ejecutar comando

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {

                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

                List<Command> items = AppDatabase.getInstance(CommandActivity.this).getCommandDao().getAll();
                for(Command c: items){
                    if(!c.isConstruido()){
                        c.construir(discordApiManager.getSingleton(),discordApiManager.getMapaMessageCreated(),context);

                        if(prefs.getLong("default", -1) == c.getId())
                        {
                            Command def = new Command(c.getName(), "!", c.getAction_text());
                            def.construir(discordApiManager.getSingleton(), discordApiManager.getMapaMessageCreated(), context);
                        }

                    }
                }

                runOnUiThread(() ->mAdapter.load(items));
            }
        });
    }

    @Override
    public void UpdateCommand(Command command){
        Context context = this;//Esto es para EjecutarComando
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                AppDatabase db = AppDatabase.getInstance(CommandActivity.this);
                db.getCommandDao().update(command);

                command.construir(discordApiManager.getSingleton(),discordApiManager.getMapaMessageCreated(), context);
                runOnUiThread(() -> mAdapter.update(command));

                //Ejecutar comando. Esto es para que se actualice el trigger y la accion al modificar
            }
        });
    }

    private void log(String msg) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.i(TAG, msg);
    }
}
