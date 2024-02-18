package org.sship.starship.player;

public abstract class AObject {
    protected int positionX, positionY;

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void moveUP() {
        positionY -= 1;
    }

    public void moveDOWN() {
        positionY += 1;
    }

    public void moveLEFT() {
        positionX -= 1;
    }

    public void moveRIGHT() {
        positionX += 1;
    }

    public AObject(int positionX, int positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
    }
}
