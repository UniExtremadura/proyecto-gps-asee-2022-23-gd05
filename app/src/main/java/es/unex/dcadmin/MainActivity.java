package es.unex.dcadmin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import es.unex.dcadmin.command.CommandActivity;
import es.unex.dcadmin.discord.discordApiManager;

public class MainActivity extends AppCompatActivity implements discordApiManager.callbackSetCorrectData, discordApiManager.callbackSetIncorrectData {
    private SharedPreferences prefs;
    private String token;
    private TextView mensaje;
    private ProgressBar progressBar;
    private View.OnClickListener listener;
    private View layout;

    private View access;
    private TextView tokenEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs  = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        setContentView(R.layout.activity_add_bot_token);
        mensaje = findViewById(R.id.infoMessage);
        progressBar = findViewById(R.id.progressBar);

        layout = findViewById(R.id.entireScreen);

        token = prefs.getString("token", "");
        tokenEditText = findViewById(R.id.addTokenView);
        tokenEditText.setText(token);
        tokenEditText.setHint("Token...");

        listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("token", tokenEditText.getText().toString());
                editor.commit();

                Intent i = new Intent(MainActivity.this, CommandActivity.class);
                startActivity(i);
            }
        };

        access = findViewById(R.id.access);

        access.setOnClickListener(view -> {
            // Bloquear pantalla a la espera de que se desbloquee
            access.setClickable(false);
            tokenEditText.setClickable(false);
            progressBar.setVisibility(View.VISIBLE);

            discordApiManager.getSingleton().setToken(tokenEditText.getText().toString());
            discordApiManager.getSingleton().getApi(this);
        });
    }

    public void setCorrectCallback(){
        mensaje.setText("Pulsa en cualquier lugar para continuar");
        mensaje.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
        layout.setOnClickListener(listener);
    }

    public void setIncorrectCallback(){
        mensaje.setVisibility(View.VISIBLE);
        mensaje.setText("No se ha podido iniciar sesion en Discord. ¿El token es correcto?");
        progressBar.setVisibility(View.INVISIBLE);
        access.setClickable(true);
        tokenEditText.setClickable(true);
    }

}