package im.mace.android.bounce;

import im.mace.android.bounce.game.mainmenu.MainMenuActivity;
import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;

public class Bounce extends Activity {
	
	public static AssetManager assetManager;
    

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        assetManager = this.getAssets();
        
//        testDB();

        Intent intent = new Intent(getBaseContext(), MainMenuActivity.class);
        startActivityForResult(intent, 0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        finish();
    }
    

}
