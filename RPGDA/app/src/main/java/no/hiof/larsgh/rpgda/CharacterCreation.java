package no.hiof.larsgh.rpgda;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class CharacterCreation extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_creation);



    }

    public void goCharacterSelectionScreen(View view){
        Intent characterSelectionScreen = new Intent(this, CharcterSelection.class);
        startActivity(characterSelectionScreen);

    }

    public void backMenuScreen(View view){
        Intent bMenuScreen = new Intent(this, Menu.class);
        startActivity(bMenuScreen);
    }
}