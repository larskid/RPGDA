package no.hiof.larsgh.rpgda;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import no.hiof.larsgh.rpgda.weapon.PlayerWeapon;
import no.hiof.larsgh.rpgda.weapon.Weapon_Knife;


public class CharacterCreation extends AppCompatActivity {

    // variables
    EditText userName;
    TextView userLevel, userHP , userDamage, userIntelligence, userSP, userWeapon,userWeaponName;
    Button buttonConfirme;
    String name, weaponName;
    int level, hp, damage, intelligence, sp, weaponDamage, hpPotion;

    PlayerWeapon weapon = new PlayerWeapon();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_creation);

        //linking username to edittext
        userName = (EditText)findViewById(R.id.csName);
        userLevel = (TextView)findViewById(R.id.playerLevel);
        userHP = (TextView)findViewById(R.id.textHP);
        userDamage = (TextView)findViewById(R.id.textDamage);
        userIntelligence = (TextView)findViewById(R.id.textIntelligence);
        userSP = (TextView)findViewById(R.id.textSP);
        userWeapon = (TextView)findViewById(R.id.textWeaponDamage);
        userWeaponName = (TextView)findViewById(R.id.starterWeaponName);

        // Checking if there is text in name tag
        buttonConfirme = (Button) findViewById(R.id.buttonConfirm);
        buttonConfirme.setOnClickListener(v -> {
            name = userName.getText().toString();
            if (name.matches("")){
                Toast.makeText(getApplicationContext(), "Name is requierd", Toast.LENGTH_SHORT).show();
            }
            else {

                level = 1;
                hp = 20;
                damage = 10;
                intelligence = 5;
                sp = 0;
                hpPotion = 0;;

                weapon = new Weapon_Knife();
                weaponName = weapon.name;
                weaponDamage = weapon.damage;

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
        editor.putInt("damage", damage);
        editor.putInt("intelligence", intelligence);
        editor.putInt("sp", sp);
        editor.putInt("weaponDamage", weaponDamage);
        editor.putString("weaponName", weaponName);
        editor.putInt("hpPotion", hpPotion);
        editor.apply();
    }

    public void backMenuScreen(View view){
        Intent bMenuScreen = new Intent(this, Menu.class);
        startActivity(bMenuScreen);
    }
}