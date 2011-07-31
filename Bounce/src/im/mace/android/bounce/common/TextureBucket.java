package im.mace.android.bounce.common;

import java.util.HashMap;
import java.util.Map;

import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

public class TextureBucket {
    
    private Map<String, TextureRegion> textures = new HashMap<String, TextureRegion>();
    private Map<String, TiledTextureRegion> tiledTextures = new HashMap<String, TiledTextureRegion>();
    
    void addTexture(String textureName, TextureRegion texture) {
        textures.put(textureName, texture);
    }
    
    void addTiledTexture(String textureName, TiledTextureRegion tiledTexture) {
        tiledTextures.put(textureName, tiledTexture);
    }

    public TextureRegion getTexture(String textureName) {
        return textures.get(textureName);
    }
    
    public TiledTextureRegion getTiledTexture(String tiledTextureName) {
        return tiledTextures.get(tiledTextureName);
    }

}
