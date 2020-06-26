package dec.discord.notifire;

import discord4j.core.GatewayDiscordClient;
import discord4j.core.DiscordClientBuilder;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.channel.Channel;

public class NotiFire {
    private static GatewayDiscordClient client;

    public static void main(String[] args) throws Exception {
        System.out.println("INITIALIZING...");

        // login and initialize DM manager
        client = DiscordClientBuilder
            .create(R.TOKEN)
            .build()
            .login().block();

        DMManager.init(client);
        client.getEventDispatcher()
            .on(MessageCreateEvent.class)
            .subscribe(event -> {
                if (event.getMessage().getChannel().block().getType() == Channel.Type.DM
                    && !event.getMessage().getAuthor().get().isBot())
                        DMManager.newDM(event);
            });

        // init and run threads
        Thread updateThread = new Thread(new UpdateThread());
        updateThread.setDaemon(true);
        updateThread.start();
        Thread inputThread = new Thread(new InputThread());
        inputThread.setDaemon(true);
        inputThread.start();

        System.out.println("BOT READY");
        client.onDisconnect().block();
    }

    public static void shutdown() {
        System.out.println("SHUTDOWN SIGNAL");
        
        client.logout().block();
        System.exit(0);
    }
}
