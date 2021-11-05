package no.hiof.larsgh.rpgda;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class CharacterSelection extends AppCompatActivity {

    TextView csName, csLevel, csHP, csMP, csStrength, csIntelligence, csAgility, csSP;
    public static final String DEFAULT = "N/A";
    public static final int zNum = 0;
    int level, hp, mp, strength, intelligence, agility, sp;
    String name;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charcter_selection);

        csName = (TextView)findViewById(R.id.csName);
        csLevel = (TextView)findViewById(R.id.csLevel);
        csHP = (TextView)findViewById(R.id.csHP);
        csMP = (TextView)findViewById(R.id.csMP);
        csStrength = (TextView)findViewById(R.id.csStrength);
        csIntelligence = (TextView)findViewById(R.id.csIntelligence);
        csAgility = (TextView)findViewById(R.id.csAgility);
        csSP = (TextView)findViewById(R.id.csSP);

        SharedPreferences sharedPreferences = getSharedPreferences("userStat", MODE_PRIVATE);
        name = sharedPreferences.getString("name", DEFAULT);
        level = sharedPreferences.getInt("level", zNum);
        hp = sharedPreferences.getInt("hp", zNum);
        mp = sharedPreferences.getInt("mp", zNum);
        strength = sharedPreferences.getInt("strength", zNum);
        intelligence = sharedPreferences.getInt("intelligence", zNum);
        agility = sharedPreferences.getInt("agility", zNum);
        sp = sharedPreferences.getInt("sp", zNum);

        csName.setText(name);
        csLevel.setText(Integer.toString(level));
        csHP.setText(Integer.toString(hp));
        csMP.setText(Integer.toString(mp));
        csStrength.setText(Integer.toString(strength));
        csIntelligence.setText(Integer.toString(intelligence));
        csAgility.setText(Integer.toString(agility));
        csSP.setText(Integer.toString(sp));
    }


    public void backMenuScreenFromCS(View view){
        Intent backMenuScreen = new Intent(this, Menu.class);
        startActivity(backMenuScreen);
    }

    public void goGameScreen(View view){
        Intent gameScreen = new Intent(this, GameScreen.class);
        startActivity(gameScreen);
    }
}