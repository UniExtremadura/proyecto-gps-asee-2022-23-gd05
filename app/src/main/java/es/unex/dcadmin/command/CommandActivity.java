package es.unex.dcadmin.command;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import es.unex.dcadmin.AppExecutors;
import es.unex.dcadmin.R;
import es.unex.dcadmin.discord.discordApiManager;
import es.unex.dcadmin.roomdb.AppDatabase;

public class CommandActivity extends AppCompatActivity implements CommandDetail.OnCallbackReceivedUpdate {
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

        // use a linear layout manager
        //Este layoutmanager es para manejar las rejillas del recyclerview
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);//Le asignamos el layoutmanager al recycler view para que así podamos ir por cada fila de las actividades creadas

        //Adapter adapta los datos del modelo a una vista, mostrará los datos Java de la forma que se hayan definido en el adapter y así se verán en el recyclerview
        mAdapter = new CommandAdapter(new CommandAdapter.OnItemClickListener() {//Cuando se clicke el elemento haga algo
            @Override
            public void onItemClick(Command item) {//Cuando se clicke mostrará el detalle
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
        }, getApplicationContext());

        mRecyclerView.setAdapter(mAdapter);

        BottomNavigationView bottomNavigationView;
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(this::onOptionsItemSelected);

        //getSupportActionBar().hide();
        AppDatabase.getInstance(this);
        Command command = new Command("test","testtrigger","testaccion");
        command.construir(discordApiManager.getSingleton(), discordApiManager.getMapaMessageCreated());

        Context context = this;
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                AppDatabase.getInstance(context).getCommandDao().insert(command);
                runOnUiThread(()->mAdapter.add(command));
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {//Este método no hace nada ya
        super.onActivityResult(requestCode, resultCode, data);
        //Esto se ejecuta cuando le hemos dado a OK a añadir una tarea
        log("Entered onActivityResult()");

        if(requestCode == ADD_TODO_ITEM_REQUEST && resultCode == RESULT_OK){//Si ha ido bien
            Command toDoItem = new Command(data);//Creamos un objeto con los datos de la tarea
            mAdapter.add(toDoItem);//Añadimos el item al adapter, así se podrá guardar en el recyclerview y podrá ver
        }
    }

    @Override
    public void UpdateCommand(Command command){
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                AppDatabase db = AppDatabase.getInstance(CommandActivity.this);
                db.getCommandDao().update(command);

                runOnUiThread(() -> mAdapter.update(command));
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();

        // Load saved ToDoItems, if necessary

        if (mAdapter.getItemCount() == 0)
            loadItems();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy(){
        AppDatabase.getInstance(this).close();
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
                 //Quita el token de shared preferences y llama a la Activity / quita el token y cierra la aplicación
                return true;
            case R.id.page_2:
                return true;

            case R.id.page_3:
                //Código de replace de un fragment...
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void dump() {
        for (int i = 0; i < mAdapter.getItemCount(); i++) {
            String data = ((Command) mAdapter.getItem(i)).toLog();
            log("Item " + i);
        }

    }

    // Load stored ToDoItems
    private void loadItems() {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                List<Command> items = AppDatabase.getInstance(CommandActivity.this).getCommandDao().getAll();
                runOnUiThread(() ->mAdapter.load(items));
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

