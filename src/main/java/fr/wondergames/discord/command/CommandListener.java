package fr.wondergames.discord.command;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class CommandListener extends ListenerAdapter {

    private final CommandManager commandManager;

    public CommandListener(final CommandManager commandManager) {
        this.commandManager = commandManager;
    }

    @Override
    public void onSlashCommandInteraction(final SlashCommandInteractionEvent event) {
        this.commandManager.onCommand(event);
    }

}
