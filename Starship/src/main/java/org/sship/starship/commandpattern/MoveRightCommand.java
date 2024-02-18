package org.sship.starship.commandpattern;

import org.sship.starship.player.AObject;

/**
 * The MoveRightCommand class represents a concrete command in the command pattern.
 * It moves the associated object to the right when executed.
 */
public class MoveRightCommand implements ICommand {
    private AObject object;

    /**
     * Constructs a MoveRightCommand with the specified object.
     *
     * @param object The object to be moved right
     */
    public MoveRightCommand(AObject object) {
        this.object = object;
    }

    /**
     * Executes the move right command.
     */
    @Override
    public void execute() {
        object.moveRIGHT();
    }
}
