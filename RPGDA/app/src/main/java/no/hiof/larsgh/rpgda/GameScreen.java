package no.hiof.larsgh.rpgda;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import no.hiof.larsgh.rpgda.monsters.FightingMonster;
import no.hiof.larsgh.rpgda.monsters.Monster_Dragon;
import no.hiof.larsgh.rpgda.monsters.Monster_GiantSnake;
import no.hiof.larsgh.rpgda.monsters.Monster_Goblin;
import no.hiof.larsgh.rpgda.monsters.Monster_Golem;
import no.hiof.larsgh.rpgda.monsters.Monster_Orc;
import no.hiof.larsgh.rpgda.monsters.Monster_Skeleton;
import no.hiof.larsgh.rpgda.monsters.Monster_Zombie;
import no.hiof.larsgh.rpgda.weapon.PlayerWeapon;
import no.hiof.larsgh.rpgda.weapon.Weapon_GreatAxe;
import no.hiof.larsgh.rpgda.weapon.Weapon_LongSword;
import no.hiof.larsgh.rpgda.weapon.Weapon_MagicSword;


public class GameScreen extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    // TextView and Buttons
    TextView text, tvUserLevel, tvUserHP, tvUserWeapon;
    Button bOption1, bOption2, bOption3, bOption4, bGameMenu, bUseSP, bUseHPPotion, bPlayerStat;
    // Button position
    String nextPosition1, nextPosition2, nextPosition3, nextPosition4;

    // Default values for SharedPreferences
    public static final String DEFAULT = "N/A";
    public static final int zNum = 0;

    // Player stat
    int level, hp, damage, intelligence, sp, weaponDamage, hpPotion;
    String name, weaponName;

    // Player achievement
    boolean cr2Door;
    boolean killGoblin;
    boolean killGiantSnake;
    boolean killGolem;
    boolean killOrc;
    boolean killSkeleton;
    boolean killZombie;
    boolean killDragon;
    boolean cr1DiscoverBody;
    boolean skeletonSneakAttack;
    boolean doorKey;

    PlayerWeapon weapon = new PlayerWeapon();
    FightingMonster monster = new FightingMonster();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);

        // Getting the user name from creation class
        tvUserLevel = findViewById(R.id.tvUserLevel);
        tvUserHP = findViewById(R.id.tvUserHP);
        tvUserWeapon = findViewById(R.id.tvUserWeapon);

        //Menu button
        bGameMenu = findViewById(R.id.buttonGameMenu);

        text = findViewById(R.id.textViewGameInfo);
        bOption1 = findViewById(R.id.buttonOption1);
        bOption2 = findViewById(R.id.buttonOption2);
        bOption3 = findViewById(R.id.buttonOption3);
        bOption4 = findViewById(R.id.buttonOption4);

        bOption1.setTransformationMethod(null);
        bOption2.setTransformationMethod(null);
        bOption3.setTransformationMethod(null);
        bOption4.setTransformationMethod(null);

        bGameMenu.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(GameScreen.this, bGameMenu);
            popupMenu.setOnMenuItemClickListener(GameScreen.this);
            popupMenu.inflate(R.menu.in_game_menu);
            popupMenu.show();
        });

        getStat();
        getNameLevelHP(tvUserLevel,tvUserHP,tvUserWeapon);
        startingPoint();

        // HP potion button
        bUseHPPotion = findViewById(R.id.buttonUseHPPotion);
        bUseHPPotion.setOnClickListener(v -> {
            if (hpPotion>=1){
                showHPPotionPopup();
            }
            else {
                Toast.makeText(getApplicationContext(),"There are no HP potion available to use.", Toast.LENGTH_SHORT).show();
            }
        });

        // SP button
        bUseSP = findViewById(R.id.buttonUseSP);
        bUseSP.setOnClickListener(v -> {
            if (sp>=1){
                showUseSPPopup();
            }
            else {
                Toast.makeText(getApplicationContext(),"There are no SP available to use.", Toast.LENGTH_SHORT).show();
            }
        });

        // Player stat button
        bPlayerStat = findViewById(R.id.buttonCharacterSheet);
        bPlayerStat.setOnClickListener(view -> {
            showPlayerStat();
        });
    }


    // Back and forward buttons
    // Popup window to add points in stats
    private PopupWindow POPUP_WINDOW_SCORE = null;
    private void showUseSPPopup(){

        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.popup_use_sp, null);

        POPUP_WINDOW_SCORE = new PopupWindow(this);
        POPUP_WINDOW_SCORE.setContentView(layout);
        POPUP_WINDOW_SCORE.setWidth(width);
        POPUP_WINDOW_SCORE.setHeight(height);
        POPUP_WINDOW_SCORE.setFocusable(true);
        POPUP_WINDOW_SCORE.setBackgroundDrawable(null);
        POPUP_WINDOW_SCORE.showAtLocation(layout, Gravity.CENTER, 1, 1);

        TextView tvUseSPInfo = layout.findViewById(R.id.popupUseSPInfoTV);
        TextView tvUseSPCount = layout.findViewById(R.id.popupUseSPCountTV);
        TextView tvUseSPDamage = layout.findViewById(R.id.popupUseSPDamageTV);
        TextView tvUseSPIntelligence = layout.findViewById(R.id.popupUseSPIntelligenceTV);

        tvUseSPInfo.setText("Here you press the button for the attribute you want to increase.");
        tvUseSPCount.setText("Current SP: " + sp);
        tvUseSPDamage.setText("Damage: " + damage);
        tvUseSPIntelligence.setText("Intelligence: " + intelligence);

        Button bUseSPDamage = layout.findViewById(R.id.popupButtonDamage);
        bUseSPDamage.setOnClickListener(v -> {
            if (sp > 0){
                damage = damage + 1;
                sp = sp - 1;
                tvUseSPCount.setText("Current SP: " + sp);
                tvUseSPDamage.setText("Damage: " + damage);
                SharedPreferences upSP = getSharedPreferences("userStat", 0);
                SharedPreferences.Editor editor = upSP.edit();
                editor.putInt("damage", damage);
                editor.putInt("sp", sp);
                editor.apply();
                getStat();
            }
            else {
                Toast.makeText(getApplicationContext(), "There are no more SP to use", Toast.LENGTH_SHORT).show();
            }
        });

        Button bUseSPIntelligence = layout.findViewById(R.id.popupButtonIntelligence);
        bUseSPIntelligence.setOnClickListener(v -> {
            if (sp > 0){
                intelligence = intelligence + 1;
                sp = sp - 1;
                tvUseSPCount.setText("Current SP: " + sp);
                tvUseSPIntelligence.setText("Intelligence: " + intelligence);
                SharedPreferences upSP = getSharedPreferences("userStat", 0);
                SharedPreferences.Editor editor = upSP.edit();
                editor.putInt("intelligence", intelligence);
                editor.putInt("sp", sp);
                editor.apply();
                getStat();
            }
            else {
                Toast.makeText(getApplicationContext(), "There are no more SP to use", Toast.LENGTH_SHORT).show();
            }
        });

        Button bUseSPBack = layout.findViewById(R.id.popupUseSPBack);
        bUseSPBack.setOnClickListener(v -> POPUP_WINDOW_SCORE.dismiss());
    }
    // Popup window to add points in stats - END

    // Create a popup window to use HP potion
    private void showHPPotionPopup(){

        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.popup_use_hp_potion, null);

        POPUP_WINDOW_SCORE = new PopupWindow(this);
        POPUP_WINDOW_SCORE.setContentView(layout);
        POPUP_WINDOW_SCORE.setWidth(width);
        POPUP_WINDOW_SCORE.setHeight(height);
        POPUP_WINDOW_SCORE.setFocusable(true);
        POPUP_WINDOW_SCORE.setBackgroundDrawable(null);
        POPUP_WINDOW_SCORE.showAtLocation(layout, Gravity.CENTER, 1, 1);

        TextView tvHPPotionInfo = layout.findViewById(R.id.tvPopupHPPotionInfo);
        TextView tvCurrentHPPotion = layout.findViewById(R.id.tvPopupCurrentHPPotion);
        TextView tvCurrentUserHP = layout.findViewById(R.id.tvPopupCurrentHP);
        tvHPPotionInfo.setText("Each potion will give you 20 HP.");
        tvCurrentHPPotion.setText("HP-potion(s) count: " + hpPotion);
        tvCurrentUserHP.setText("Current HP: " + hp);

        Button bUseHPPotion = (Button) layout.findViewById(R.id.buttonPopupHPPotionUse);
        bUseHPPotion.setOnClickListener(v -> {
            if (hpPotion > 0) {
                hp = hp + 20;
                hpPotion = hpPotion - 1;
                tvCurrentHPPotion.setText("HP-potion(s) count: " + hpPotion);
                tvCurrentUserHP.setText("Current HP: " + hp);
                SharedPreferences upSP = getSharedPreferences("userStat", 0);
                SharedPreferences.Editor editor = upSP.edit();
                editor.putInt("hp", hp);
                editor.putInt("hpPotion", hpPotion);
                editor.apply();
                getStat();
            }
            else {
                Toast.makeText(getApplicationContext(), "There are no more HP-potions to use", Toast.LENGTH_SHORT).show();
            }
        });

        Button bHPPotionBack = layout.findViewById(R.id.buttonPopupHPPotionBack);
        bHPPotionBack.setOnClickListener(v -> POPUP_WINDOW_SCORE.dismiss());
    }
    // Create a popup window to use HP potion - END

    // Popup window to show player stat
    private void showPlayerStat(){
        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.popup_player_stat, null);

        POPUP_WINDOW_SCORE = new PopupWindow(this);
        POPUP_WINDOW_SCORE.setContentView(layout);
        POPUP_WINDOW_SCORE.setWidth(width);
        POPUP_WINDOW_SCORE.setHeight(height);
        POPUP_WINDOW_SCORE.setFocusable(true);
        POPUP_WINDOW_SCORE.setBackgroundDrawable(null);
        POPUP_WINDOW_SCORE.showAtLocation(layout, Gravity.CENTER, 1, 1);

        TextView tvPlayerStatLevel = layout.findViewById(R.id.cssLevel);
        TextView tvPlayerStatName = layout.findViewById(R.id.cssName);
        TextView tvPlayerStatWeaponName = layout.findViewById(R.id.cssWeaponName);
        TextView tvPlayerStatWeaponDamage = layout.findViewById(R.id.cssWeaponDamage);
        TextView tvPlayerStatHP = layout.findViewById(R.id.cssHP);
        TextView tvPlayerStatDamage = layout.findViewById(R.id.cssDamage);
        TextView tvPlayerStatIntelligence = layout.findViewById(R.id.cssIntelligence);
        TextView tvPlayerStatSP = layout.findViewById(R.id.cssSP);
        TextView tvPlayerStatTotalDamage = layout.findViewById(R.id.cssTotalDamage);

        int totalDamage = damage + weaponDamage;

        tvPlayerStatLevel.setText(Integer.toString(level));
        tvPlayerStatName.setText(name);
        tvPlayerStatWeaponName.setText(weaponName);
        tvPlayerStatWeaponDamage.setText(Integer.toString(weaponDamage));
        tvPlayerStatHP.setText(Integer.toString(hp));
        tvPlayerStatDamage.setText(Integer.toString(damage));
        tvPlayerStatIntelligence.setText(Integer.toString(intelligence));
        tvPlayerStatSP.setText(Integer.toString(sp));
        tvPlayerStatTotalDamage.setText(Integer.toString(totalDamage));

        Button bPlayerStatBack = layout.findViewById(R.id.popupBackButtonPlayerStat);
        bPlayerStatBack.setOnClickListener(view -> POPUP_WINDOW_SCORE.dismiss());

    }
    // Popup window to show player stat - END

    // To make it so you cant press on the back button
    @Override
    public void onBackPressed() {
    }

    // Open in game menu
    public void goMenuScreen(){
        Intent menuScreen = new Intent(this, Menu.class);
        startActivity(menuScreen);
    }
    // Back and forward buttons - END

    // Update current weapon
    public void weaponUpdate(){
        weaponName = weapon.name;
        weaponDamage = weapon.damage;
        updateStat();
    }
    // Update current weapon - END

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()){
            case R.id.save:
                getStat();
                updateStat();
                return true;

            case R.id.quit:
                Intent goMenuScreen = new Intent(this, Menu.class);
                startActivity(goMenuScreen);
                return true;
            default:
                return false;
        }
    }

    @SuppressLint("SetTextI18n")
    public void getNameLevelHP(TextView tvUserLevel, TextView tvUserHP, TextView tvUserWeapon){
        SharedPreferences spName = getSharedPreferences("userStat", MODE_PRIVATE);
        String name = spName.getString("name", DEFAULT);
        int level = spName.getInt("level", zNum);
        int hp = spName.getInt("hp", zNum);
        String weaponName = spName.getString("weaponName", DEFAULT);

        if (name.equals(DEFAULT)){
            Toast.makeText(this, "Details not found", Toast.LENGTH_SHORT).show();
        }
        else {
            //tvUserName.setText(name);
            tvUserLevel.setText("Lvl: " + level);
            tvUserHP.setText("HP: " + hp);
            tvUserWeapon.setText(weaponName);
        }
    }

    // Player character level up
    public void levelUp(){
        level = level + 1;
        hp = hp + 20;
        damage = damage + 2;
        intelligence = intelligence + 1;
        sp = sp + 3;
        updateStat();
    }
    // Player character level up - END

    // Get player status form SharedPreferences
    public void getStat(){
        SharedPreferences getStat = getSharedPreferences("userStat", MODE_PRIVATE);
        name = getStat.getString("name",DEFAULT);
        level = getStat.getInt("level", zNum);
        hp = getStat.getInt("hp", zNum);
        damage = getStat.getInt("damage", zNum);
        intelligence = getStat.getInt("intelligence", zNum);
        sp = getStat.getInt("sp", zNum);
        weaponName = getStat.getString("weaponName", DEFAULT);
        weaponDamage = getStat.getInt("weaponDamage", zNum);
        hpPotion = getStat.getInt("hpPotion", zNum);
        //maxHP = getStat.getInt("maxHP", zNum);

        // Get achievement
        cr2Door = getStat.getBoolean("cr2Door",false);
        killGoblin  = getStat.getBoolean("killGoblin ",false);
        killGiantSnake  = getStat.getBoolean("killGiantSnake ",false);
        killGolem  = getStat.getBoolean("killGolem ",false);
        killOrc  = getStat.getBoolean("killOrc ",false);
        killSkeleton  = getStat.getBoolean("killSkeleton ",false);
        killZombie  = getStat.getBoolean("killZombie ",false);
        killDragon  = getStat.getBoolean("killDragon ",false);
        cr1DiscoverBody  = getStat.getBoolean("cr1DiscoverBody ",false);
        skeletonSneakAttack  = getStat.getBoolean("skeletonSneakAttack ",false);
        doorKey = getStat.getBoolean("doorKey", false);

        getNameLevelHP(tvUserLevel, tvUserHP, tvUserWeapon);
    }
    // Get player status form SharedPreferences - END

    // Updates player status in SharedPreferences
    public void updateStat(){
        SharedPreferences upSP = getSharedPreferences("userStat", 0);
        SharedPreferences.Editor editor = upSP.edit();
        // Players stat
        editor.putInt("level", level);
        editor.putInt("hp", hp);
        editor.putInt("damage", damage);
        editor.putInt("intelligence", intelligence);
        editor.putInt("sp", sp);
        editor.putInt("weaponDamage", weaponDamage);
        editor.putString("weaponName", weaponName);
        editor.putInt("hpPotion", hpPotion);
        //editor.putInt("maxHP", maxHP);
        // Players achievement
        editor.putBoolean("cr2Door", cr2Door);
        editor.putBoolean("killGoblin", killGoblin);
        editor.putBoolean("killGiantSnake", killGiantSnake);
        editor.putBoolean("killGolem", killGolem);
        editor.putBoolean("killOrc", killOrc);
        editor.putBoolean("killSkeleton", killSkeleton);
        editor.putBoolean("killZombie", killZombie);
        editor.putBoolean("killDragon", killDragon);
        editor.putBoolean("cr1DiscoverBody", cr1DiscoverBody);
        editor.putBoolean("skeletonSneakAttack", skeletonSneakAttack);
        editor.putBoolean("doorKey", doorKey);
        editor.commit();

        getNameLevelHP(tvUserLevel, tvUserHP, tvUserWeapon);
    }
    // Updates player status in SharedPreferences - END

    // When dead delete character
    public void characterDelete(){
        SharedPreferences spDelete = getSharedPreferences("userStat", MODE_PRIVATE);
        SharedPreferences.Editor editor = spDelete.edit();

        editor.clear();
        editor.commit();
    }
    // When dead delete character - END

    // Story position buttons
    public void button1(View view){
        selectPosition(nextPosition1);
    }
    public void button2(View view){
        selectPosition(nextPosition2);
    }
    public void button3(View view){
        selectPosition(nextPosition3);
    }
    public void button4(View view){
        selectPosition(nextPosition4);
    }
    // Story position buttons - END

    // Game story
    public void selectPosition(String position){
        switch (position){
            case "startingPoint": startingPoint(); break;
            case "firstCrossroad": firstCrossroad(); break;
            case "cr1Sign": cr1Sign(); break;
            case "cr1Right": cr1Right(); break;
            case "cr1SearchCorpse": cr1SearchCorpse(); break;
            case "cr1Forward": cr1Forward(); break;
            case "cr1fightGoblin": cr1fightGoblin(); break;
            case "cr1Left": cr1Left(); break;
            case "cr1FightGiantSnake": cr1FightGiantSnake(); break;
            case "goMenuScreen": goMenuScreen(); break;
            case "dead": dead(); break;
            case "playerAttack": playerAttack(); break;
            case "monsterAttack": monsterAttack(); break;
            case "win": win(); break;
            case "ending": ending(); break;
            case "secondCrossroad": secondCrossroad(); break;
            case "cr2Left": cr2Left(); break;
            case "cr2Door": cr2Door(); break;
            case "cr2Explore": cr2Explore(); break;
            case "cr2Right": cr2Right(); break;
            case "cr2FightZombie": cr2FightZombie(); break;
            case "cr2Forward": cr2Forward(); break;
            case "cr2FightSkeleton": cr2FightSkeleton(); break;
            case "thirdCrossroad": thirdCrossroad(); break;
            case "cr3Left": cr3Left(); break;
            case "cr3FightOrc": cr3FightOrc(); break;
            case "cr3Forward": cr3Forward(); break;
            case "cr3Door": cr3Door(); break;
            case "cr3SecretRoom": cr3SecretRoom(); break;
            case "cr3DoorTrap": cr3DoorTrap(); break;
            case "cr3Right": cr3Right(); break;
            case "cr3FightGolem": cr3FightGolem(); break;
            case "bossArea": bossArea(); break;
            case "bossAreaHealing": bossAreaHealing(); break;
            case "bossAreaImage": bossAreaImage(); break;
            case "bossAreaDoor": bossAreaDoor(); break;
            case "bossFightDragon": bossFightDragon(); break;
        }
    }

    // First crossroad
    public void startingPoint(){
        text.setText("You're on your way into an unexplored dungeon with only a knife as your companion.\n" +
                "Is it wealth and fame you find or a cruel death!");

        bOption1.setText("Begin adventure");
        bOption2.setText("");
        bOption3.setText("");
        bOption4.setText("");

        showOneButton();

        nextPosition1 = "firstCrossroad";
        nextPosition2 = "";
        nextPosition3 = "";
        nextPosition4 = "";
    }

    public void firstCrossroad(){
        text.setText("After walking a while you come to a crossroad.\n" +
                "On the wall you see a sign.\n\nWhat will you do?");

        bOption1.setText("Go left");
        bOption2.setText("Go right");
        bOption3.setText("Go forward");
        bOption4.setText("Read the sign");

        showAllButtons();

        nextPosition1 = "cr1Left";
        nextPosition2 = "cr1Right";
        nextPosition3 = "cr1Forward";
        nextPosition4 = "cr1Sign";
    }

    public void cr1Sign(){
        text.setText("The sign says: \n\n There's a dangerous monster on the left!");

        bOption1.setText("Back");
        bOption2.setText("");
        bOption3.setText("");
        bOption4.setText("");

        showOneButton();

        nextPosition1 = "firstCrossroad";
        nextPosition2 = "";
        nextPosition3 = "";
        nextPosition4 = "";
    }

    public void cr1Right(){
        if (!cr1DiscoverBody){
            text.setText("You discover an old adventure corpse.");

            cr1DiscoverBody = true;
            updateStat();
            bOption1.setText("Search the corpse");
            bOption2.setText("Back");
            bOption3.setText("");
            bOption4.setText("");

            showTwoButtons();

            nextPosition1 = "cr1SearchCorpse";
            nextPosition2 = "firstCrossroad";
            nextPosition3 = "";
            nextPosition4 = "";
        }
        else {
            text.setText("You discover an old adventure corpse. But there are no item's here.");

            bOption1.setText("Back");
            bOption2.setText("");
            bOption3.setText("");
            bOption4.setText("");

            showTwoButtons();

            nextPosition1 = "firstCrossroad";
            nextPosition2 = "";
            nextPosition3 = "";
            nextPosition4 = "";
        }
    }

    public void cr1SearchCorpse(){
        text.setText("After searching the old corpse you found a sword!\n\n(You have a long sword)");

        // Need weapon long sword
        weapon = new Weapon_LongSword();
        weaponUpdate();
        cr1DiscoverBody = true;
        updateStat();

        bOption1.setText("Back");
        bOption2.setText("");
        bOption3.setText("");
        bOption4.setText("");

        showOneButton();

        nextPosition1 = "firstCrossroad";
        nextPosition2 = "";
        nextPosition3 = "";
        nextPosition4 = "";
    }

    public void cr1Forward(){
        if (!killGoblin){
            monster = new Monster_Goblin();
            text.setText("You walk forward and meet and monster!\nThe monster is a " + monster.name + " that looks weak.\n\nWhat do you do?");

            bOption1.setText("Fight");
            bOption2.setText("Run");
            bOption3.setText("");
            bOption4.setText("");

            showTwoButtons();

            nextPosition1 = "cr1fightGoblin";
            nextPosition2 = "firstCrossroad";
            nextPosition3 = "";
            nextPosition4 = "";
        }
        else {
            text.setText("You find nothing more in this passage. You go back to the crossroad.");

            bOption1.setText(">");
            bOption2.setText("");
            bOption3.setText("");
            bOption4.setText("");

            showOneButton();

            nextPosition1 = "firstCrossroad";
            nextPosition2 = "";
            nextPosition3 = "";
            nextPosition4 = "";
        }
    }

    public void cr1fightGoblin(){

        text.setText(monster.name + ": " + monster.hp + " HP\n\n What do you do?");

        bOption1.setText("Attack");
        bOption2.setText("Run");
        bOption3.setText("");
        bOption4.setText("");

        showTwoButtons();

        nextPosition1 = "playerAttack";
        nextPosition2 = "firstCrossroad";
        nextPosition3 = "";
        nextPosition4 = "";
    }

    public void cr1Left(){
        if (!killGiantSnake){
            monster = new Monster_GiantSnake();

            text.setText("You chose the road on the left and encounter a giant snake.\n\nWhat do you do?");

            bOption1.setText("Fight");
            bOption2.setText("Run");
            bOption3.setText("");
            bOption4.setText("");
            showTwoButtons();

            nextPosition1 = "cr1FightGiantSnake";
            nextPosition2 = "firstCrossroad";
            nextPosition3 = "";
            nextPosition4 = "";
        }

        else {
            text.setText("Only a corpse of a giant snake is left, but you do not find anything interesting here and continue the exploration.");

            bOption1.setText("Continue");
            bOption2.setText("Back");
            bOption3.setText("");
            bOption4.setText("");

            showTwoButtons();

            nextPosition1 = "secondCrossroad";
            nextPosition2 = "firstCrossroad";
            nextPosition3 = "";
            nextPosition4 = "";
        }
    }

    public void cr1FightGiantSnake(){
        text.setText(monster.name + ": " + monster.hp + "\n\n What do you do?");

        bOption1.setText("Attack");
        bOption2.setText("Run");
        bOption3.setText("");
        bOption4.setText("");

        showTwoButtons();

        nextPosition1 = "playerAttack";
        nextPosition2 = "firstCrossroad";
        nextPosition3 = "";
        nextPosition4 = "";
    }
    // First crossroad - END

    // Second crossroad
    public void secondCrossroad(){
        text.setText("You have continue to move further in the dungeon and you come to the second junction.\n\nWhat do you do?");

        bOption1.setText("Go left");
        bOption2.setText("Go Right");
        bOption3.setText("Go forward");
        bOption4.setText("Go back");

        showAllButtons();

        nextPosition1 = "cr2Left";
        nextPosition2 = "cr2Right";
        nextPosition3 = "cr2Forward";
        nextPosition4 = "cr1Left";
    }

    public void cr2Left(){
        if (!doorKey){
            text.setText("You find a door and tries to open it. You try to open it but it is locked.");

            bOption1.setText("Back");
            bOption2.setText("");
            bOption3.setText("");
            bOption4.setText("");
            showOneButton();

            nextPosition1 = "secondCrossroad";
            nextPosition2 = "";
            nextPosition3 = "";
            nextPosition4 = "";
        }
        else {
            text.setText("You find a door and tries to open it. You try to open it but it is locked. \n But wait you have a key. Do you want to open the door?");

            showTwoButtons();
            bOption1.setText("Open the door");
            bOption2.setText("Back");
            bOption3.setText("");
            bOption4.setText("");

            nextPosition1 = "cr2Door";
            nextPosition2 = "secondCrossroad";
            nextPosition3 = "";
            nextPosition4 = "";
        }
    }

    public void cr2Door(){
        if (!cr2Door){
            text.setText("You open the door and enter a small room.\n Inside the room you find chest. You open the chest and finds a Great axe and 2 HP-potions!");

            weapon = new Weapon_GreatAxe();
            hpPotion = hpPotion + 2;
            doorKey = true;
            cr2Door = true;
            weaponUpdate();

            bOption1.setText("Explore the area");
            bOption2.setText("Back");
            bOption3.setText("");
            bOption4.setText("");
            showTwoButtons();

            nextPosition1 = "cr2Explore";
            nextPosition2 = "secondCrossroad";
            nextPosition3 = "";
            nextPosition4 = "";
        }
        else {
            text.setText("You open the door and enter a small room.\nThere is nothing inside.");

            bOption1.setText("Explore the area");
            bOption2.setText("Back");
            bOption3.setText("");
            bOption4.setText("");
            showTwoButtons();

            nextPosition1 = "cr2Explore";
            nextPosition2 = "secondCrossroad";
            nextPosition3 = "";
            nextPosition4 = "";
        }
    }

    public void cr2Explore(){
        if (intelligence>7){
            text.setText("You explored the area and find a strange kode hidden in the wall.\n\nThe kode is: 1585");

            bOption1.setText(">");
            bOption2.setText("");
            bOption3.setText("");
            bOption4.setText("");

            showOneButton();

            nextPosition1 = "secondCrossroad";
            nextPosition2 = "";
            nextPosition3 = "";
            nextPosition4 = "";
        }
        else if (intelligence<=7){
            text.setText("You explored the area but you don't find anything.");

            bOption1.setText(">");
            bOption2.setText("");
            bOption3.setText("");
            bOption4.setText("");

            showOneButton();

            nextPosition1 = "secondCrossroad";
            nextPosition2 = "";
            nextPosition3 = "";
            nextPosition4 = "";
        }
    }

    public void cr2Right(){
        if (!killZombie){
            monster = new Monster_Zombie();
            text.setText("You find yourself facing a " + monster.name + "!\nWhat do you do?");

            bOption1.setText("Attack");
            bOption2.setText("Run");
            bOption3.setText("");
            bOption4.setText("");
            showTwoButtons();

            nextPosition1 = "cr2FightZombie";
            nextPosition2 = "secondCrossroad";
            nextPosition3 = "";
            nextPosition4 = "";
        }
        else{
            text.setText("You find yourself facing a dead end.");

            bOption1.setText("Back");
            bOption2.setText("");
            bOption3.setText("");
            bOption4.setText("");
            showOneButton();

            nextPosition1 = "secondCrossroad";
            nextPosition2 = "";
            nextPosition3 = "";
            nextPosition4 = "";
        }
    }

    public void cr2FightZombie(){
        text.setText(monster.name + ": " + monster.hp + "\n\n What do you do?");

        bOption1.setText("Attack");
        bOption2.setText("Run");
        bOption3.setText("");
        bOption4.setText("");
        showTwoButtons();

        nextPosition1 = "playerAttack";
        nextPosition2 = "secondCrossroad";
        nextPosition3 = "";
        nextPosition4 = "";
    }

    public void cr2Forward(){
        if (!killSkeleton){
            monster = new Monster_Skeleton();
            if (intelligence <=9){
                text.setText("A monster sneaks up on you and attacks!");

                skeletonSneakAttack = true;
                updateStat();

                bOption1.setText(">");
                bOption2.setText("");
                bOption3.setText("");
                bOption4.setText("");
                showOneButton();

                nextPosition1 = "cr2FightSkeleton";
                nextPosition2 = "";
                nextPosition3 = "";
                nextPosition4 = "";
            }
            else if (intelligence>9){
                text.setText("You discover that a monster is sneaking up on you and attacking but you barely escape!\nYou calm down and see that the monster is a " + monster.name + "!\n\nWhat do you do?");

                bOption1.setText("Fight");
                bOption2.setText("Run");
                bOption3.setText("");
                bOption4.setText("");
                showTwoButtons();

                nextPosition1 = "cr2FightSkeleton";
                nextPosition2 = "secondCrossroad";
                nextPosition3 = "";
                nextPosition4 = "";
            }
        }
        else{
            text.setText("You find a way further into the dungeon. What will you do?");

            bOption1.setText("Continue");
            bOption2.setText("Back");
            bOption3.setText("");
            bOption4.setText("");
            showTwoButtons();

            nextPosition1 = "thirdCrossroad";
            nextPosition2 = "secondCrossroad";
            nextPosition3 = "";
            nextPosition4 = "";
        }
    }

    public void cr2FightSkeleton(){
        if (skeletonSneakAttack){
            text.setText(monster.name + ": " + monster.hp + "\n\n What do you do?");
            bOption1.setText(">");
            bOption2.setText("");
            bOption3.setText("");
            bOption4.setText("");
            showOneButton();

            nextPosition1 = "monsterAttack";
            nextPosition2 = "";
            nextPosition3 = "";
            nextPosition4 = "";
        }
        else{
            text.setText(monster.name + ": " + monster.hp + "\n\n What do you do?");
            bOption1.setText("Attack");
            bOption2.setText("Run");
            bOption3.setText("");
            bOption4.setText("");
            showTwoButtons();

            nextPosition1 = "playerAttack";
            nextPosition2 = "secondCrossroad";
            nextPosition3 = "";
            nextPosition4 = "";
        }
    }
    // Second crossroad - END

    // Third crossroad
    public void thirdCrossroad(){
        text.setText("You have continue to move further in the dungeon and you come to the third junction.\n\nWhat do you do?");

        showAllButtons();
        bOption1.setText("Go left");
        bOption2.setText("Go Right");
        bOption3.setText("Go forward");
        bOption4.setText("Go back");

        nextPosition1 = "cr3Left";
        nextPosition2 = "cr3Right";
        nextPosition3 = "cr3Forward";
        nextPosition4 = "cr2Forward";
    }

    public void cr3Left(){
        if (!killOrc){
            monster = new Monster_Orc();
            text.setText("You took a left turn and enter a big room. On the other side of the room you see a big " + monster.name + "!\n\nWhat do you do?");

            showTwoButtons();
            bOption1.setText("Fight");
            bOption2.setText("Run");
            bOption3.setText("");
            bOption4.setText("");

            nextPosition1 = "cr3FightOrc";
            nextPosition2 = "thirdCrossroad";
            nextPosition3 = "";
            nextPosition4 = "";
        }
        else{
            text.setText("You are in a big empty rom. There is nothing of interest her.");

            showOneButton();
            bOption1.setText("Back");
            bOption2.setText("");
            bOption3.setText("");
            bOption4.setText("");

            nextPosition1 = "thirdCrossroad";
            nextPosition2 = "";
            nextPosition3 = "";
            nextPosition4 = "";
        }
    }

    public void cr3FightOrc(){
        text.setText(monster.name + ": " + monster.hp + "\n\n What do you do?");
        bOption1.setText("Attack");
        bOption2.setText("Run");
        bOption3.setText("");
        bOption4.setText("");
        showTwoButtons();

        nextPosition1 = "playerAttack";
        nextPosition2 = "thirdCrossroad";
        nextPosition3 = "";
        nextPosition4 = "";
    }

    public void cr3Forward(){
        text.setText("You walk forward and stumble on a big door made of iron. You observe and open the door and see that the door don't move. After exploring some more you find 3 switches, each with 4 numbers, beside the door.\n\nWhat do you do?");
        showTwoButtons();
        bOption1.setText("Enter a number");
        bOption2.setText("Back");
        bOption3.setText("");
        bOption4.setText("");

        nextPosition1 = "cr3Door";
        nextPosition2 = "thirdCrossroad";
        nextPosition3 = "";
        nextPosition4 = "";
    }

    public void cr3Door(){
        text.setText("On the switches you see the numbers:\n\n8513\n5719\n1585\n\nWhat do you do?");

        showAllButtons();
        bOption1.setText("8513");
        bOption2.setText("5719");
        bOption3.setText("1585");
        bOption4.setText("Back");

        nextPosition1 = "cr3DoorTrap";
        nextPosition2 = "cr3DoorTrap";
        nextPosition3 = "cr3SecretRom";
        nextPosition4 = "cr3Forward";
    }

    public void cr3SecretRoom(){
        text.setText("You managed to open the iron door. You enter the room. Inside the room you see an altar in the middle of the room, up on the altar you see a sword from which comes a blue light that lights up the room.\n\n (You found a Magic Sword!)");

        weapon = new Weapon_MagicSword();
        weaponUpdate();

        showOneButton();
        bOption1.setText("Back");
        bOption2.setText("");
        bOption3.setText("");
        bOption4.setText("");

        nextPosition1 = "thirdCrossroad";
        nextPosition2 = "";
        nextPosition3 = "";
        nextPosition4 = "";
    }

    public void cr3DoorTrap(){
        text.setText("The ground start to shake under your feet and in the next moment the ground disappears you you fall to your death!");

        showOneButton();
        bOption1.setText(">");
        bOption2.setText("");
        bOption3.setText("");
        bOption4.setText("");

        nextPosition1 = "dead";
        nextPosition2 = "";
        nextPosition3 = "";
        nextPosition4 = "";
    }

    public void cr3Right(){
        if (!killGolem){
            monster = new Monster_Golem();
            text.setText("You enter a path that gets bigger and bigger the longer you walk. After a short while you see a boulder in the path ahead. The boulder become bigger and bigger as you approach. \nWhen you got closer to th boulder it started to move.");

            showTwoButtons();
            bOption1.setText("Fight");
            bOption2.setText("Run");
            bOption3.setText("");
            bOption4.setText("");

            nextPosition1 = "cr3FightGolem";
            nextPosition2 = "thirdCrossroad";
            nextPosition3 = "";
            nextPosition4 = "";
        }
        else{
            text.setText("You find yourself in a big path.");

            showTwoButtons();
            bOption1.setText("Continue");
            bOption2.setText("Back");
            bOption3.setText("");
            bOption4.setText("");

            nextPosition1 = "bossArea";
            nextPosition2 = "thirdCrossroad";
            nextPosition3 = "";
            nextPosition4 = "";
        }
    }

    public void cr3FightGolem(){
        text.setText(monster.name + ": " + monster.hp + "\n\n What do you do?");
        bOption1.setText("Attack");
        bOption2.setText("Run");
        bOption3.setText("");
        bOption4.setText("");
        showTwoButtons();

        nextPosition1 = "playerAttack";
        nextPosition2 = "thirdCrossroad";
        nextPosition3 = "";
        nextPosition4 = "";
    }
    // Third crossroad - END

    // Boss
    public void bossArea(){
        text.setText("You enter a big area. To your left you see a healing fountain, to your right you see an image on the stone wall and in front of you you see a big door. The door stands 10 meter high and 4 meter width.\n\nWhat do you do?");
        bOption1.setText("Healing fountain");
        bOption2.setText("Look at wall image");
        bOption3.setText("Enter the door");
        bOption4.setText("Back");
        showAllButtons();

        nextPosition1 = "bossAreaHealing";
        nextPosition2 = "bossAreaImage";
        nextPosition3 = "bossAreaDoor";
        nextPosition4 = "thirdCrossroad";
    }

    public void bossAreaHealing(){
        hp = hp + 60;
        text.setText("The Healing fountain healed you for 60 HP!");
        updateStat();

        bOption1.setText("Back");
        bOption2.setText("");
        bOption3.setText("");
        bOption4.setText("");
        showOneButton();

        nextPosition1 = "bossArea";
        nextPosition2 = "";
        nextPosition3 = "";
        nextPosition4 = "";
    }

    public void bossAreaImage(){
        text.setText("You look at the image on the wall trying to find it's secrets, but you don't see anything important.");

        bOption1.setText("Back");
        bOption2.setText("");
        bOption3.setText("");
        bOption4.setText("");
        showOneButton();

        nextPosition1 = "bossArea";
        nextPosition2 = "";
        nextPosition3 = "";
        nextPosition4 = "";
    }

    public void bossAreaDoor(){
        monster = new Monster_Dragon();
        text.setText("You use your strength to push the big door. After pushing for 30 minutes, the gap in the door finally became large enough for you to get in.\nInside you see a large oval room that is 100 meters wide and high. In the middle of this room you see a huge and scary figure. \n\nIT IS A " + monster.name + "!");

        bOption1.setText("Fight");
        bOption2.setText("Run");
        bOption3.setText("");
        bOption4.setText("");
        showTwoButtons();

        nextPosition1 = "bossFightDragon";
        nextPosition2 = "bossArea";
        nextPosition3 = "";
        nextPosition4 = "";
    }

    public void bossFightDragon(){
        if (!killDragon){
            text.setText(monster.name + ": " + monster.hp + "\n\n What do you do?");
            bOption1.setText("Attack");
            bOption2.setText("Run");
            bOption3.setText("");
            bOption4.setText("");
            showTwoButtons();

            nextPosition1 = "playerAttack";
            nextPosition2 = "bossArea";
            nextPosition3 = "";
            nextPosition4 = "";
        }
        else{
            ending();
        }
    }
    // Boss - END

    // Fighting scene
    public void playerAttack(){
        int playerDamage = damage + weapon.damage;
        playerDamage = new java.util.Random().nextInt(playerDamage);

        monster.hp = monster.hp - playerDamage;

        text.setText("You attacked the " + monster.name + " and gave " + playerDamage + " damage!\n\n"+ monster.name + " HP: " + monster.hp);

        bOption1.setText(">");
        bOption2.setText("");
        bOption3.setText("");
        bOption4.setText("");
        showOneButton();

        if(monster.hp>0){
            nextPosition1 = "monsterAttack";
            nextPosition2 = "";
            nextPosition3 = "";
            nextPosition4 = "";
        }
        else if (monster.hp<1){
            nextPosition1 = "win";
            nextPosition2 = "";
            nextPosition3 = "";
            nextPosition4 = "";
        }
    }

    public void monsterAttack(){
        int monsterDamage = new java.util.Random().nextInt(monster.attack);
        hp = hp - monsterDamage;
        tvUserHP.setText("HP: " + hp);
        text.setText("The " + monster.name + " did " + monsterDamage + " damage!");
        updateStat();

        bOption1.setText(">");
        bOption2.setText("");
        bOption3.setText("");
        bOption4.setText("");
        showOneButton();

        if (hp>0){
            switch (monster.name) {
                case "Goblin":
                    nextPosition1 = "cr1fightGoblin";
                    nextPosition2 = "";
                    nextPosition3 = "";
                    nextPosition4 = "";
                    break;
                case "Giant snake":
                    nextPosition1 = "cr1FightGiantSnake";
                    nextPosition2 = "";
                    nextPosition3 = "";
                    nextPosition4 = "";
                    break;
                case "Zombie":
                    nextPosition1 = "cr2FightZombie";
                    nextPosition2 = "";
                    nextPosition3 = "";
                    nextPosition4 = "";
                    break;
                case "Skeleton":
                    nextPosition1 = "cr2FightSkeleton";
                    nextPosition2 = "";
                    nextPosition3 = "";
                    nextPosition4 = "";
                    break;
                case "Orc":
                    nextPosition1 = "cr3FightOrc";
                    nextPosition2 = "";
                    nextPosition3 = "";
                    nextPosition4 = "";
                    break;
                case "Golem":
                    nextPosition1 = "cr3FightGolem";
                    nextPosition2 = "";
                    nextPosition3 = "";
                    nextPosition4 = "";
                    break;
                case "Dragon":
                    nextPosition1 = "bossFightDragon";
                    nextPosition2 = "";
                    nextPosition3 = "";
                    nextPosition4 = "";
                    break;
            }
        }
        else if(hp<1){
            nextPosition1 = "dead";
            nextPosition2 = "";
            nextPosition3 = "";
            nextPosition4 = "";
        }
    }

    public void win(){
        text.setText("You defeated the " + monster.name + "!\nThe monster dropped a potion!\n\n(You obtained a HP potion!)");

        hpPotion = hpPotion + 1;
        levelUp();

        showOneButton();
        bOption1.setText(">");
        bOption2.setText("");
        bOption3.setText("");
        bOption4.setText("");

        switch (monster.name) {
            case "Goblin":
                killGoblin = true;
                updateStat();
                nextPosition1 = "cr1Forward";
                nextPosition2 = "";
                nextPosition3 = "";
                nextPosition4 = "";
                break;
            case "Giant snake":
                killGiantSnake = true;
                updateStat();
                nextPosition1 = "cr1Left";
                nextPosition2 = "";
                nextPosition3 = "";
                nextPosition4 = "";
                break;
            case "Zombie":
                text.setText("You defeated the " + monster.name + "!\nThe monster dropped a potion and a door key!\n\n(You obtained a HP potion and door key!)");
                killZombie = true;
                doorKey = true;
                updateStat();
                nextPosition1 = "secondCrossroad";
                nextPosition2 = "";
                nextPosition3 = "";
                nextPosition4 = "";
                break;
            case "Skeleton":
                killSkeleton = true;
                updateStat();
                nextPosition1 = "cr2Forward";
                nextPosition2 = "";
                nextPosition3 = "";
                nextPosition4 = "";
                break;
            case "Orc":
                killOrc = true;
                updateStat();
                nextPosition1 = "cr3Left";
                nextPosition2 = "";
                nextPosition3 = "";
                nextPosition4 = "";
                break;
            case "Golem":
                killGolem = true;
                updateStat();
                nextPosition1 = "cr3Right";
                nextPosition2 = "";
                nextPosition3 = "";
                nextPosition4 = "";
                break;
            case "Dragon":
                killDragon = true;
                updateStat();
                nextPosition1 = "bossFightDragon";
                nextPosition2 = "";
                nextPosition3 = "";
                nextPosition4 = "";
                break;
        }
    }

    public void ending(){
        text.setText("You have defeated the last boss the mighty Dragon!\n You have become a true hero!\n\n<THE END>");

        bOption1.setText("Finish");
        bOption2.setText("");
        bOption3.setText("");
        bOption4.setText("");

        nextPosition1 = "goMenuScreen";
        nextPosition2 = "";
        nextPosition3 = "";
        nextPosition4 = "";
    }
    // Fighting scene - END

    // Dead
    public void dead(){
        characterDelete();
        text.setText("You are dead. Your adventure ends here.\n\nGAME OVER");

        bOption1.setText("Go th the menu screen");
        bOption2.setText("");
        bOption3.setText("");
        bOption4.setText("");

        showOneButton();

        nextPosition1 = "goMenuScreen";
        nextPosition2 = "";
        nextPosition3 = "";
        nextPosition4 = "";
    }
    // Dead - END
    // Game story - END

    // Show and don't show buttons
    private void showAllButtons() {
        bOption1.setVisibility(View.VISIBLE);
        bOption2.setVisibility(View.VISIBLE);
        bOption3.setVisibility(View.VISIBLE);
        bOption4.setVisibility(View.VISIBLE);
    }
    public void showOneButton(){
        bOption2.setVisibility(View.VISIBLE);
        bOption3.setVisibility(View.VISIBLE);
        bOption4.setVisibility(View.VISIBLE);
    }
    public void showTwoButtons(){
        bOption3.setVisibility(View.VISIBLE);
        bOption4.setVisibility(View.VISIBLE);
    }
    // Show and don't show buttons - END
}
