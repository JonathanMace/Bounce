package im.mace.android.bounce.game.standard;

import im.mace.android.bounce.game.mainmenu.MainMenu;
import im.mace.android.bounce.game.mainmenu.SelectLevelSetMenu;

public class StandardModeMenu extends SelectLevelSetMenu {

	@Override
	protected void onLevelSetSelected(MainMenu menu, String levelSet) {
		menu.startGame(StandardMode.class, levelSet);		
	}

}
