package es.unex.dcadmin.command;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import es.unex.dcadmin.AppExecutors;
import es.unex.dcadmin.R;
import es.unex.dcadmin.roomdb.AppDatabase;

public class CommandActivity extends AppCompatActivity implements AddCommandFragment.OnCallbackReceivedAdd{
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
        //TODO - Set a Linear Layout Manager to the RecyclerView
        //Este layoutmanager es para manejar las rejillas del recyclerview
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);//Le asignamos el layoutmanager al recycler view para que así podamos ir por cada fila de las actividades creadas

        //TODO - Create a new Adapter for the RecyclerView
        // specify an adapter (see also next example)
        //Adapter adapta los datos del modelo a una vista, mostrará los datos Java de la forma que se hayan definido en el adapter y así se verán en el recyclerview
        mAdapter = new CommandAdapter(new CommandAdapter.OnItemClickListener() {//Cuando se clicke el elemento haga algo
            @Override
            public void onItemClick(Command item) {//Cuando se clicke mostrará el detalle
            }
        });

        //TODO - Attach the adapter to the RecyclerView
        mRecyclerView.setAdapter(mAdapter);



        BottomNavigationView bottomNavigationView;
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(this::onOptionsItemSelected);

        //getSupportActionBar().hide();
        AppDatabase.getInstance(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {//Este método no hace nada ya
        super.onActivityResult(requestCode, resultCode, data);
        //Esto se ejecuta cuando le hemos dado a OK a añadir una tarea
        log("Entered onActivityResult()");

        // TODO - Check result code and request code.
        if(requestCode == ADD_TODO_ITEM_REQUEST && resultCode == RESULT_OK){//Si ha ido bien
            Command toDoItem = new Command(data);//Creamos un objeto con los datos de la tarea
            mAdapter.add(toDoItem);//Añadimos el item al adapter, así se podrá guardar en el recyclerview y podrá ver
        }

        //TODO - Create a TodoItem from data and add it to the adapter
    }

    @Override
    public void AddCommand(String name, String trigger, String action) {//El método que llama el fragment antes de morir pasando los datos
        // Write your logic here.

        //mAdapter.add(command);//Añadimos el item al adapter, así se podrá guardar en el recyclerview y podrá ver

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

        // Save Commands

        //saveItems();

    }

    @Override
    protected void onDestroy(){
        AppDatabase.getInstance(this).close();
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /*super.onCreateOptionsMenu(menu);

        menu.add(Menu.NONE, MENU_DELETE, Menu.NONE, "Delete all");
        menu.add(Menu.NONE, MENU_DUMP, Menu.NONE, "Dump to log");*/
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.page_1:
                 //Quita el token de shared preferences y llama a la Activity / quita el token y cierra la aplicación
                return true;
            case R.id.page_2:
                /*if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                    getSupportFragmentManager().popBackStack(getSupportFragmentManager().getBackStackEntryAt(0).getId(), getSupportFragmentManager().POP_BACK_STACK_INCLUSIVE);
                } else {
                    super.onBackPressed();}*/
                AddCommandFragment fragment = new AddCommandFragment();
                getSupportFragmentManager().beginTransaction() .replace(R.id.content_to_do_manager, fragment)
                        .addToBackStack(null)
                        .commit();
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

    // Save ToDoItems to file
    /*private void saveItems() {
        PrintWriter writer = null;
        try {
            FileOutputStream fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
            writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
                    fos)));

            for (int idx = 0; idx < mAdapter.getItemCount(); idx++) {

                writer.println(mAdapter.getItem(idx));

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != writer) {
                writer.close();
            }
        }
    }*/

    private void log(String msg) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.i(TAG, msg);
    }

    /*@Override
    public void onBackPressed(){ //Para no acceder a la pantalla de añadir token si ya lo hemos añadido
        moveTaskToBack(true);
    }*/

}

