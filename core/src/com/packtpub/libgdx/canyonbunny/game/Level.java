package com.packtpub.libgdx.canyonbunny.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.packtpub.libgdx.canyonbunny.game.objects.AbstracGameObject;
import com.packtpub.libgdx.canyonbunny.game.objects.Clouds;
import com.packtpub.libgdx.canyonbunny.game.objects.Mountains;
import com.packtpub.libgdx.canyonbunny.game.objects.Rock;
import com.packtpub.libgdx.canyonbunny.game.objects.WaterOverlay;

public class Level {
  public static final String TAG = Level.class.getName();
  
  public enum BLOCK_TYPE {
    EMPTY(0, 0, 0), // BLACK
    ROCK(0, 255, 0), // GREEN
    PLAYER_SPAWNPOINT(255, 255, 255), // WHITE
    ITEM_FEATHER(255, 0, 255), // PURPLE
    ITEM_GOLD_COIN(255, 255, 0);
    
    private int color;
    
    private BLOCK_TYPE(int r, int g, int b){
      color = r << 24 | g << 16 | b << 8 | 0xff;
    }
    public boolean sameColor(int color) {
      return this.color == color;
    }
    public int getColor() {
      return color;
    }
  }
  
  // Objects
  public Array<Rock> rocks;
  
  // Decoration
  public Clouds clouds;
  public Mountains mountains;
  public WaterOverlay waterOverlay;
  
  public Level(String fileName) {
    init(fileName);
  }
  
  private void init(String fileName) {
    // Objects
    rock = new Array<Rock>();
    
    // Load image file that represents the level data
    Pixmap pixmap = new Pixmap(Gdx.file.internal(fileName));
    
    // Scan pixels from top-left to bottom-right
    for(int pixelY = 0; pixelY < pixmap.getHeight(); pixelY++) {
      for(int pixelX = 0;pixelX < pixmap.getWidth(); pixelX++) {
        AbstracGameObject obj = null;
        float offsetHeight = 0;
        // Height grows from bottom to top
        float baseHeight = pixmap.getHeight() - pixelY;
        // Get the color of current pixel as 32-bit RGBA value
        int currentPixel = pixmap.getPixel(pixelX, pixelY);
        // Find matching color value to identify block type at (x, y)
        // point and create the corresponding game object if there is a match
        
        // Empty space
        if(BLOCK_TYPE.EMPTY.sameColor(currentPixel)){
          // Do nothing
        }
        else if(BLOCK_TYPE.ROCK.sameColor(currentPixel)){
          if(lastPixel != currentPixel){
            obj = new Rock();
            float heightIncreaseFactor = 0.25f;
            offsetHeight = -2.5f;
            obj.position.set(pixelX, baseHeight * obj.dimension.y * heightIncreaseFactor + offsetHeight);
            rocks.add((Rock) obj);
          } else{
            rock.get(roks.size() -1).increaseLength(1);
          }
        }
        else if(BLOCK_TYPE.PLAYER_SPAWNPOINT.sameColor(currentPixel)) {
          
        }
        else if(BLOCK_TYPE.ITEM_FEATHER.sameColor(currentPixel)) {
          
        }
        else if(BLOCK_TYPE.ITEM_GOLD_COIN.sameColor(currentPixel)) {
          
        }
        else {
          // For unknown object/pixel color
          int r = 0xff & (currentPixel >>> 24); // Red color channel
          int g = 0xff & (currentPixel >>> 16); // Green color channel
          int b = 0xff & (currentPixel >>> 8); // Blue color channel
          int a = 0xff & currentPixel;
          
          Gdx.app.error(TAG, "Unknown object at x<" + pixelX + "> y<" + pixelY + ">: r<" + r + "> g<" 
                              + g + "> b<" + b + "> a<" + a + ">");
        }
        lastPixel = currentPixel;
      }
    }
    // Decoration
    clouds = new Clouds(pixmap.getWidth());
    clouds.position.set(0, 2);
    mountains = new Mountains(pixmap.getWidth());
    mountains.position.set(-1, -1);
    waterOverlay = new WaterOverlay(pixmap.getWidth());
    waterOverlay.position.set(0, -3.75f);
    
    // Free memory
    pixmap.dispose();
    Gdx.app.debug(TAG, "Level '" + fileName + "' loaded");
  }
  
  public void render(SpriteBatch batch) {
    // Draw Mountains
    mountians.render(batch);
    // Draw Rocks
    for(Rock rock : rocks) {
      rock.render(batch);
    }
    // Draw WaterOverlay
    waterOverlay.render(batch);
    // Draw clouds
    clouds.render(batch);
  }
}








