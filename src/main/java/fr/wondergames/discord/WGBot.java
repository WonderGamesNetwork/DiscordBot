package fr.wondergames.discord;

import fr.wondergames.discord.command.CommandListener;
import fr.wondergames.discord.command.CommandManager;
import net.dv8tion.jda.api.interactions.commands.Command;

public class WGBot {

    public static final CommandManager COMMAND_MANAGER = new CommandManager(Main.JDA);

    private static void deleteCommands(final Runnable afterDelete) {
        Main.JDA.retrieveCommands().queue(commands -> {
            for (final Command command : commands)
                command.delete().queue();
            if (afterDelete != null)
                afterDelete.run();
        });
    }

    protected static void botMainSetup() {
        Main.JDA.addEventListener(new CommandListener(WGBot.COMMAND_MANAGER)); // Register command listener for use command manager.
        deleteCommands(() -> {
            // Register new commands.
        });
    }

}
