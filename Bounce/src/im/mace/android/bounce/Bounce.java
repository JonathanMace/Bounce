package im.mace.android.bounce;

import im.mace.android.bounce.levels.LevelIO;
import im.mace.android.bounce.levels.LevelManager;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class Bounce extends Activity {
    

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        LevelIO.assetManager = this.getAssets();

        Intent intent = new Intent(getBaseContext(), MainMenuActivity.class);
        startActivityForResult(intent, 0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        finish();
    }

}
