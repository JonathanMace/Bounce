package im.mace.android.bounce.game.practice;

import im.mace.android.bounce.common.Level;
import im.mace.android.bounce.game.mainmenu.MainMenu;
import im.mace.android.bounce.game.mainmenu.SelectLevelMenu;
import im.mace.android.bounce.game.mainmenu.SelectLevelSetMenu;

public class PracticeModeMenu extends SelectLevelSetMenu {

	@Override
	protected void onLevelSetSelected(MainMenu menu, String levelSet) {
        menu.nextMenu(new PracticeModeSelectLevelMenu(levelSet));
	}
	
	public class PracticeModeSelectLevelMenu extends SelectLevelMenu {

		public PracticeModeSelectLevelMenu(String levelSet) {
			super(levelSet, 0);
		}

		@Override
		protected void onLevelSelected(MainMenu menu, int levelNumber) {
			menu.startGame(PracticeMode.class, levelSet, levelNumber);
		}

		@Override
		protected boolean displayTick(Level level) {
			return false;
		}
		
	}
	
}
