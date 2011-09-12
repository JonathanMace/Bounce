import im.mace.android.bounce.common.LevelSpec;
import im.mace.android.bounce.ui.Wall;


public class LevelDefXMLWriter {

    public static String writeXML(LevelSpec def) {
        StringBuilder xml = new StringBuilder();
        
        xml.append("<level name=\"");
        xml.append(def.name);
        xml.append("\" id=\""+def.id+"\">\n");
        
        xml.append("\t<ball x=\"");
        xml.append((int) def.ballX);
        xml.append("\" y=\"");
        xml.append((int) def.ballY);
        xml.append("\"/>\n");
        
        xml.append("\t<bucket x=\"");
        xml.append((int) def.bucketX);
        xml.append("\" y=\"");
        xml.append((int) def.bucketY);
        xml.append("\"/>\n");
        
        for (Wall wall : def.walls) {
            xml.append("\t<wall x1=\"");
            xml.append((int) wall.x1);
            xml.append("\" y1=\"");
            xml.append((int) wall.y1);
            xml.append("\" x2=\"");
            xml.append((int) wall.x2);
            xml.append("\" y2=\"");
            xml.append((int) wall.y2);
            xml.append("\"/>\n");
        }
        
        xml.append("</level>");
        
        return xml.toString();
    }    

}
