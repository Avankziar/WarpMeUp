package main.java.de.avankziar.warpmeup.bungee;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;
import java.util.logging.Level;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;

public class WarpMeUp extends Plugin implements Listener
{	
	public void onEnable()
	{
        getProxy().registerChannel("warpmeup:warpmeupin");
        getProxy().registerChannel("warpmeup:warpmeupout");
        getProxy().getPluginManager().registerListener(this, this);
	}
	
	@EventHandler
	 public void onPluginMessage(PluginMessageEvent ev) 
	 {
		 if (ev.getTag().equals("warpmeup:warpmeupin")) 
		 {
			 ByteArrayInputStream streamin = new ByteArrayInputStream(ev.getData());
		     DataInputStream in = new DataInputStream(streamin);
		     try 
		     {
		        String[] s = in.readUTF().split("µ");
		        String Category = s[0];
		        String PlayerUUID = s[1];
				if(getProxy().getPlayer(UUID.fromString(PlayerUUID)) == null)
				{
					return;
				}
				if(Category.equals("playerteleport")) //Tpa
				{
					String ToServer = s[2];
			        String location = s[3];
					ProxiedPlayer p = getProxy().getPlayer(UUID.fromString(PlayerUUID));
					ServerInfo server = ProxyServer.getInstance().getServerInfo(ToServer);
					if(server==null)
					{
						return;
					}
					if(!p.getServer().getInfo().getName().equals(server.getName())){p.connect(server);}
					String µ = "µ";
					String message = Category+µ+PlayerUUID+µ+ToServer+µ+location;
					ByteArrayOutputStream streamout = new ByteArrayOutputStream();
			        DataOutputStream out = new DataOutputStream(streamout);
			        String msg = message;
			        try {
						out.writeUTF(msg);
					} catch (IOException e) {
						e.printStackTrace();
					}
				    server.sendData("punisher:punisherout", streamout.toByteArray());
				    return;
				}
				if(Category.equals("warp")) //warp
				{
					
				}
				if(Category.equals("home")) //home
				{
					
				}
				if(Category.equals("back"))
				{
					
				}
			} catch (IOException e) 
		    {
				getLogger().log(Level.INFO, e, () -> e.getMessage());
			}
		    return;
		 } else
		 {
			 return;
		 }
	}
    
    public static String tl(String path)//FIN
	{
		return ChatColor.translateAlternateColorCodes('&', path);
	}

	public static TextComponent tc(String s)//FIN
	{
		return new TextComponent(s);
	}
}
