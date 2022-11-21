package es.unex.dcadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.w3c.dom.Text;

import es.unex.dcadmin.discord.discordApiManager;

public class MainActivity extends AppCompatActivity {
    public static TextView mensaje;
    public static ProgressBar progressBar;
    public static View.OnClickListener listener;
    public static View layout;

    public static View command_b;
    public static TextView addTokenView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bot_token);

        addTokenView = (TextView) findViewById(R.id.addTokenView);

        mensaje = findViewById(R.id.infoMessage);
        progressBar = findViewById(R.id.progressBar);

        layout = findViewById(R.id.entireScreen);
        listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mensaje.setText("El bot ha sido encendido.");
            }
        };

        command_b = findViewById(R.id.access);
        command_b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Si ya hay token en preferencias, no guarda, si no, guardar token
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