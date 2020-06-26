package dec.discord.notifire.webnotifier;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class JEENotifier extends Notifier {
    private final String HOMEPAGE_URL =
        "https://jeemain.nta.nic.in/webinfo/public/home.aspx";
    private final String ELEMENT_ID = 
        "ctl00_CurrentEvents_BLinks";

    private static JEENotifier instance = new JEENotifier();
    
    public static JEENotifier getInstance() {
        return instance;
    }

    @Override
    protected void update() {
        ArrayList<ListItem> news = new ArrayList<>();
        
        Elements items = null;
        try {
            loadFile();
            items = Jsoup
                .connect(HOMEPAGE_URL)
                .get()
                .getElementById(ELEMENT_ID)
                .children();
        } catch (IOException | NullPointerException e) {
            System.out.println("Error in JEENotifier: " + e);
            System.exit(1);
        }
        
        Element a;
        for (Element item : items) {
            a = item.child(0);

            String newsURL = a.attr("href");
            String desc = a.ownText();
            boolean isNew = !file.contains(desc + newsURL);

            news.add(new ListItem(desc, newsURL, isNew));
        }

        this.news = news;
    }
}