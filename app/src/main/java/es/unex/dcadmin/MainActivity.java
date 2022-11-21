package es.unex.dcadmin;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;

public class MainActivity extends AppCompatActivity {
    SharedPreferences prefs;
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs  = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        setContentView(R.layout.activity_add_bot_token);

        token = prefs.getString("token", "");

        EditText tokenEditText = findViewById(R.id.addTokenView);

        if(!token.equals("")) {
            tokenEditText.setText(token);
        }else{
            tokenEditText.setText("");
            tokenEditText.setHint("Token...");
        }

        ImageView access = findViewById(R.id.access);

        access.setOnClickListener(view -> {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("token", tokenEditText.getText().toString());
            editor.commit();
        });

    }

}