package im.mace.android.bounce.levels;


import im.mace.android.bounce.levels2.LevelDef;

import java.io.IOException;
import java.io.InputStream;
import java.util.NoSuchElementException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import android.content.res.AssetManager;
import android.util.Log;

public class LevelIO {

    public static AssetManager assetManager;
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
     * Returns an iterator of level defs.  This pretty much defines a set of levels,
     * ie. "Easy" or "Medium".
     * @param chainName the name of the chain to get the levels for
     * @return 
     */
    public static LevelChain getLevels(String chainName) {
        return getLevels(chainName, 0);
    }
    
    /**
     * Returns an iterator of level defs, but beginning at the specified point in the chain
     * @param chainName
     * @param beginIndex
     * @return
     */
    public static LevelChain getLevels(String chainName, int beginIndex) {
        return new LevelChainFromAssets(chainName, beginIndex);
    }
    
    private static String getFileName(String chainName, String levelNumber) {
        return "levels/"+chainName+"/"+levelNumber+".xml";
    }
    
    private static boolean fileExists(String fileName) {
        try {
            assetManager.open(fileName);
            return true;
        } catch (Exception e) {
            Log.i("levelio", "Level " + fileName + " does not exist", e);
            return false;
        }
        
    }
    
    private static LevelDef parseLevel(String fileName) throws SAXException, IOException {
        InputStream is = assetManager.open(fileName);
        LevelDefHandler handler = new LevelDefHandler();
        parser.parse(is, handler);
        return handler.getLevelDef();        
    }
    
    private static class LevelChainFromAssets extends LevelChain {
        
        private String chainName;
        private int levelNumber;

        public LevelChainFromAssets(String chainName, int startLevelNumber) {
            this.chainName = chainName;
            this.levelNumber = startLevelNumber;
        }

        @Override
        public boolean hasNext() {
            return fileExists(getFileName(chainName, String.valueOf(levelNumber)));
        }

        @Override
        public LevelDef next() {
            String fileName = getFileName(chainName, String.valueOf(levelNumber));
            try {
                LevelDef def = parseLevel(fileName);
                levelNumber++;
                return def;
            } catch (Exception e) {
                Log.e("levelio", "Attempted to load " + fileName + " but it does not exist", e);
                throw new NoSuchElementException();
            }
        }
        
    }

}
