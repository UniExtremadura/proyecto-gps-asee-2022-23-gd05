package es.unex.dcadmin;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import es.unex.dcadmin.discord.discordApiManager;

public class CommandActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_command);

        BottomNavigationView bottomNavigationView;
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(this::onOptionsItemSelected);
    }

    @Override
    protected void onDestroy(){

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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.page_1:
                //Quita el token de shared preferences y llama a la Activity / quita el token y cierra la aplicación
                //Para poder cerrar la conexion con la api (Ejecutar comando)


                Intent i = new Intent(CommandActivity.this, MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);

                CommandActivity.this.finish();

                return true;
            case R.id.page_2:
                return true;

            case R.id.page_3:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
