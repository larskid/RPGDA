package no.hiof.larsgh.rpgda;

import android.view.View;

public class Story {

    GameScreen gs;
    String nextPosition1, nextPosition2, nextPosition3, nextPosition4;
    boolean ironSword = false;
    boolean killGoblin = false;

    public Story(GameScreen gs){

        this.gs = gs;
    }

    public void selectPosition(String position){
        switch (position){
            case "startingPoint": startingPoint(); break;
            case "sign": sign(); break;
            case "right": right(); break;
            case "searchCorpse": searchCorpse(); break;
            case "left": left(); break;
            case "dead": dead(); break;
            case "goMenuScreen": gs.goMenuScreen(); break;
            case "forward": forward(); break;
            case "fightGoblin": fightGoblin(); break;
            case "fightGiantSnake": fightGiantSnake(); break;
        }
    }

    public void showAllButtons(){
        gs.bOption1.setVisibility(View.VISIBLE);
        gs.bOption2.setVisibility(View.VISIBLE);
        gs.bOption3.setVisibility(View.VISIBLE);
        gs.bOption4.setVisibility(View.VISIBLE);
    }

    public void startingPoint(){

        gs.text.setText("You are walking in to the dungeon seeking fame and treasures. \nAfter walking a while you meet a crossroad.\nOn the wall you see a sign.\n\nWhat will you do?");

        gs.bOption1.setText("Go left");
        gs.bOption2.setText("Go Right");
        gs.bOption3.setText("Go forward");
        gs.bOption4.setText("Read the sign");

        showAllButtons();

        nextPosition1 = "left";
        nextPosition2 = "right";
        nextPosition3 = "forward";
        nextPosition4 = "sign";
    }

    public void sign(){

        gs.text.setText("The sign says: \n\n DANGER MONSTER TO THE LEFT!");

        gs.bOption1.setText("Back");
        gs.bOption2.setText("");
        gs.bOption3.setText("");
        gs.bOption4.setText("");
        gs.bOption2.setVisibility(View.INVISIBLE);
        gs.bOption3.setVisibility(View.INVISIBLE);
        gs.bOption4.setVisibility(View.INVISIBLE);


        nextPosition1 = "startingPoint";
        nextPosition2 = "";
        nextPosition3 = "";
        nextPosition4 = "";

    }

    public void right(){
        gs.text.setText("You discover an old adventure corpse.");

        gs.bOption1.setText("Search the corpse");
        gs.bOption2.setText("Back");
        gs.bOption3.setText("");
        gs.bOption4.setText("");

        gs.bOption3.setVisibility(View.INVISIBLE);
        gs.bOption4.setVisibility(View.INVISIBLE);


        nextPosition1 = "searchCorpse";
        nextPosition2 = "startingPoint";
        nextPosition3 = "";
        nextPosition4 = "";
    }

    public void searchCorpse(){
        gs.text.setText("After searching the old corpse you found a sword!\n\n(You have a rusty sword)");

        ironSword = true;

        gs.bOption1.setText("Back");
        gs.bOption2.setText("");
        gs.bOption3.setText("");
        gs.bOption4.setText("");

        gs.bOption2.setVisibility(View.INVISIBLE);
        gs.bOption3.setVisibility(View.INVISIBLE);
        gs.bOption4.setVisibility(View.INVISIBLE);


        nextPosition1 = "startingPoint";
        nextPosition2 = "";
        nextPosition3 = "";
        nextPosition4 = "";

    }

