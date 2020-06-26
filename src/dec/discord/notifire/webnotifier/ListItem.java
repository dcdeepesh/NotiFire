package dec.discord.notifire.webnotifier;

import java.util.List;

public class ListItem {
    private String desciption;
    private String URL;
    private boolean isNew;

    public ListItem(String description, String URL, boolean isNew) {
        this.desciption = description;
        this.URL = URL;
        this.isNew = isNew;
    }

    public void markAsRead() {
        isNew = false;
    }
    
    public static String formatAsString(List<ListItem> news) {
        StringBuilder sb = new StringBuilder();
        for (ListItem info : news)
            sb.append(info.getDescription() + "\n(" + info.getURL() + ")\n\n");

        return sb.toString();
    }
    
    public String getURL() {
        return URL;
    }

    public String getDescription() {
        return desciption;
    }

    public boolean isNew() {
        return isNew;
    }
}