package im.mace.android.bounce;

import im.mace.android.bounce.common.Constants;
import im.mace.android.bounce.levels.LevelManager;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class GameControllerActivity extends Activity {

    int count = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String levelSet = this.getIntent().getStringExtra(Constants.LEVEL_SET_KEY);
        Log.i("jon", "Starting level set " + levelSet);
        if (levelSet==null) {
            levelSet="impossible";
        }
        LevelManager.begin(levelSet);
        startLevel();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode==RESULT_CANCELED) {
            finish();
        } else {
            LevelManager.progress();
            if (LevelManager.hasCurrent()) {
                startLevel();
            } else {
                finish();
            }
        }
    }

    private void startLevel() {
        Intent intent = new Intent(getBaseContext(), GameActivity.class);
        startActivityForResult(intent, 0);
    }

}
