package es.unex.dcadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import es.unex.dcadmin.users.UsersList;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bot_token);

        UsersList fragment = new UsersList();

        ImageView iv = findViewById(R.id.access);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.entireScreen, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }
}