package no.hiof.larsgh.rpgda;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameScreen extends AppCompatActivity {

    TextView text;
    Button bOption1, bOption2, bOption3, bOption4;

    Story story = new Story(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);

        text = findViewById(R.id.textViewGameInfo);
        bOption1 = findViewById(R.id.buttonOption1);
        bOption2 = findViewById(R.id.buttonOption2);
        bOption3 = findViewById(R.id.buttonOption3);
        bOption4 = findViewById(R.id.buttonOption4);

        bOption1.setTransformationMethod(null);
        bOption2.setTransformationMethod(null);
        bOption3.setTransformationMethod(null);
        bOption4.setTransformationMethod(null);

        story.startingPoint();

    }

    public void goCharacterSheetScreen(View view){
        Intent characterSheetScreen = new Intent(this, CharacterSheetScreen.class);
        startActivity(characterSheetScreen);
    }

    public void goInventoryScreen(View view){
        Intent inventroyScreen = new Intent(this, InventoryScreen.class);
        startActivity(inventroyScreen);
    }

    public void button1(View view){
        story.selectPosition(story.nextPosition1);
    }
    public void button2(View view){
        story.selectPosition(story.nextPosition2);
    }
    public void button3(View view){
        story.selectPosition(story.nextPosition3);
    }
    public void button4(View view){
        story.selectPosition(story.nextPosition4);
    }

    public void goMenuScreen(){
        Intent meunScreen = new Intent(this, Menu.class);
        startActivity(meunScreen);
    }

}