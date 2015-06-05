package pl.kamilkime.ytguilds.cmds;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class MainCmd implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("g")){
			if(args.length == 0){
				
			} else{
				if(args[0].equalsIgnoreCase("zaloz") || args[0].equalsIgnoreCase("create")){
					new CmdZaloz().zaloz(sender, cmd, label, args);
				}
				else if(args[0].equalsIgnoreCase("usun") || args[0].equalsIgnoreCase("disband")){
					//new CmdUsun().usun(sender, cmd, label, args);
				}
			}
		}
		return false;
	}
}