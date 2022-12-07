package command;



/**
 * The manager of commands.
 *
 */
public class CommandManager {

    public CommandManager() {
    }

    public String executeCommand(Command command) {
        return command.execute();
    }
}
