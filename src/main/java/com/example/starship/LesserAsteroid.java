package com.example.starship;

public class LesserAsteroid extends DemoAsteroid {
    public LesserAsteroid(double x, double y,DemoAsteroid step) {
        super(x, y);
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
