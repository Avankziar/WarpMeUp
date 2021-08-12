package main.java.de.avankziar.warpmeup.spigot;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import main.java.de.avankziar.warpmeup.spigot.database.MysqlInterface;
import main.java.de.avankziar.warpmeup.spigot.database.MysqlSetup;
import main.java.de.avankziar.warpmeup.spigot.database.YamlHandler;

public class WarpMeUp extends JavaPlugin
{
	public static Logger log;
	public static String pluginName = "WarpMeUp";
	private static YamlHandler yamlHandler;
	private static MysqlSetup databaseHandler;
	private static MysqlInterface mysqlinterface;
	private static BackgroundTask backgroundtask;
	private static Utility utility;
	
	public void onEnable()
	{
		log = getLogger();
		utility = new Utility(this);
		yamlHandler = new YamlHandler(this);
		if(yamlHandler.get().getString("mysql.status").equalsIgnoreCase("true"))
		{
			mysqlinterface = new MysqlInterface(this);
			databaseHandler = new MysqlSetup(this);
		} else
		{
			log.severe("MySQL is not set in the Plugin "+pluginName+"!");
			Bukkit.getPluginManager().getPlugin(pluginName).getPluginLoader().disablePlugin(this);
			return;
		}
		CommandSetup();
		ListenerSetup();
		//this.getServer().getMessenger().registerIncomingPluginChannel(this, "punisher:punisherout", new PluginListener(this));
	    //this.getServer().getMessenger().registerOutgoingPluginChannel(this, "punisher:punisherin");
	}
	
	public void onDisable()
	{
		Bukkit.getScheduler().cancelTasks(this);
		HandlerList.unregisterAll(this);
		if(yamlHandler.get().getString("mysql.status").equalsIgnoreCase("true"))
		{
			if (databaseHandler.getConnection() != null) 
			{
				//backgroundtask.onShutDownDataSave();
				databaseHandler.closeConnection();
			}
		}
		
		log.info(pluginName + " is disabled!");
	}
	
	public YamlHandler getYamlHandler() 
	{
		return yamlHandler;
	}
	
	public MysqlSetup getDatabaseHandler() 
	{
		return databaseHandler;
	}
	
	public MysqlInterface getMysqlInterface()
	{
		return mysqlinterface;
	}
	
	public BackgroundTask getBackgroundTask()
	{
		return backgroundtask;
	}
	
	public Utility getUtility()
	{
		return utility;
	}
	
	public void CommandSetup()
	{
		//getCommand("cmd").setExecutor(new CMD());
	}
	
	public void ListenerSetup()
	{
		PluginManager pm = getServer().getPluginManager();
		//pm.registerListener(this, new EVENT());
	}
}
