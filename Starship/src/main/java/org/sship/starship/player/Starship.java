package org.sship.starship.player;

import javafx.scene.text.Text;

public class Starship extends AObject {

    private boolean dead;

    public Starship() {
        super(19,39);
        dead = false;
    }

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }
}
