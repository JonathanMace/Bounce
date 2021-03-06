package im.mace.android.bounce.common;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.opengl.font.Font;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;

public class TextureUtils {
    
    public static TextureBucket loadGameTextures(Context context, Engine engine) {
        /* Create the atlases */
        BitmapTextureAtlas m64BitmapTextureAtlas = new BitmapTextureAtlas(64, 64, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        BitmapTextureAtlas m128BitmapTextureAtlas = new BitmapTextureAtlas(128, 1024, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        BitmapTextureAtlas m256BitmapTextureAtlas = new BitmapTextureAtlas(256, 512, TextureOptions.BILINEAR_PREMULTIPLYALPHA);

        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
        
        TextureBucket bucket = new TextureBucket();

        /* Game sprites */
        bucket.addTexture("ball", BitmapTextureAtlasTextureRegionFactory.createFromAsset(m64BitmapTextureAtlas, context, "basketball.png", 0, 0));
        bucket.addTexture("recticle", BitmapTextureAtlasTextureRegionFactory.createFromAsset(m128BitmapTextureAtlas, context, "recticle.png", 0, 0));
        bucket.addTexture("ghostrecticle", BitmapTextureAtlasTextureRegionFactory.createFromAsset(m128BitmapTextureAtlas, context, "ghostrecticle.png", 0, 128));
        
        /* Button sprites */
        bucket.addTiledTexture("reset", BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(m256BitmapTextureAtlas, context, "reset.png", 0, 0, 2, 1));
        bucket.addTiledTexture("quit", BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(m256BitmapTextureAtlas, context, "quit.png", 0, 128, 2, 1));
        bucket.addTiledTexture("next", BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(m256BitmapTextureAtlas, context, "next.png", 0, 256, 2, 1));

        /* Load the atlases into the engine */
        engine.getTextureManager().loadTexture(m64BitmapTextureAtlas);
        engine.getTextureManager().loadTexture(m128BitmapTextureAtlas);
        engine.getTextureManager().loadTexture(m256BitmapTextureAtlas);
        
        return bucket;
    }
    
    public static TextureBucket loadMenuTextures(Context context, Engine engine) {
        BitmapTextureAtlas m64BitmapTextureAtlas = new BitmapTextureAtlas(64, 128, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        BitmapTextureAtlas m512BitmapTextureAtlas = new BitmapTextureAtlas(512, 1024, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        BitmapTextureAtlas m1024BitmapTextureAtlas = new BitmapTextureAtlas(1024, 2048, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        BitmapTextureAtlas m2048BitmapTextureAtlas = new BitmapTextureAtlas(2048, 64, TextureOptions.BILINEAR_PREMULTIPLYALPHA);

        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");

        TextureBucket bucket = new TextureBucket();

        bucket.addTexture("ball", BitmapTextureAtlasTextureRegionFactory.createFromAsset(m64BitmapTextureAtlas, context, "basketball.png", 0, 0));
        bucket.addTexture("tick", BitmapTextureAtlasTextureRegionFactory.createFromAsset(m64BitmapTextureAtlas, context, "tick.png", 0, 64));

        bucket.addTexture("logo", BitmapTextureAtlasTextureRegionFactory.createFromAsset(m512BitmapTextureAtlas, context, "logo.png", 0, 0));
        bucket.addTiledTexture("button_playgame", BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(m512BitmapTextureAtlas, context, "button_playgame.png", 0, 128, 2, 1));
        bucket.addTiledTexture("button_settings", BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(m512BitmapTextureAtlas, context, "button_settings.png", 0, 256, 2, 1));
        bucket.addTiledTexture("button_quit", BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(m512BitmapTextureAtlas, context, "button_quit.png", 0, 384, 2, 1));
        bucket.addTiledTexture("arrowleft", BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(m512BitmapTextureAtlas, context, "arrowleft.png", 0, 512, 2, 1));
        bucket.addTiledTexture("arrowright", BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(m512BitmapTextureAtlas, context, "arrowright.png", 0, 640, 2, 1));
        
        // Select mode buttons
        bucket.addTiledTexture("button_practice", BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(m1024BitmapTextureAtlas, context, "button_practice.png", 0, 0, 3, 1));
        bucket.addTiledTexture("button_standardmode", BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(m1024BitmapTextureAtlas, context, "button_standardmode.png", 0, 128, 3, 1));
        bucket.addTiledTexture("button_minbounce", BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(m1024BitmapTextureAtlas, context, "button_minbounce.png", 0, 256, 3, 1));
        bucket.addTiledTexture("button_maxbounce", BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(m1024BitmapTextureAtlas, context, "button_maxbounce.png", 0, 384, 3, 1));
        bucket.addTiledTexture("button_minmax", BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(m1024BitmapTextureAtlas, context, "button_minmax.png", 0, 512, 3, 1));
        bucket.addTiledTexture("button_timeattack", BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(m1024BitmapTextureAtlas, context, "button_timeattack.png", 0, 640, 3, 1));

        // Select level set buttons
        bucket.addTiledTexture("button_levels_starter", BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(m1024BitmapTextureAtlas, context, "button_levels_starter.png", 0, 768, 3, 1));
        bucket.addTiledTexture("button_levels_bouncy", BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(m1024BitmapTextureAtlas, context, "button_levels_bouncy.png", 0, 896, 3, 1));
        bucket.addTiledTexture("button_levels_trickshot", BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(m1024BitmapTextureAtlas, context, "button_levels_trickshot.png", 0, 1024, 3, 1));
        bucket.addTiledTexture("button_levels_intermediate", BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(m1024BitmapTextureAtlas, context, "button_levels_intermediate.png", 0, 1152, 3, 1));
        bucket.addTiledTexture("button_levels_delicate", BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(m1024BitmapTextureAtlas, context, "button_levels_delicate.png", 0, 1280, 3, 1));
        bucket.addTiledTexture("button_levels_fluke", BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(m1024BitmapTextureAtlas, context, "button_levels_fluke.png", 0, 1408, 3, 1));
        bucket.addTiledTexture("button_levels_advanced", BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(m1024BitmapTextureAtlas, context, "button_levels_advanced.png", 0, 1536, 3, 1));
        bucket.addTiledTexture("button_levels_painful", BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(m1024BitmapTextureAtlas, context, "button_levels_painful.png", 0, 1664, 3, 1));
        bucket.addTiledTexture("button_levels_impossible", BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(m1024BitmapTextureAtlas, context, "button_levels_impossible.png", 0, 1792, 3, 1));

        bucket.addTiledTexture("button_level", BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(m2048BitmapTextureAtlas, context, "button_level.png", 0, 0, 3, 1));
        
        
        engine.getTextureManager().loadTexture(m64BitmapTextureAtlas);
        engine.getTextureManager().loadTexture(m512BitmapTextureAtlas);
        engine.getTextureManager().loadTexture(m1024BitmapTextureAtlas);
        engine.getTextureManager().loadTexture(m2048BitmapTextureAtlas);
        
        return bucket;   
    }
    
    public static Font loadGameFont(Engine engine) {
//        return loadFont(engine, 50, Color.rgb(181, 196, 0));
        return loadFont(engine, 32, Color.rgb(230, 130, 130));
    }
    
    public static Font loadMenuFont(Engine engine) {
        return loadFont(engine, 32, Color.rgb(100, 100, 100));
    }
    
    public static Font loadFont(Engine engine, int fontSize, int colour) {
        BitmapTextureAtlas fontTexture = new BitmapTextureAtlas(256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);

        Font font = new Font(fontTexture, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), fontSize, true, colour);

        engine.getTextureManager().loadTexture(fontTexture);
        engine.getFontManager().loadFont(font);
        
        return font;
    }

}
