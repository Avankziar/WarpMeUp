package main.java.de.avankziar.warpmeup.spigot;

import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;

public class Utility 
{
	private WarpMeUp plugin;
	
	public Utility(WarpMeUp plugin)
	{
		this.plugin = plugin;
	}

	public String tl(String path)
	{
		return ChatColor.translateAlternateColorCodes('&', path);
	}
	
	public TextComponent tc(String s)
	{
		return new TextComponent(s);
	}
	
	public void sendMessage(Player p, String path)
	{
		p.spigot().sendMessage(tc(tl(path)));
	}
}
