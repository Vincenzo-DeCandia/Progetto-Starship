package org.sship.starship.commandpattern;

import org.sship.starship.player.AObject;

/**
 * The MoveLeftCommand class represents a concrete command in the command pattern.
 * It implements the ICommand interface and defines the behavior to move an object to the left.
 */
public class MoveLeftCommand implements ICommand {
    private final AObject object;

    /**
     * Constructs a MoveLeftCommand with the specified object to move left.
     *
     * @param object The object to move left
     */
    public MoveLeftCommand(AObject object) {
        this.object = object;
    }

    /**
     * Executes the command to move the object to the left.
     */
    @Override
    public void execute() {
        object.moveLEFT();
    }
}
