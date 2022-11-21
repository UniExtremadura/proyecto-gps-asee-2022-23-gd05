package es.unex.dcadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import es.unex.dcadmin.command.CommandActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Comprobar si existe ya el token, si existe, saltar a CommandActivity
        setContentView(R.layout.activity_add_bot_token);

        ImageView command_b = findViewById(R.id.access);
        command_b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Guardar token
                Intent i = new Intent(MainActivity.this, CommandActivity.class);
                startActivity(i);
            }
        });

    }




}