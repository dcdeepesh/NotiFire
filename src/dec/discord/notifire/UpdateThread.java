package dec.discord.notifire;

import dec.discord.notifire.webnotifier.CommonNotifier;
import dec.discord.notifire.webnotifier.ListItem;

public class UpdateThread implements Runnable {

    private CommonNotifier notifier = CommonNotifier.getInstance();

    @Override
    public void run() {
        while (true) {
            notifier.upateInfo();
            if (notifier.hasNewInfo())
                DMManager.dm("YOU GOT NEWS!\n" +
                    ListItem.formatAsString(notifier.getNewInfo()));

            try {
                Thread.sleep(R.UPDATE_INTERVAL);
            } catch (InterruptedException e) {
                System.err.println("Update thread exception: " + e);
            }
        }
    }
}