package client.messages;

import java.util.List;
import java.util.Collections;
import tools.FilePrinter;
import client.messages.commands.ConsoleCommandExecute;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import client.messages.commands.ConsoleCommand;
import client.messages.commands.ConsoleCommandObject;
import java.util.HashMap;

public class ConsoleCommandProcessor
{
    private static final HashMap<String, ConsoleCommandObject> commands;
    
    private static void sendDisplayMessage(final String msg) {
        System.err.println("[sendDisplayMessage]錯誤:" + msg);
    }
    
    public static boolean processCommand(final String line) {
        final String[] splitted = line.split(" ");
        splitted[0] = splitted[0].toLowerCase();
        final ConsoleCommandObject co = (ConsoleCommandObject)ConsoleCommandProcessor.commands.get(splitted[0]);
        if (co == null) {
            sendDisplayMessage("沒有這個指令.");
            return true;
        }
        final int ret = co.execute(splitted);
        return true;
    }
    
    static {
        commands = new HashMap<String, ConsoleCommandObject>();
        final Class[] array;
        final Class<?>[] CommandFiles = (Class<?>[])(array = new Class[] { ConsoleCommand.class });
        for (final Class<?> clasz : array) {
        }
    }
}
