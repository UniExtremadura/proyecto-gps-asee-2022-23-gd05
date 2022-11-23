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

public class MainActivity extends AppCompatActivity {
    SharedPreferences prefs;
    String token;
    public static TextView mensaje;
    public static ProgressBar progressBar;
    public static View.OnClickListener listener;
    public static View layout;

    public static View access;
    public static TextView tokenEditText;


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
            MainActivity.tokenEditText.setClickable(false);
            progressBar.setVisibility(View.VISIBLE);

            discordApiManager.setToken(MainActivity.tokenEditText.getText().toString());
            discordApiManager.getSingleton();
        });
    }
}