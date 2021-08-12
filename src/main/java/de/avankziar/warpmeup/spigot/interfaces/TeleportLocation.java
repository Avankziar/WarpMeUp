package main.java.de.avankziar.warpmeup.spigot.interfaces;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class TeleportLocation 
{
	private String servername;
	private World world;
	private double x;
	private double y;
	private double z;
	private float yaw;
	private float pitch;
	
	public TeleportLocation(String servername, World world, double x, double y, double z, float yaw, float pitch)
	{
		setServername(servername);
		setWorld(world);
		setX(x);
		setY(y);
		setX(x);
		setYaw(yaw);
		setPitch(pitch);
	}
	
	public TeleportLocation(String servername, Location location)
	{
		setServername(servername);
		setWorld(location.getWorld());
		setX(location.getX());
		setY(location.getY());
		setX(location.getZ());
		setYaw(location.getYaw());
		setPitch(location.getPitch());
	}
	
	public TeleportLocation getTeleportLocation(String s)
	{
		String[] data = s.split(";");
		return new TeleportLocation(data[0], Bukkit.getWorld(data[1]),
				Double.parseDouble(data[2]), Double.parseDouble(data[3]), Double.parseDouble(data[4]),
				Float.parseFloat(data[5]), Float.parseFloat(data[6]));
	}
	
	public Location getLocation(TeleportLocation tl)
	{
		return new Location(tl.getWorld(), tl.getX(), tl.getY() , tl.getZ(), tl.getYaw(), tl.getPitch());
	}
	
	public String getStringLocation(TeleportLocation tl)
	{
		return "%server%;%world%;%x%;%y%;%z%;%yaw%;%pitch%"
				.replaceAll("%server%", String.valueOf(tl.getServername()))
				.replaceAll("%world%", String.valueOf(tl.getWorld().getName()))
				.replaceAll("%x%", String.valueOf(tl.getX()))
				.replaceAll("%y%", String.valueOf(tl.getY()))
				.replaceAll("%z%", String.valueOf(tl.getZ()))
				.replaceAll("%yaw%", String.valueOf(tl.getYaw()))
				.replaceAll("%pitch%", String.valueOf(tl.getPitch()));
	}

	public String getServername() 
	{
		return servername;
	}

	public void setServername(String servername) 
	{
		this.servername = servername;
	}

	public World getWorld() 
	{
		return world;
	}

	public void setWorld(World world) 
	{
		this.world = world;
	}

	public double getX() 
	{
		return x;
	}

	public void setX(double x) 
	{
		this.x = x;
	}

	public double getY() 
	{
		return y;
	}

	public void setY(double y) 
	{
		this.y = y;
	}

	public double getZ() {
		return z;
	}

	public void setZ(double z) 
	{
		this.z = z;
	}

	public float getYaw() 
	{
		return yaw;
	}

	public void setYaw(float yaw) 
	{
		this.yaw = yaw;
	}

	public float getPitch() 
	{
		return pitch;
	}

	public void setPitch(float pitch) 
	{
		this.pitch = pitch;
	}
}
