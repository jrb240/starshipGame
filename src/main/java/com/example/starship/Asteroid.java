package com.example.starship;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.awt.*;

public class Asteroid {
    private int numVertices;
    double radius;
    double[] homeXs, homeYs;
    double tx, ty, rVel, xVel, yVel;
    double angleRadians;
    Canvas offscreen;
    GraphicsContext offscreenGC;
    WritableImage buffer;
    PixelReader reader;
    double vWidth, vHeight;
    private double maxVel;
    int z;

    public Asteroid(double w, double h, int newZ) {
        vWidth = w;
        vHeight = h;
        z = newZ;
        maxVel = 0.005;
        tx = Math.random() - 0.5;
        ty = Math.random() - 0.5;

        numVertices = (int) (Math.random() * 4 + 8);
        radius = (0.01 + Math.random() * 0.02);
        homeXs = new double[numVertices];
        homeYs = new double[numVertices];
        rVel = Math.random() * (2 * Math.PI / 360 * 2);
        xVel = Math.random() / 1000 - .0005;
        yVel = Math.random() / 1000 - .0005;
        angleRadians = 0.0;

        // generate points around the circle
        double angle = 0;
        double r;
        double step = (Math.PI * 2) / numVertices;
        for (int index = 0; index < numVertices; index++) {
            angle = index * step;
            r = radius + Math.random() * radius - radius / 5;
            homeXs[index] = r * Math.cos(angle);
            homeYs[index] = r * Math.sin(angle);
        }

        // selection bitmap
        offscreen = new Canvas(radius * w * 4, radius * w * 4);
        offscreenGC = offscreen.getGraphicsContext2D();
        buffer = new WritableImage((int) offscreen.getWidth(), (int) offscreen.getHeight());
        offscreenGC.setFill(javafx.scene.paint.Color.BLACK);
        offscreenGC.fillRect(0, 0, offscreen.getWidth(), offscreen.getHeight());
        offscreenGC.setFill(Color.WHITE);
        offscreenGC.translate(offscreen.getWidth() / 2, offscreen.getHeight() / 2);
        offscreenGC.scale(w, h);
        offscreenGC.fillPolygon(homeXs, homeYs, numVertices);
        offscreen.snapshot(null, buffer);
        reader = buffer.getPixelReader();
    }

    public void rotate() {
        angleRadians += rVel;
        angleRadians = angleRadians % (Math.PI * 2);
    }

    private double rotateX(double x, double y, double radians) {
        return Math.cos(radians) * x - Math.sin(radians) * y;
    }

    private double rotateY(double x, double y, double radians) {
        return Math.sin(radians) * x + Math.cos(radians) * y;
    }

    public void move() {
        tx += xVel;
        ty += yVel;
        if (tx > 0.55) tx = -0.55;
        if (tx < -0.55) tx = 0.55;
        if (ty > 0.55) ty = -0.55;
        if (ty < -0.55) ty = 0.55;
    }

    public int getNumVertices() {
        return numVertices;
    }

    public boolean contains(double rmXNorm, double rmYNorm) {
        // translate to the reference frame of the bitmap
        // so that clicking the centre of the asteroid
        // will be at the centre of the bitmap
        rmXNorm = rmXNorm - tx;
        rmYNorm = rmYNorm - ty;
        // un-rotate the point based on my rotation
        double rrmXNorm = rotateX(rmXNorm, rmYNorm, angleRadians * -1);
        double rrmYNorm = rotateY(rmXNorm, rmYNorm, angleRadians * -1);
        // de-normalize to world frame of reference and offset for buffer starting at 0,0
        rrmXNorm = rrmXNorm * vWidth + buffer.getWidth() / 2;
        rrmYNorm = rrmYNorm * vHeight + buffer.getHeight() / 2;
        // get integer pixel values for lookup
        int px = (int) rrmXNorm;
        int py = (int) rrmYNorm;
        // check the bitmap
        boolean result = false;
        if (px >= 0 && px < buffer.getWidth() && py >= 0 && py < buffer.getHeight()) {
            result = reader.getColor(px, py).equals(Color.WHITE);
        }
        return result;
    }
    public boolean isContained(double crNorm, double rmXNorm, double rmYNorm) {
        rmXNorm -= tx;
        rmYNorm -= ty;
        double rrmXNorm = rotateX(rmXNorm, rmYNorm, angleRadians * -1);
        double rrmYNorm = rotateY(rmXNorm, rmYNorm, angleRadians * -1);

        for (int i = 0; i < homeXs.length; i++) {
            if (Math.hypot(rrmXNorm - homeXs[i], rrmYNorm - homeYs[i]) < crNorm) {
                return true;
            }
        }
        return false;
    }
}
