package me.tianyin.minecrafthacks;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Queue;

public class Main extends JavaPlugin {
  HashMap<Player, Queue<Long>> hp;
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

    hp = new HashMap<>();
    Listener crouchListener = new CrouchTeleportListener(hp);

    getServer().getPluginManager().registerEvents(arrowDamageEvent, this);
    getServer().getPluginManager().registerEvents(kirikoHackListener, this);
    getServer().getPluginManager().registerEvents(snowBallListener, this);
    getServer().getPluginManager().registerEvents(crouchListener, this);
  }

  @Override
  public void onDisable() {
    Bukkit.getLogger().info(ChatColor.RED + "Disabled " + this.getName());
  }
}
