package im.mace.android.bounce.io;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class HighScoreDatabase extends SQLiteOpenHelper {
	
	private static final int dbVersion = 1;

	private static final String dbName="highscoresDB";
	
	private static final String scoresTable="Scores";
	private static final String colLevelID="LevelID";
	private static final String colMode="Mode";
	private static final String colScore="Score";


	public HighScoreDatabase(Context context) {
		super(context, dbName, null, dbVersion);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String createTimes = "CREATE TABLE "+scoresTable+
								"("+
									colLevelID+" TEXT NOT NULL, "+
									colMode+" TEXT NOT NULL, "+
									colScore+" LONG NOT NULL, "+
									"CONSTRAINT pk_levelID PRIMARY KEY ("+colLevelID+","+colMode+")"+
								")";
		Log.i("sql", "Executing SQL " + createTimes);
		
		db.execSQL(createTimes);
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// Do nothing
	}
	
	public void setScore(String mode, String levelID, Long newScore) {
		Long currentScore = this.getScore(mode, levelID);
		if (currentScore==null) {
			this.saveNewScore(mode, levelID, newScore);
		} else {
			this.updateScore(mode, levelID, newScore);
		}
	}
	
	public Long getScore(String mode, String levelID) {
		Long result = null;
		
		SQLiteDatabase db=this.getReadableDatabase();
		String rawQuery = 	"SELECT "+colScore+
							" FROM "+scoresTable+
							" WHERE "+colLevelID+"='"+levelID+"'"+
							" AND "+colMode+"='"+mode+"'";
		Log.i("sql", "Executing SQL " + rawQuery);
		Cursor c = db.rawQuery(rawQuery, new String[] {});
		if (c.moveToFirst()) {
			result = c.getLong(c.getColumnIndex(colScore));
		}
		
		return result;
	}
	
	private void saveNewScore(String mode, String levelID, Long score) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put(colMode, mode);
		cv.put(colLevelID, levelID);
		cv.put(colScore, score);
		db.insert(scoresTable, null, cv);
	}
	
	private void updateScore(String mode, String levelID, Long score) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put(colScore, score);
		String whereClause = colMode+"='"+mode+"' AND "+colLevelID+"='"+levelID+"'";
		int i = db.update(scoresTable, cv, whereClause, new String []{});   
		Log.i("jon", "Updated "+i);
	}

}
