package net.ess3.commands;

import static net.ess3.I18n._;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;


public class Commandkill extends EssentialsCommand
{
	@Override
	protected void run(final CommandSender sender, final String commandLabel, final String[] args) throws Exception
	{
		if (args.length < 1)
		{
			throw new NotEnoughArgumentsException();
		}

		for (Player matchPlayer : server.matchPlayer(args[0]))
		{
			final EntityDamageEvent ede = new EntityDamageEvent(matchPlayer, sender instanceof Player && ((Player)sender).getName().equals(matchPlayer.getName()) ? EntityDamageEvent.DamageCause.SUICIDE : EntityDamageEvent.DamageCause.CUSTOM, 1000);
			server.getPluginManager().callEvent(ede);
			if (ede.isCancelled() && !sender.hasPermission("essentials.kill.force"))
			{
				continue;
			}

			matchPlayer.damage(1000);
			matchPlayer.setHealth(0);
			sender.sendMessage(_("kill", matchPlayer.getDisplayName()));
		}
	}
}