package dec.discord.notifire.webnotifier;

import java.util.ArrayList;

public class CommonNotifier extends Notifier {
    private Notifier[] notifiers = new Notifier[] {
        JEENotifier.getInstance()
    };

    private static CommonNotifier instance = new CommonNotifier();

    public static CommonNotifier getInstance() {
        return instance;
    }

    @Override
    public ArrayList<ListItem> getAllInfo() {
        ArrayList<ListItem> allInfo = new ArrayList<>();
        for (Notifier notifier : notifiers)
            allInfo.addAll(notifier.getAllInfo());
        
        return allInfo;
    }

    @Override
    public boolean hasNewInfo() {
        for (Notifier notifier : notifiers)
            if (notifier.hasNewInfo())
                return true;

        return false;
    }

    @Override
    public ArrayList<ListItem> getNewInfo() {
        ArrayList<ListItem> newInfo = new ArrayList<>();
        for (Notifier notifier : notifiers)
            newInfo.addAll(notifier.getAllInfo());
        
        return newInfo;
    }

    @Override
    public void markAllAsRead() {
        for (Notifier notifier : notifiers)
            notifier.markAllAsRead();
    }

    @Override
    public void clearCache() {
        for (Notifier notifier : notifiers)
            notifier.clearCache();
    }

    @Override
    protected void update() {}

    public void upateInfo() {
        for (Notifier notifier : notifiers)
            notifier.update();
    }
}