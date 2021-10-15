package no.hiof.larsgh.rpgda;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Menu extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Button lgButton = findViewById(R.id.loadGameButton);

        lgButton.setVisibility(View.INVISIBLE);
    }

    public void goCCScreen(View view){
        Intent ccScreen = new Intent(this, CharacterCreation.class);
        startActivity(ccScreen);
    }

    public void goCSScreen(View view){
        Intent csScreen = new Intent(this, CharcterSelection.class);
        startActivity(csScreen);
    }

    public void backTitleScreen(View view){
        Intent bTitleScreen = new Intent(this, TitleScreen.class);
        startActivity(bTitleScreen);
    }
}