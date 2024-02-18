package org.sship.starship.commandpattern;

/**
 * The MovementInvoker class acts as an invoker in the command pattern.
 * It invokes the command set to it by calling the execute method.
 */
public class MovementInvoker {
    private ICommand command;

    /**
     * Sets the command to be invoked.
     *
     * @param command The command to be invoked
     */
    public void setCommand(ICommand command) {
        this.command = command;
    }

    /**
     * Invokes the set command.
     */
    public void invokeCommand() {
        command.execute();
    }
}
