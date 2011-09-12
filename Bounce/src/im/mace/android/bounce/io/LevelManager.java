package im.mace.android.bounce.io;


import im.mace.android.bounce.Bounce;
import im.mace.android.bounce.common.Level;
import im.mace.android.bounce.common.LevelSpec;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import android.content.Context;
import android.util.Log;

public class LevelManager {
	
	private static final Map<String, Map<Integer, Level>> levels = new HashMap<String, Map<Integer, Level>>();

    private static final SAXParser parser;
    
    static {
        try {
            parser = SAXParserFactory.newInstance().newSAXParser();
        } catch (ParserConfigurationException e) {
            // Can't load XML, so Error the app
            Log.e("io", "Couldn't initialise the sax parser", e);
            throw new Error("Unable to get to data required to run the app");
        } catch (SAXException e) {
            // Can't load XML, so Error the app
            Log.e("io", "Couldn't initialise the sax parser", e);
            throw new Error("Unable to get to data required to run the app");
        }
    }
    
    /**
     * Loads the specified def and returns a level for it, null otherwise
     * @param levelSet
     * @param levelNumber
     * @return
     */
    public static Level getLevel(Context context, String levelSet, int levelNumber) {
    	Map<Integer, Level> chainLevels = levels.get(levelSet);
    	if (chainLevels==null) {
    		chainLevels = new HashMap<Integer, Level>();
    	}
    	Level level = chainLevels.get(levelNumber);
    	if (level == null) {
    		level = loadLevel(context, levelSet, levelNumber);
    	}
    	chainLevels.put(levelNumber, level);
    	levels.put(levelSet, chainLevels);
    	return level;
    }
    
    public static boolean exists(String levelSet, int levelNumber) {
    	return fileExists(getFileName(levelSet, levelNumber));
    }
    
    public static List<Level> getLevels(Context context, String levelSet) {
    	List<Level> levels = new ArrayList<Level>();
    	int levelNumber = 0;
    	Level next = getLevel(context, levelSet, levelNumber);
    	while (next!=null) {
    		levels.add(next);
    		levelNumber++;
    		next = getLevel(context, levelSet, levelNumber);
    	}
    	return levels;
    }
    
    private static Level loadLevel(Context context, String levelSet, int levelNumber) {
    	Level level = null;
        String fileName = getFileName(levelSet, levelNumber);
        try {
            LevelSpec def = parseLevel(fileName);
            level = new Level(context, def);
        } catch (Exception e) {
        }
        return level;
    }
    
    private static String getFileName(String levelSet, int levelNumber) {
        return "levels/"+levelSet+"/"+levelNumber+".xml";
    }
    
    private static boolean fileExists(String fileName) {
        try {
            Bounce.assetManager.open(fileName);
            return true;
        } catch (Exception e) {
            Log.i("levelio", "Level " + fileName + " does not exist", e);
            return false;
        }
        
    }
    
    private static LevelSpec parseLevel(String fileName) throws SAXException, IOException {
        InputStream is = Bounce.assetManager.open(fileName);
        LevelSpecHandler handler = new LevelSpecHandler();
        parser.parse(is, handler);
        return handler.getLevelDef();        
    }

}
