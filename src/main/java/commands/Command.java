package commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public abstract class Command {
    public String name;
    public abstract void commandRun(SlashCommandInteractionEvent event);
}
