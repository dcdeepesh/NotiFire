package dec.discord.notifire;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class InputThread implements Runnable {
    @Override
    public void run() {
        try (BufferedReader reader =
                new BufferedReader(new InputStreamReader(System.in))) {
            String line;

            while (true) {
                line = reader.readLine().trim();
                if (!line.startsWith(R.PREFIX))
                    continue;

                // we got a command
                String command = line.substring(R.PREFIX.length());
                switch (command) {
                    case R.CMD_SHUTDOWN:
                        NotiFire.shutdown();
                        break;
                    
                    default:
                        // ignore invalid commands
                }
            }
        } catch (IOException e) {
            System.err.println("Exception in input thread: " + e);
        }
    }
}