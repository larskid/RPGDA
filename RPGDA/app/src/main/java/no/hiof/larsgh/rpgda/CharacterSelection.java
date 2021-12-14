package no.hiof.larsgh.rpgda;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class CharacterSelection extends AppCompatActivity {

    TextView csName, csLevel, csHP, csDamage, csIntelligence, csSP, csWeaponName, csWeaponDamage, csTotalDamage;
    public static final String DEFAULT = "N/A";
    public static final int zNum = 0;
    int level, hp, damage, intelligence, sp, weaponDamage, totalDamage;
    String name, weaponName;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charcter_selection);

        csName = (TextView)findViewById(R.id.csName);
        csLevel = (TextView)findViewById(R.id.csLevel);
        csHP = (TextView)findViewById(R.id.csHP);
        csDamage = (TextView)findViewById(R.id.csStrength);
        csIntelligence = (TextView)findViewById(R.id.csIntelligence);
        csSP = (TextView)findViewById(R.id.csSP);
        csWeaponName = (TextView)findViewById(R.id.csWeaponName);
        csWeaponDamage = (TextView)findViewById(R.id.csWeaponDamage);
        csTotalDamage = (TextView)findViewById(R.id.csTotalDamage);

        // Getting values from sharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("userStat", MODE_PRIVATE);
        name = sharedPreferences.getString("name", DEFAULT);
        level = sharedPreferences.getInt("level", zNum);
        hp = sharedPreferences.getInt("hp", zNum);
        damage = sharedPreferences.getInt("damage", zNum);
        intelligence = sharedPreferences.getInt("intelligence", zNum);
        sp = sharedPreferences.getInt("sp", zNum);
        weaponName = sharedPreferences.getString("weaponName", weaponName);
        weaponDamage = sharedPreferences.getInt("weaponDamage", weaponDamage);

        totalDamage = damage + weaponDamage;

        csName.setText(name);
        csLevel.setText(Integer.toString(level));
        csHP.setText(Integer.toString(hp));
        csDamage.setText(Integer.toString(damage));
        csIntelligence.setText(Integer.toString(intelligence));
        csSP.setText(Integer.toString(sp));
        csWeaponName.setText(weaponName);
        csWeaponDamage.setText(Integer.toString(weaponDamage));
        csTotalDamage.setText(Integer.toString(totalDamage));
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