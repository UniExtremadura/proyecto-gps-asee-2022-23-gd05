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

import es.unex.dcadmin.R;
import es.unex.dcadmin.command.CommandActivity;
import es.unex.dcadmin.discord.discordApiManager;


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
        //Comprobar si existe ya el token, si existe, esconder el editText
        //Es decir, si no estás logeado (no has puesto el token) lo escribes y despues sale cargando y boton
        //Si estás logeado, desaparece la capacidad de escribir y sale lo de cargando y aparece el boton (necesitaras darle al boton de acceder)
        setContentView(R.layout.activity_add_bot_token);

        addTokenView = findViewById(R.id.addTokenView);

        mensaje = findViewById(R.id.infoMessage);
        progressBar = findViewById(R.id.progressBar);

        layout = findViewById(R.id.entireScreen);
        listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, CommandActivity.class);
                startActivity(i);
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

                /*Intent i = new Intent(MainActivity.this, CommandActivity.class);
                startActivity(i);*///Este código comentado debe ir en el boton que aparece despues de cargar
            }
        });

    }




}