package org.sship.starship.commandpattern;

import org.sship.starship.player.AObject;

/**
 * The MoveDownCommand class represents a concrete command in the command pattern.
 * It implements the ICommand interface and defines the behavior to move an object down.
 */
public class MoveDownCommand implements ICommand {
    private final AObject object;

    /**
     * Constructs a MoveDownCommand with the specified object to move down.
     *
     * @param object The object to move down
     */
    public MoveDownCommand(AObject object) {
        this.object = object;
    }

    /**
     * Executes the command to move the object down.
     */
    @Override
    public void execute() {
        object.moveDOWN();
    }
}
