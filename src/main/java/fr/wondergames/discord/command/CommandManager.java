package fr.wondergames.discord.command;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import net.dv8tion.jda.internal.interactions.CommandDataImpl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class CommandManager {

    private final JDA jda;
    private final Map<Long, Consumer<SlashCommandInteractionEvent>> executors;


    public CommandManager(final JDA jda) {
        this.jda = jda;
        this.executors = new ConcurrentHashMap<Long, Consumer<SlashCommandInteractionEvent>>();
    }

    public void createSlashCommand(final String name, final String description,
                                   final Consumer<SlashCommandData> commandConsumer,
                                   final Consumer<SlashCommandInteractionEvent> executorConsumer, final BiConsumer<Command, Throwable> registerResult) {
        final CommandDataImpl commandData;

        if (commandConsumer == null || executorConsumer == null)
            return;
        commandData = new CommandDataImpl(name, description);
        commandConsumer.accept(commandData);
        this.jda.upsertCommand(commandData).queue(command -> {
            this.executors.put(command.getIdLong(), executorConsumer);
            if (registerResult != null)
                registerResult.accept(command, null);
        }, throwable -> {
            if (registerResult != null)
                registerResult.accept(null, throwable);
        });
    }

    public void onCommand(final SlashCommandInteractionEvent event) {
        final Consumer<SlashCommandInteractionEvent> executor;

        if (event == null)
            return;
        executor = this.executors.get(event.getCommandIdLong());
        if (executor != null)
            executor.accept(event);
    }

}
