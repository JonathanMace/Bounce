package im.mace.android.bounce.io;


import im.mace.android.bounce.common.Constants;
import im.mace.android.bounce.common.LevelSpec;
import im.mace.android.bounce.ui.Wall;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

public class LevelSpecHandler extends DefaultHandler {
    
    private final LevelSpec def = new LevelSpec();

    public LevelSpec getLevelDef() {
        return def;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
    	if (localName.equals("level") || qName.equals("level")) {
            String name = attributes.getValue("name");
            String id = attributes.getValue("id");
            def.name = name==null ? def.name : name;
            def.id = id==null? def.id : id;
            def.min = getLongAttribute(attributes.getValue("min"), def.min);
            def.max = getLongAttribute(attributes.getValue("max"), def.max);
            def.minmax = getLongAttribute(attributes.getValue("minmax"), def.minmax);
            def.time = getLongAttribute(attributes.getValue("time"), def.time);
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
    
    private long getLongAttribute(String value, long defaultValue) {
        if (value!=null) {
            try {
                defaultValue = Long.valueOf(value);
            } catch (Exception e) {
                // Do nothing
            }
        }
        return defaultValue;    	
    }
    
}
