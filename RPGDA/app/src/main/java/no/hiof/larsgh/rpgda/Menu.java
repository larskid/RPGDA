package no.hiof.larsgh.rpgda;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

public class Menu extends AppCompatActivity {

    public static final String DEFAULT = "N/A";
    Button lgButton, desButton, infoSettingButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        lgButton = findViewById(R.id.loadGameButton);
        saveCheck();
        desButton = findViewById(R.id.deletExsistingSaveButton);
        infoSettingButton = findViewById(R.id.infoSettingsMenuButton);

        infoSettingButton.setOnClickListener(v -> showPopup());


        desButton.setOnClickListener(v -> {
            //Alert for deleting saved data (stat)
            AlertDialog.Builder builder = new AlertDialog.Builder(Menu.this);
            builder.setMessage("Are you sure you want to delete the character?");
            builder.setTitle("Deleting character!");
            builder.setCancelable(false);
            builder.setPositiveButton("Yes", (dialog, which) -> {
                lgButton.setVisibility(View.INVISIBLE);

                SharedPreferences spDelete = getSharedPreferences("userStat", MODE_PRIVATE);
                SharedPreferences.Editor editor = spDelete.edit();

                editor.clear();
                editor.commit();
            });
            builder.setNegativeButton("No", (dialog, which) -> dialog.cancel());
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        });
    }


    public void goCCScreen(View view){
        Intent ccScreen = new Intent(this, CharacterCreation.class);
        startActivity(ccScreen);
    }

    public void goCSScreen(View view){
        Intent csScreen = new Intent(this, CharacterSelection.class);
        startActivity(csScreen);
    }

    public void backTitleScreen(View view){
        Intent bTitleScreen = new Intent(this, TitleScreen.class);
        startActivity(bTitleScreen);
    }

    public void saveCheck(){
        SharedPreferences spName = getSharedPreferences("userStat", MODE_PRIVATE);
        String name = spName.getString("name", DEFAULT);
        if (name.equals(DEFAULT)) {
            lgButton.setVisibility(View.INVISIBLE);
        }
        else {
            lgButton.setVisibility(View.VISIBLE);
        }
    }

    // A popup window for setting an info
    private PopupWindow POPUP_WINDOW_SCORE = null;
    private void showPopup(){
        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.popup_window, null);

        POPUP_WINDOW_SCORE = new PopupWindow(this);
        POPUP_WINDOW_SCORE.setContentView(layout);
        POPUP_WINDOW_SCORE.setWidth(width);
        POPUP_WINDOW_SCORE.setHeight(height);
        POPUP_WINDOW_SCORE.setFocusable(true);
        POPUP_WINDOW_SCORE.setBackgroundDrawable(null);
        POPUP_WINDOW_SCORE.showAtLocation(layout, Gravity.CENTER,1 ,1);

        TextView info = layout.findViewById(R.id.tvMenuInfo);
        info.setText(R.string.menu_info_description);


        Button butBack = layout.findViewById(R.id.popupBackButtonMenu);
        butBack.setOnClickListener(v -> POPUP_WINDOW_SCORE.dismiss());
    }
}