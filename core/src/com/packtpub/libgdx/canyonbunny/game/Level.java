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
  
  private void init(String fileName) {}
  public void render(SpriteBatch batch) {}
}








