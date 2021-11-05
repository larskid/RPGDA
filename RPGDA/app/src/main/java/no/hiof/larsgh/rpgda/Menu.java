package no.hiof.larsgh.rpgda;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Menu extends AppCompatActivity {

    public static final String DEFAULT = "N/A";
    Button lgButton, desButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        lgButton = (Button) findViewById(R.id.loadGameButton);
        saveCheck();
        desButton = (Button) findViewById(R.id.deletExsistingSaveButton);


        desButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Alert for deleting saved data (stat)
                AlertDialog.Builder builder = new AlertDialog.Builder(Menu.this);
                builder.setMessage("Are you sure you want to delete the character?");
                builder.setTitle("Deleting character!");
                builder.setCancelable(false);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        lgButton.setVisibility(View.INVISIBLE);

                        SharedPreferences spDelete = getSharedPreferences("userStat", MODE_PRIVATE);
                        SharedPreferences.Editor editor = spDelete.edit();

                        editor.clear();
                        editor.commit();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        //saveCheck();
    }


    public void goCCScreen(View view){
        Intent ccScreen = new Intent(this, CharacterCreation.class);
        startActivity(ccScreen);
    }

    public void goCSScreen(View view){
        Intent csScreen = new Intent(this, CharacterSelection.class);
        startActivity(csScreen);
    }

    public void backTitleScreen(View view){
        Intent bTitleScreen = new Intent(this, TitleScreen.class);
        startActivity(bTitleScreen);
    }

    public void saveCheck(){
        SharedPreferences spName = getSharedPreferences("userStat", MODE_PRIVATE);
        String name = spName.getString("name", DEFAULT);
        if (name == DEFAULT) {
            lgButton.setVisibility(View.INVISIBLE);
        }
        else {
            lgButton.setVisibility(View.VISIBLE);
        }
    }
}