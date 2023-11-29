package commands.testCommands;

import commands.Command;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class PingPong extends Command{
    public PingPong(){
        name = "ping";
    }
    public void commandRun(SlashCommandInteractionEvent event){
        event.reply("Pong").queue();
    }
}
