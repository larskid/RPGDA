package no.hiof.larsgh.rpgda;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;


public class GameScreen extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    TextView text, tvUserName, tvUserLevel, tvUserHP;
    Button bOption1, bOption2, bOption3, bOption4, bGameMenu;
    public static final String DEFAULT = "N/A";
    public static final int zNum = 0;
    int level, hp, mp, strength, intelligence, agility, sp;
    String name;


    Story story;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);

        story = new Story(this);

        // Getting the user name from creation class
        tvUserName = (TextView)findViewById(R.id.tvUserName);
        tvUserLevel = (TextView)findViewById(R.id.tvUserLevel);
        tvUserHP = (TextView)findViewById(R.id.tvUserHP);
        getNameLevelHP(tvUserName, tvUserLevel, tvUserHP);
        //getStat();

        //Menu button
        bGameMenu = (Button) findViewById(R.id.buttonGameMenu);

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

        bGameMenu.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(GameScreen.this, bGameMenu);
            popupMenu.setOnMenuItemClickListener(GameScreen.this);
            popupMenu.inflate(R.menu.in_game_menu);
            popupMenu.show();
        });

    }

    @SuppressLint("SetTextI18n")
    public void getNameLevelHP(TextView tvUserName, TextView tvUserLevel, TextView tvUserHP){
        SharedPreferences spName = getSharedPreferences("userStat", MODE_PRIVATE);
        String name = spName.getString("name", DEFAULT);
        int level = spName.getInt("level", zNum);
        int hp = spName.getInt("hp", zNum);

        if (name.equals(DEFAULT)){
            Toast.makeText(this, "Details not found", Toast.LENGTH_SHORT).show();
        }
        else {
            tvUserName.setText(name);
            tvUserLevel.setText("Lvl " + level);
            tvUserHP.setText("HP " + (hp));
        }
    }

    @Override
    public void onBackPressed() {
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
        Intent menuScreen = new Intent(this, Menu.class);
        startActivity(menuScreen);
    }



    public void levelUp(){
        getStat(this);
        level = level + 1;
        hp = hp + 10;
        mp = mp + 10;
        strength = strength + 1;
        intelligence = intelligence + 1;
        agility = agility + 1;
        sp = sp + 3;
        updateStat();
    }

    public void getStat(Context ctx){
        SharedPreferences getStat = ctx.getSharedPreferences("userStat", MODE_PRIVATE);
        name = getStat.getString("name",DEFAULT);
        level = getStat.getInt("level", zNum);
        hp = getStat.getInt("hp", zNum);
        mp = getStat.getInt("mp", zNum);
        strength = getStat.getInt("strength", zNum);
        intelligence = getStat.getInt("intelligence", zNum);
        agility = getStat.getInt("agility", zNum);
        sp = getStat.getInt("sp", zNum);
        updateStat();
    }

    public void updateStat(){
        SharedPreferences upSP = getSharedPreferences("userStat", 0);
        SharedPreferences.Editor editor = upSP.edit();
        editor.putInt("level", level);
        editor.putInt("hp", hp);
        editor.putInt("mp", mp);
        editor.putInt("strength", strength);
        editor.putInt("intelligence", intelligence);
        editor.putInt("agility", agility);
        editor.putInt("sp", sp);
        editor.apply();
        getNameLevelHP(tvUserName, tvUserLevel, tvUserHP);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()){
            case R.id.save:
                getStat(this);
                SharedPreferences saveStat = getSharedPreferences("userStat", MODE_PRIVATE);
                SharedPreferences.Editor editor = saveStat.edit();
                editor.putString("name", name);
                editor.putInt("level", level);
                editor.putInt("hp",hp);
                editor.putInt("mp",mp);
                editor.putInt("strength",strength);
                editor.putInt("intelligence",intelligence);
                editor.putInt("agility", agility);
                editor.putInt("sp", sp);
                editor.apply();
                return true;

            case R.id.quit:
                Intent goMenuScreen = new Intent(this, Menu.class);
                startActivity(goMenuScreen);
                return true;

            case R.id.back:
                return true;
            default:
                return false;
        }
    }
}
