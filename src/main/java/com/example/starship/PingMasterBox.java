package com.example.starship;

public interface PingMasterBox {
    //TODO: think of compression method for this
    //bullet movement
    public void passBulletLeft(EnergyBullet bullet);
    public void passBulletRight(EnergyBullet bullet);
    public void passBulletUp(EnergyBullet bullet);
    public void passBulletDown(EnergyBullet bullet);
    //asteroid movement
    public void passAsteroidLeft(DemoAsteroid asteroid);
    public void passAsteroidRight(DemoAsteroid asteroid);
    public void passAsteroidUp(DemoAsteroid asteroid);
    public void passAsteroid(DemoAsteroid asteroid);
}
