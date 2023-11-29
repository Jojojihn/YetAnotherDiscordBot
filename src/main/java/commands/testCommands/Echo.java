package commands.testCommands;

import commands.Command;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class Echo extends Command {
    public Echo(){
        name = "echo";
        description = "Echoes the message";
    }
    public void commandRun(SlashCommandInteractionEvent event){
        event.reply("Echo").queue();
    }
}
