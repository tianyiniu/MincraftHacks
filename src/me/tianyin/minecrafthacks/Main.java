package me.tianyin.minecrafthacks;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.data.type.Snow;
import org.bukkit.entity.Snowball;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
  public static void main(String[] args) {

  }

  @Override
  public void onEnable() {
    Bukkit.getLogger().info(ChatColor.GREEN + "Enabled " + this.getName());
    //getServer().getPluginManager().registerEvents(new ArrowListener(), this);

    KirikoTimeRanges playerTimes = new KirikoTimeRanges();
    Listener arrowDamageEvent = new ArrowDamageEvent();
    Listener kirikoHackListener = new KirikoHackListener(playerTimes);
    Listener snowBallListener = new SnowballDamageListener(playerTimes);

    getServer().getPluginManager().registerEvents(arrowDamageEvent, this);
    getServer().getPluginManager().registerEvents(kirikoHackListener, this);
    getServer().getPluginManager().registerEvents(snowBallListener, this);
  }

  @Override
  public void onDisable() {
    Bukkit.getLogger().info(ChatColor.RED + "Disabled " + this.getName());
  }
}
