package im.mace.android.bounce.game.mainmenu;

import im.mace.android.bounce.common.Constants;
import im.mace.android.bounce.game.MenuPage;
import im.mace.android.bounce.ui.DisableButton;

import java.util.ArrayList;
import java.util.Collection;

import org.anddev.andengine.entity.shape.Shape;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

public abstract class SelectLevelSetMenu extends MenuPage {

    private static final float[] COLS = new float[] { 13, 275, 537 };
    private static final float[] ROWS = new float[] { 150, 260, 370 };
    
    private static final float BUTTON_WIDTH = 250;
    private static final float BUTTON_HEIGHT = 100;

	@Override
	public Collection<Shape> constructMenuObjects(MainMenu menu) {
		Collection<Shape> buttons = new ArrayList<Shape>();

        for (int i = 0; i < Constants.LEVELSETS.length; i++) {
            String[] row = Constants.LEVELSETS[i];
            for (int j = 0; j < row.length; j++) {
                String levelSetName = row[j];
                buttons.add(this.constructButton(COLS[j], ROWS[i], BUTTON_WIDTH, BUTTON_HEIGHT, levelSetName, menu));
            }
        }
		
		return buttons;
	}

    private DisableButton constructButton(float x, float y, float w, float h, final String setName, final MainMenu menu) {
        DisableButton button = null;
        TiledTextureRegion buttonTexture = menu.getTextures().getTiledTexture("button_levels_"+setName);
        if (buttonTexture != null) {
            button = new DisableButton(x, y, w, h, menu.getGameState().isLevelSetUnlocked(setName), buttonTexture){
                public void onClick() {
                	onLevelSetSelected(menu, setName);
                }
            };
        }
        return button;
    }
    
    protected abstract void onLevelSetSelected(MainMenu menu, String levelSet);

}
