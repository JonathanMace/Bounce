package im.mace.android.bounce.levels;


import im.mace.android.bounce.common.Constants;
import im.mace.android.bounce.levels2.LevelDef;
import im.mace.android.bounce.objects.Wall;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

public class LevelDefHandler extends DefaultHandler {
    
    private final LevelDef def = new LevelDef();

    public LevelDef getLevelDef() {
        return def;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        if (localName.equals("level") || qName.equals("level")) {
            String name = attributes.getValue("name");
            def.name = name==null ? def.name : name;
        } else if (localName.equals("bucket") || qName.equals("bucket")) {
            def.bucketX = getFloatAttribute(attributes.getValue("x"), def.bucketX);
            def.bucketY = getFloatAttribute(attributes.getValue("y"), def.bucketY);
        } else if (localName.equals("ball") || qName.equals("ball")) {
            def.ballX = getFloatAttribute(attributes.getValue("x"), def.ballX);
            def.ballY = getFloatAttribute(attributes.getValue("y"), def.ballY);
        } else if (localName.equals("wall") || qName.equals("wall")) {
            float x1 = getFloatAttribute(attributes.getValue("x1"), (float) Constants.CAMERA_WIDTH);
            float x2 = getFloatAttribute(attributes.getValue("x2"), (float) Constants.CAMERA_WIDTH);
            float y1 = getFloatAttribute(attributes.getValue("y1"), (float) Constants.CAMERA_HEIGHT);
            float y2 = getFloatAttribute(attributes.getValue("y2"), (float) Constants.CAMERA_HEIGHT);
            float width = getFloatAttribute(attributes.getValue("width"), Constants.WALL_WIDTH_DEFAULT);
            def.walls.add(new Wall(x1, y1, x2, y2, width));
        }
    }
    
    // allow autoboxing so can pass null values
    private float getFloatAttribute(String value, float defaultValue) {
        if (value!=null) {
            try {
                defaultValue = Float.valueOf(value);
            } catch (Exception e) {
                // Do nothing
            }
        }
        return defaultValue;
    }
    
}
