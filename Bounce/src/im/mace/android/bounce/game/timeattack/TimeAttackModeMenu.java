package im.mace.android.bounce.game.timeattack;

import im.mace.android.bounce.common.Constants;
import im.mace.android.bounce.common.Level;
import im.mace.android.bounce.game.mainmenu.MainMenu;
import im.mace.android.bounce.game.mainmenu.SelectLevelMenu;
import im.mace.android.bounce.game.mainmenu.SelectLevelSetMenu;

public class TimeAttackModeMenu extends SelectLevelSetMenu {
	
	public TimeAttackModeMenu() {
		super(Constants.MODE_TIME);
	}

	@Override
	protected void onLevelSetSelected(MainMenu menu, String levelSet) {
        menu.nextMenu(new SelectLevelTimeAttackMenu(levelSet));
	}
	
	public class SelectLevelTimeAttackMenu extends SelectLevelMenu {

		public SelectLevelTimeAttackMenu(String levelSet) {
			super(levelSet, 0);
		}

		@Override
		protected void onLevelSelected(MainMenu menu, int levelNumber) {
			menu.startGame(TimeAttackMode.class, levelSet, levelNumber);
		}

		@Override
		protected boolean displayTick(Level level) {
			return level.beatenTime();
		}
		
	}
	
}
