package com.example.starship;

public class LesserAsteroid extends DemoAsteroid {
    public LesserAsteroid(double x, double y,double canvasWidth, double canasHeight, DemoAsteroid step) {
        super(x, y,canvasWidth,canasHeight);
        switch (step.getAsteroidSize()){
            case BIG -> {
                this.setAsteroidSize(Size.MED);
            }
            case MED -> {
                this.setAsteroidSize(Size.SMALL);
            }
            case SMALL -> {
                this.setAsteroidSize(Size.BIG);
            }
        }
    }
}
