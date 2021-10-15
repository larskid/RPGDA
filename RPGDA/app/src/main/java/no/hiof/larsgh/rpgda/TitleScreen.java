package no.hiof.larsgh.rpgda;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TitleScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title_screen);

    }
    public void QuitApp(View view) {
        TitleScreen.this.finish();
        System.exit(0);
    }

    public void goMenuScreen(View view){
        Intent menuScreen = new Intent(this, Menu.class);
        startActivity(menuScreen);
    }

}