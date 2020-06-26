package dec.discord.notifire;

import dec.discord.notifire.webnotifier.CommonNotifier;
import dec.discord.notifire.webnotifier.ListItem;
import discord4j.common.util.Snowflake;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.channel.PrivateChannel;

public class DMManager {
    private static PrivateChannel dmChannel;
    
    public static void init(GatewayDiscordClient client) {
        dmChannel = client
            .getUserById(Snowflake.of(R.USER_ID)).block()
            .getPrivateChannel().block();
    }

    public static void dm(String msg) {
        String shortMsg = msg, rest = "";

        while (shortMsg.length() > 0) {
            while (shortMsg.length() > 2000) {
                int lastIndex = shortMsg.lastIndexOf("\n");
                if (lastIndex == -1)
                    lastIndex = 2000;

                shortMsg = msg.substring(0, lastIndex);
                rest = msg.substring(lastIndex+1, msg.length());
            }

            dmChannel.createMessage(shortMsg).block();
            shortMsg = rest;
            rest = "";
        }
    }

    public static void newDM(MessageCreateEvent event) {
        String msg = event.getMessage().getContent().toLowerCase().trim();
        if (!msg.startsWith(R.PREFIX))
            return;
        
        CommonNotifier notifier = CommonNotifier.getInstance();

        String command = msg.substring(R.PREFIX.length()), reply="";
        switch (command) {
            case R.CMD_MARK_AS_READ:
                notifier.markAllAsRead();
                reply = "Marked all news as read";
                break;
            
            case R.CMD_CLR_CACHE:
                notifier.clearCache();
                reply = "Cache cleared";
                break;

            case R.CMD_NEW:
                if (notifier.hasNewInfo())
                    reply = ListItem.formatAsString(notifier.getNewInfo());
                else
                    reply = "No new info";
                break;

            case R.CMD_ALL:
                reply = ListItem.formatAsString(notifier.getAllInfo());
                if (reply.equals(""))
                    reply = "No news";
                break;

            case R.CMD_SHUTDOWN:
                NotiFire.shutdown();
                break;
                
            default:
                reply = "Invalid command";
        }

        dm(reply);
    }

    private DMManager() {}
}