package fr.wondergames.discord;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import java.util.Arrays;
import java.util.Scanner;

public class Main {


    protected static final JDA jda = buildJDA();

    private static JDA buildJDA() {
        final JDABuilder jdaBuilder;

        if (System.getenv("DISCORD_TOKEN") == null) {
            System.err.println("DISCORD_TOKEN environment variable need to be defined.");
            return null;
        }
        jdaBuilder = JDABuilder.createDefault(System.getenv("DISCORD_TOKEN"));
        jdaBuilder.setEnabledIntents(GatewayIntent.getIntents(GatewayIntent.ALL_INTENTS));
        jdaBuilder.enableCache(Arrays.asList(CacheFlag.values()));
        jdaBuilder.setAutoReconnect(true);
        return jdaBuilder.build();
    }

    public static void main(final String[] args) {
        final Scanner scanner;

        if (Main.jda == null)
            return;
        try {
            Main.jda.awaitReady();
        } catch (InterruptedException exception) {
            exception.printStackTrace();
            return;
        }
        scanner = new Scanner(System.in);
        for (; scanner.hasNext(); scanner.next());
        System.out.println("Stopping...");
        Main.jda.shutdown();
    }

}
