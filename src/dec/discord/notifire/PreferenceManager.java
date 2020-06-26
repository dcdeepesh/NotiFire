package dec.discord.notifire;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;

public class PreferenceManager {
    /**
     * Loads token from
     * {@code &lt;userHome&gt;/.dec/notifire/token}.
     * Make sure the file is present and contains a valid token.
     * 
     * @return The token
     */
    public static String loadToken() {
        File file = Paths.get(System.getProperty("user.home"),
            ".dec", "notifire", "token").toFile();

        if (!file.exists()) {
            System.err.println("Error: No token found");
            System.exit(1);
        }

        String token = "";
        try (BufferedReader in = new BufferedReader(new FileReader(file))) {
            token = in.readLine();
            if (token == null || token.trim().equals(""))
                throw new IOException();    // to trigger the exception handler
        } catch (IOException e) {
            System.err.println("Error retrieving token");
            System.exit(1);
        }

        return token;
    }
}