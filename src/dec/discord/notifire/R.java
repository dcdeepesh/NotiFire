package dec.discord.notifire;

public class R {
    /**
     * Token is loaded from the file 
     * {@code &lt;userHome&gt;/.dec/notifire/token}.
     * Make sure the file is present and contains a valid token.
     */
    public static final String TOKEN = PreferenceManager.loadToken();
    public static final String USER_ID = "522724872695185418";
    
    public static final String PREFIX = "!";
    public static final String CMD_ALL = "all";
    public static final String CMD_NEW = "new";
    public static final String CMD_MARK_AS_READ = "mark";
    public static final String CMD_CLR_CACHE = "clear";
    public static final String CMD_SHUTDOWN = "quit";
    
    public static final long UPDATE_INTERVAL = 600000;
}