package im.mace.android.bounce.game.mainmenu;

import im.mace.android.bounce.common.Constants;
import im.mace.android.bounce.game.MenuPage;
import im.mace.android.bounce.game.maxbounce.MaxModeMenu;
import im.mace.android.bounce.game.minbounce.MinModeMenu;
import im.mace.android.bounce.game.practice.PracticeModeMenu;
import im.mace.android.bounce.game.standard.StandardModeMenu;
import im.mace.android.bounce.game.timeattack.TimeAttackModeMenu;
import im.mace.android.bounce.ui.DisableButton;

import java.util.ArrayList;
import java.util.Collection;

import org.anddev.andengine.entity.shape.Shape;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

public class SelectModeMenu extends MenuPage {
	
	private static final float COL_1 = 125;
	private static final float COL_2 = 425;

	private static final float ROW_1 = 150;
	private static final float ROW_2 = 260;
	private static final float ROW_3 = 370;
	
	private static final float BUTTON_WIDTH = 250;
	private static final float BUTTON_HEIGHT = 100;

	@Override
	public Collection<Shape> constructMenuObjects(MainMenu menu) {
		Collection<Shape> buttons = new ArrayList<Shape>();
		buttons.add(constructPracticeButton(menu));
		buttons.add(constructStandardModeButton(menu));
		buttons.add(constructTimeAttackButton(menu));
		buttons.add(constructMinBounceButton(menu));
		buttons.add(constructMaxBounceButton(menu));
		buttons.add(constructMinMaxButton(menu));
		return buttons;
	}

    
    private DisableButton constructPracticeButton(final MainMenu menu) {
        DisableButton button = null;
        TiledTextureRegion buttonTexture = menu.getTextures().getTiledTexture("button_practice");
        if (buttonTexture != null) {
            button = new DisableButton(COL_1, ROW_1, BUTTON_WIDTH, BUTTON_HEIGHT, menu.getGameState().isModeUnlocked(Constants.MODE_PRACTICE), buttonTexture){
                public void onClick() {
                    menu.nextMenu(new PracticeModeMenu());
                }
            };
        }        
        return button;
    }
    
    private DisableButton constructStandardModeButton(final MainMenu menu) {
        DisableButton button = null;
        TiledTextureRegion buttonTexture = menu.getTextures().getTiledTexture("button_standardmode");
        if (buttonTexture != null) {
            button = new DisableButton(COL_1, ROW_2, BUTTON_WIDTH, BUTTON_HEIGHT, menu.getGameState().isModeUnlocked(Constants.MODE_STANDARD), buttonTexture){
                public void onClick() {
                    menu.nextMenu(new StandardModeMenu());
                }
            };
        }
        return button;
    }

    
    private DisableButton constructTimeAttackButton(final MainMenu menu) {
        DisableButton button = null;
        TiledTextureRegion buttonTexture = menu.getTextures().getTiledTexture("button_timeattack");
        if (buttonTexture != null) {
            button = new DisableButton(COL_1, ROW_3, BUTTON_WIDTH, BUTTON_HEIGHT, menu.getGameState().isModeUnlocked(Constants.MODE_TIME), buttonTexture){
                public void onClick() {
                	menu.nextMenu(new TimeAttackModeMenu());
                }
            };
        } 
        return button;
    }
    
    private DisableButton constructMinBounceButton(final MainMenu menu) {
        DisableButton button = null;
        TiledTextureRegion buttonTexture = menu.getTextures().getTiledTexture("button_minbounce");
        if (buttonTexture != null) {
            button = new DisableButton(COL_2, ROW_1, BUTTON_WIDTH, BUTTON_HEIGHT, menu.getGameState().isModeUnlocked(Constants.MODE_MIN), buttonTexture){
                public void onClick() {
                    menu.nextMenu(new MinModeMenu());
                }
            };
        }
        return button;
    }
    
    private DisableButton constructMaxBounceButton(final MainMenu menu) {
        DisableButton button = null;
        TiledTextureRegion buttonTexture = menu.getTextures().getTiledTexture("button_maxbounce");
        if (buttonTexture != null) {
            button = new DisableButton(COL_2, ROW_2, BUTTON_WIDTH, BUTTON_HEIGHT, menu.getGameState().isModeUnlocked(Constants.MODE_MAX), buttonTexture){
                public void onClick() {
                    menu.nextMenu(new MaxModeMenu());
                }
            };
        }
        return button;
    }
    
    private DisableButton constructMinMaxButton(final MainMenu menu) {
        DisableButton button = null;
        TiledTextureRegion buttonTexture = menu.getTextures().getTiledTexture("button_minmax");
        if (buttonTexture != null) {
            button = new DisableButton(COL_2, ROW_3, BUTTON_WIDTH, BUTTON_HEIGHT, menu.getGameState().isModeUnlocked(Constants.MODE_MINMAX), buttonTexture){
                public void onClick() {
                    // TODO:
                }
            };
        }
        return button;
    }
}
