package no.hiof.larsgh.rpgda;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class CharacterCreation extends AppCompatActivity {

    //Global variables
    EditText userName;
    TextView userLevel, userHP, userMP , userStrength , userIntelligence , userAgility;
    Button buttonConfirme;

    int level = 1;
    int hp = 100;
    int mp = 100;
    int strength = 10;
    int intelligence = 10;
    int agility = 10;
    int sp = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_creation);

        //linking username to edittext
        userName = (EditText)findViewById(R.id.csName);
        userLevel = (TextView)findViewById(R.id.playerLevel);
        userHP= (TextView)findViewById(R.id.textHP);
        userMP= (TextView)findViewById(R.id.textMP);
        userStrength= (TextView)findViewById(R.id.textStrength);
        userIntelligence= (TextView)findViewById(R.id.textIntelligence);
        userAgility= (TextView)findViewById(R.id.textAgility);

        buttonConfirme = (Button) findViewById(R.id.buttonConfirm);
        buttonConfirme.setOnClickListener(v -> {
            String name = userName.getText().toString();
            if (name.matches("")){
                Toast.makeText(getApplicationContext(), "Name is requierd", Toast.LENGTH_SHORT).show();
            }
            else {
                saveStat();
                Intent goButtonConfirme = new Intent(getApplicationContext(), GameScreen.class);
                startActivity(goButtonConfirme);
            }

        });
    }

    //store the data in the SharedPreferences
    public void saveStat(){
        SharedPreferences spName = getSharedPreferences("userStat", MODE_PRIVATE);
        SharedPreferences.Editor editor = spName.edit();
        editor.putString("name", userName.getText().toString());
        editor.putInt("level", level);
        editor.putInt("hp", hp);
        editor.putInt("mp", mp);
        editor.putInt("strength", strength);
        editor.putInt("intelligence", intelligence);
        editor.putInt("agility", agility);
        editor.putInt("sp", sp);
        editor.apply();
    }

    public void backMenuScreen(View view){
        Intent bMenuScreen = new Intent(this, Menu.class);
        startActivity(bMenuScreen);
    }
}