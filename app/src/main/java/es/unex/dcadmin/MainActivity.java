package es.unex.dcadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import es.unex.dcadmin.R;
import es.unex.dcadmin.discord.discordApiManager;
import es.unex.dcadmin.users.UsersList;
import es.unex.dcadmin.discord.discordApiManager;
import es.unex.dcadmin.users.UsersList;

public class MainActivity extends AppCompatActivity {
    public static TextView mensaje;
    public static ProgressBar progressBar;
    public static View.OnClickListener listener;
    public static View layout;
    public static ImageView command_b;
    public static TextView addTokenView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bot_token);

        addTokenView = findViewById(R.id.addTokenView);

        mensaje = findViewById(R.id.infoMessage);
        progressBar = findViewById(R.id.progressBar);

        layout = findViewById(R.id.entireScreen);
        listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UsersList fragment = new UsersList();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.entireScreen, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        };

        command_b = findViewById(R.id.access);
        command_b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                command_b.setClickable(false);
                addTokenView.setClickable(false);
                progressBar.setVisibility(View.VISIBLE);
                discordApiManager.setToken(addTokenView.getText().toString());
                // cargando... -> pantalla de cargando
                discordApiManager.getSingleton();
            }
        });
    }
}