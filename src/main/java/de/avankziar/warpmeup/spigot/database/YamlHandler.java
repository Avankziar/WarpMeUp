package main.java.de.avankziar.warpmeup.spigot.database;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import main.java.de.avankziar.warpmeup.spigot.WarpMeUp;

public class YamlHandler 
{
	private WarpMeUp plugin;
	private File config = null;
	private YamlConfiguration cfg = new YamlConfiguration();
	private File language = null;
	private YamlConfiguration lgg = new YamlConfiguration();
	
	public YamlHandler(WarpMeUp plugin) 
	{
		this.plugin = plugin;
		mkdir();
		loadYamls();
	}
	
	public YamlConfiguration get()
	{
		return cfg;
	}
	
	public YamlConfiguration getL()
	{
		return lgg;
	}
	
	private void mkdir() 
	{
		config = new File(plugin.getDataFolder(), "config.yml");
		if(!config.exists()) 
		{
			WarpMeUp.log.info("Create config.yml...");
			plugin.saveResource("config.yml", false);
		}
		language = new File(plugin.getDataFolder(), "language.yml");
		if(!language.exists()) 
		{
			WarpMeUp.log.info("Create language.yml...");
			plugin.saveResource("language.yml", false);
		}
	}
	
	public void saveConfig() 
	{
	    try 
	    {
	    	WarpMeUp.log.info("Save config.yml...");
	        cfg.save(config);
	    } catch (IOException e) 
	    {
	    	WarpMeUp.log.severe("Could not save the config.yml! Error: " + e.getMessage());
			e.printStackTrace();
	    }
	}
	
	public void saveLanguage() 
	{
	    try 
	    {
	    	WarpMeUp.log.info("Save language.yml...");
	        lgg.save(language);
	    } catch (IOException e) 
	    {
	    	WarpMeUp.log.severe("Could not save the language.yml! Error: " + e.getMessage());
			e.printStackTrace();
	    }
	}
	
	public void loadYamls() 
	{
		try 
		{
			WarpMeUp.log.info("Load config.yml...");
			cfg.load(config);
		} catch (IOException | InvalidConfigurationException e) 
		{
			WarpMeUp.log.severe("Could not load the config file! You need to regenerate the config! Error: " + e.getMessage());
			e.printStackTrace();
		}
		try 
		{
			lgg.load(language);
		} catch (IOException | InvalidConfigurationException e) 
		{
			throw new RuntimeException(e);
		}
	}
}
