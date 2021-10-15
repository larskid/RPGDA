package no.hiof.larsgh.rpgda;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class CharcterSelection extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charcter_selection);
    }

    public void backMenuScreenfrocCS(View view){
        Intent backMenuScreen = new Intent(this, Menu.class);
        startActivity(backMenuScreen);
    }

    public void goGameScreen(View view){
        Intent gameScreen = new Intent(this, GameScreen.class);
        startActivity(gameScreen);
    }
}