    public void left(){

        if (ironSword==true && killGoblin==true){
            gs.text.setText("You chose the road on the left and encounter a giant snake.\n\nWhat do you do?");

            gs.bOption1.setText("Attack");
            gs.bOption2.setText("Run");
            gs.bOption3.setText("");
            gs.bOption4.setText("");

            gs.bOption3.setVisibility(View.INVISIBLE);
            gs.bOption4.setVisibility(View.INVISIBLE);

            nextPosition1 = "fightGiantSnake";
            nextPosition2 = "startingPoint";
            nextPosition3 = "";
            nextPosition4 = "";
        }
        else {
            gs.text.setText("You chose the road on the left and encounter a giant snake. You try to fight the snake but it is to strong.\n\nThe giant snake kills you.");

            gs.bOption1.setText(">");
            gs.bOption2.setText("");
            gs.bOption3.setText("");
            gs.bOption4.setText("");

            gs.bOption3.setVisibility(View.INVISIBLE);
            gs.bOption4.setVisibility(View.INVISIBLE);

            nextPosition1 = "dead";
            nextPosition2 = "";
            nextPosition3 = "";
            nextPosition4 = "";
        }
    }

    public void dead(){
        showAllButtons();
        gs.text.setText("You are dead. Your adventure ends here.\n\nGAME OVER");

        gs.bOption1.setText("Try again form the beginning");
        gs.bOption2.setText("Go th the menu screen");
        gs.bOption3.setText("");
        gs.bOption4.setText("");

        gs.bOption3.setVisibility(View.INVISIBLE);
        gs.bOption4.setVisibility(View.INVISIBLE);

        nextPosition1 = "startingPoint";
        nextPosition2 = "goMenuScreen";
        nextPosition3 = "";
        nextPosition4 = "";
    }

    public void forward(){

        if (ironSword==false){
            gs.text.setText("You walk forward and meet and monster!\nThe monster is a Goblin that looks weak.\n\nWhat do you do?");

            gs.bOption1.setText("Attack the goblin");
            gs.bOption2.setText("run");
            gs.bOption3.setText("");
            gs.bOption4.setText("");

            gs.bOption3.setVisibility(View.INVISIBLE);
            gs.bOption4.setVisibility(View.INVISIBLE);

            nextPosition1 = "dead";
            nextPosition2 = "startingPoint";
            nextPosition3 = "";
            nextPosition4 = "";
        }if (ironSword == true){
            gs.text.setText("You walk forward and meet and monster!\nThe monster is a Goblin that looks weak.\n\nWhat do you do?");

            gs.bOption1.setText("Attack the Goblin!");
            gs.bOption2.setText("Run");
            gs.bOption3.setText("");
            gs.bOption4.setText("");

            gs.bOption3.setVisibility(View.INVISIBLE);
            gs.bOption4.setVisibility(View.INVISIBLE);

            nextPosition1 = "fightGoblin";
            nextPosition2 = "startingPoint";
            nextPosition3 = "";
            nextPosition4 = "";

            //mangler sloss sene
        }

    }
    public void fightGoblin(){
        gs.text.setText("You defeated the Gobling with your rusty sword!\n\nYou feel much stronger now!");

        killGoblin = true;

        gs.bOption1.setText("Back");
        gs.bOption2.setText("");
        gs.bOption3.setText("");
        gs.bOption4.setText("");

        gs.bOption2.setVisibility(View.INVISIBLE);
        gs.bOption3.setVisibility(View.INVISIBLE);
        gs.bOption4.setVisibility(View.INVISIBLE);

        nextPosition1 = "startingPoint";
        nextPosition2 = "";
        nextPosition3 = "";
        nextPosition4 = "";
    }

    public void fightGiantSnake(){
        gs.text.setText("The monster was strong but you defeated the giant snake with your rusty sword and experience as a warrior! You find the giant snakes treasure and cleared the dungeon!!\n\nTHE END");

        gs.bOption1.setText("Go to character selection screen");
        gs.bOption2.setText("");
        gs.bOption3.setText("");
        gs.bOption4.setText("");

        gs.bOption2.setVisibility(View.INVISIBLE);
        gs.bOption3.setVisibility(View.INVISIBLE);
        gs.bOption4.setVisibility(View.INVISIBLE);

        nextPosition1 = "goMenuScreen";
        nextPosition2 = "";
        nextPosition3 = "";
        nextPosition4 = "";
    }
}
