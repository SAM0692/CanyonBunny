package com.packtpub.libgdx.canyonbunny.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.match.MathUtils;

public abstract class AbstractGameObject {
  public Vector2 position;
  public Vector2 dimension;
  public Vector2 origin;
  public Vector2 scale;
  public Vector2 velocity;
  public Vector2 terminalVelocity;
  public Vector2 friction;
  public Vector2 acceleration;
  public float rotation;
  public Rectangle bounds;
  
  public AbstractGameObject() {
    position = new Vector2();
    dimension = new Vector2(1, 1);
    origin = new Vector2();
    scale = new Vector2(1, 1);
    rotation = 0;
    velocity = new Vectro2();
    terminalVelocity = new Vectro2(1, 1);
    friction = new Vectro2();
    acceleration = new Vectro2();
    bounds = new Rectangle();
  }
  
  public void update(float deltaTime) {
    updateMotionX(deltaTime);
    updateMotionY(deltaTime);
    // Move object to new position
    position.x += velocity.x * deltaTime;
    position.y += velocity.y * deltaTime;
  }
  
  protected void updateMotionX(float deltaTime) {
    if(velocity.x != 0) {
      // Apply friction
      if(velocity.x > 0) {
        velocity.x = Math.max(velocity.x - friction.x * deltaTime, 0);
      }else {
        velocity.x = Math.max(velocity.x + friction.x * deltaTime, 0);
      }
    }
    // Apply acceleration
    velocity.x += acceleration.x * deltaTime;
    // Make sure the velocity does not exceed the positive or negetive terminal velocity
    velocity.x = MathUtils.clamp(velocity.x, -terminalVelocity.x, terminalVelocity.x);
  }
  
  protected void updateMotionY(float deltaTime) {
    if(velocity.y != 0) {
      // Apply friction
      if(velocity.y > 0) {
        velocity.y = Math.max(velocity.y - friction.y * deltaTime);
      }else {
        velocity.y = Math.max(velocity.y + friction.y * deltaTime);
      }
    }
    // Apply acceleration
    velocity.y += acceleration.y + acceleracion.y * deltaTime;
    // Make sure the velocity does not exceed the positive or negetive terminal velocity
    velocity.y = MathUtils.clamp(velocity.y, -terminalVelocity.y, terminalVelocity.y);
  }
  
  public abstract void render(SpriteBatch batch);
}









