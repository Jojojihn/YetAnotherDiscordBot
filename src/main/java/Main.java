import database.DBManager;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.requests.GatewayIntent;
import commands.CommandManager;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Main {
    static JDA bot;
    static DBManager dbManager;
    public static void main(String[] args) throws Exception{
        URL tokenUrl = Main.class.getResource("/token");
        assert tokenUrl != null;
        String token = Files.readString(Paths.get(tokenUrl.toURI()));
        bot = JDABuilder.createDefault(token, GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_MESSAGE_REACTIONS, GatewayIntent.GUILD_VOICE_STATES, GatewayIntent.GUILD_EMOJIS_AND_STICKERS, GatewayIntent.SCHEDULED_EVENTS )
                .addEventListeners(new CommandManager())
                .build();
        bot.awaitReady();
        bot.getPresence().setActivity(Activity.competing("Amogus"));
        DBManager dbManager = new DBManager();
        dbManager.setup(bot);
        CommandManager.updateCommands(bot);
        
    }
}
