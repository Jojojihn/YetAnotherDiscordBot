package commands;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import commands.testCommands.*;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CommandManager extends ListenerAdapter {

    private static final Logger logger
            = LoggerFactory.getLogger(CommandManager.class);
    static Map<String, Command> commands = new HashMap<>();
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event){
        String commandName = event.getName();
        if(commands.containsKey(commandName)){
            commands.get(commandName).commandRun(event);
        }else {
            event.reply("Command not implemented yet").setEphemeral(true).queue();
            logger.warn("Tried to run unimplemented Command: " + commandName);
        }
    }

    public static void updateCommands(JDA bot) throws InterruptedException {
        registerCommands();
        //Only guild commands for now since they are updated faster for better debugging
        for(Guild g: bot.getGuilds()){
            List<net.dv8tion.jda.api.interactions.commands.Command> guildCommands = g.retrieveCommands().complete();
            for(net.dv8tion.jda.api.interactions.commands.Command c: guildCommands){
                if(!commands.containsKey(c.getName())) {
                    logger.warn("Deleting obsolete guild command: " + c.getName());
                    g.deleteCommandById(c.getId()).queue();
                }else {
                    g.deleteCommandById(c.getId()).queue();
                }
            }
            bot.awaitReady();
            g.updateCommands().addCommands(
                    Commands.slash("ping", "Pong!"),
                    Commands.slash("echo", "Echoes the message")
            ).queue();
        }
        logger.info("Updated all commands");
    }

    public static void registerCommands(){
        PingPong pingPong = new PingPong();
        commands.put(pingPong.name,pingPong);
        Echo echo = new Echo();
        commands.put(echo.name,echo);
    }
}
