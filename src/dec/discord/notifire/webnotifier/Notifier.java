package dec.discord.notifire.webnotifier;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class Notifier {
    private final String COMMON_FILE_NAME = "notifierDB_common";

    protected List<ListItem> news;
    protected String file;

    protected abstract void update();

    public Notifier() {
        update();
    }

    public List<ListItem> getAllInfo() {
        return news;
    }

    public boolean hasNewInfo() {
        for (ListItem info : getAllInfo())
            if (info.isNew())
                return true;

        return false;
    }

    public List<ListItem> getNewInfo() {
        List<ListItem> newInfo = new ArrayList<>();

        for (ListItem info : news)
            if (info.isNew())
                newInfo.add(info);

        return newInfo;
    }

    public void markAllAsRead() {
        FileWriter writer = null;
        try {
            File file = new File(getFileName());
            if (!file.exists())
                file.createNewFile();

            writer = new FileWriter(file, true);
            for (ListItem info : getNewInfo()) {
                writer.write(info.getDescription() + info.getURL());
                info.markAsRead();
            }

        } catch (IOException e) {
            System.err.println("Error marking all as read: "  + e);

        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    System.err.println("Error closing file writer");
                }
            }    
        }
    }

    protected String getFileName() {
        return COMMON_FILE_NAME;
    }

    protected void loadFile() throws IOException {
        File fileObj = new File(getFileName());
        if (!fileObj.exists())
            fileObj.createNewFile();

        StringBuilder sb = new StringBuilder();
        FileInputStream fis = new FileInputStream(fileObj);
        
        int c;
        while ((c = fis.read()) != -1)
            sb.append((char)c);

        fis.close();
        file = sb.toString();
    }

    public void clearCache() {
        new File(getFileName()).delete();

        // update to change the objects in memory
        update();
    }
}