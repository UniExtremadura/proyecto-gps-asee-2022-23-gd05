package es.unex.dcadmin.command;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import es.unex.dcadmin.AppExecutors;
import es.unex.dcadmin.R;
import es.unex.dcadmin.roomdb.AppDatabase;

public class CommandActivity extends AppCompatActivity {
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

            }});

        //TODO - Attach the adapter to the RecyclerView
        mRecyclerView.setAdapter(mAdapter);

        BottomNavigationView bottomNavigationView;
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(this::onOptionsItemSelected);

        //getSupportActionBar().hide();
        AppDatabase.getInstance(this);

        Context context = this;
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                Command c = new Command("Test","Test","Test");
                AppDatabase.getInstance(context).getCommandDao().insert(c);

                runOnUiThread(() -> mAdapter.add(c));
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

