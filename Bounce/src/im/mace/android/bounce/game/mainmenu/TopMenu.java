package im.mace.android.bounce.game.mainmenu;

import im.mace.android.bounce.common.Constants;
import im.mace.android.bounce.game.MenuPage;
import im.mace.android.bounce.ui.Button;

import java.util.ArrayList;
import java.util.Collection;

import org.anddev.andengine.entity.shape.Shape;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

public class TopMenu extends MenuPage {

	private static final float BUTTON_WIDTH = 250;
	private static final float BUTTON_HEIGHT = 100;
	
	private static final float BUTTONS_X = (Constants.CAMERA_WIDTH - BUTTON_WIDTH)/2;

	private static final float PLAY_Y = 150;
	private static final float SETTINGS_Y = 260;
	private static final float QUIT_Y = 370;

	@Override
	public Collection<Shape> constructMenuObjects(MainMenu menu) {
		Collection<Shape> buttons = new ArrayList<Shape>();
		buttons.add(constructPlayButton(menu));
		buttons.add(constructSettingsButton(menu));
		buttons.add(constructQuitButton(menu));
		return buttons;
	}
	
	private Button constructPlayButton(final MainMenu menu) {
        Button playGameButton = null;
        TiledTextureRegion buttonTexture = menu.getTextures().getTiledTexture("button_playgame");
        if (buttonTexture != null) {
            playGameButton = new Button(BUTTONS_X, PLAY_Y, BUTTON_WIDTH, BUTTON_HEIGHT, buttonTexture){
                public void onClick() {
                    menu.nextMenu(new SelectModeMenu());
                }
            };
        }        
        return playGameButton;
	}
    
	private Button constructSettingsButton(final MainMenu menu) {
        Button nextButton = null;
        TiledTextureRegion resetTexture = menu.getTextures().getTiledTexture("button_settings");
        if (resetTexture != null) {
            nextButton = new Button(BUTTONS_X, SETTINGS_Y, BUTTON_WIDTH, BUTTON_HEIGHT, resetTexture){
                public void onClick() {
                	// TODO: implement
                }
            };
        }        
        return nextButton;
    }
    
	private Button constructQuitButton(final MainMenu menu) {
        Button quitButton = null;
        TiledTextureRegion quitTexture = menu.getTextures().getTiledTexture("button_quit");
        if (quitTexture != null) {
            quitButton = new Button(BUTTONS_X, QUIT_Y, BUTTON_WIDTH, BUTTON_HEIGHT, quitTexture){
                public void onClick() {
                    menu.quitGame();
                }
            };
        }
        return quitButton;
    }

}
