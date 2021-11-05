package no.hiof.larsgh.rpgda;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class CharacterSheetScreen extends AppCompatActivity {

    TextView cssName, cssLevel, cssHP, cssMP, cssStrength, cssIntelligence, cssAgility, cssSP;
    public static final String DEFAULT = "N/A";
    public static final int zNum = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_sheet_screen);

        cssName = (TextView) findViewById(R.id.cssName);
        cssLevel = (TextView) findViewById(R.id.cssLevel);
        cssHP = (TextView) findViewById(R.id.cssHP);
        cssMP = (TextView) findViewById(R.id.cssMP);
        cssStrength = (TextView) findViewById(R.id.cssStrength);
        cssIntelligence = (TextView) findViewById(R.id.cssIntelligence);
        cssAgility = (TextView) findViewById(R.id.cssAgility);
        cssSP = (TextView)findViewById(R.id.cssSP);

        loadStat(cssName, cssLevel, cssHP, cssMP, cssStrength, cssIntelligence, cssAgility, cssSP);
    }

    @SuppressLint("SetTextI18n")
    public void loadStat(TextView editName, TextView cssLevel, TextView cssHP, TextView cssMP,
                         TextView cssStrength, TextView cssIntelligence, TextView cssAgility, TextView cssSP){
        SharedPreferences spStat = getSharedPreferences("userStat", MODE_PRIVATE);
        String name = spStat.getString("name", DEFAULT);
        int level = spStat.getInt("level", zNum);
        int hp = spStat.getInt("hp", zNum);
        int mp = spStat.getInt("mp", zNum);
        int strength = spStat.getInt("strength", zNum);
        int intelligence = spStat.getInt("intelligence", zNum);
        int agility = spStat.getInt("agility", zNum);
        int sp = spStat.getInt("sp", zNum);

        if (name.matches("")){
            Toast.makeText(this,"No data found", Toast.LENGTH_SHORT).show();
        }
        else {
            editName.setText(name);
            cssLevel.setText(Integer.toString(level));
            cssHP.setText(Integer.toString(hp));
            cssMP.setText(Integer.toString(mp));
            cssStrength.setText(Integer.toString(strength));
            cssIntelligence.setText(Integer.toString(intelligence));
            cssAgility.setText(Integer.toString(agility));
            cssSP.setText(Integer.toString(sp));
        }
    }

    public void goGameScreen(View view){
        Intent GameScreen = new Intent(this, GameScreen.class);
        startActivity(GameScreen);
    }

    public void goInventoryScreen(View view){
        Intent InventoryScreen = new Intent(this, InventoryScreen.class);
        startActivity(InventoryScreen);
    }
}