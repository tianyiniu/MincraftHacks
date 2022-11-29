package me.tianyin.minecrafthacks;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class KirikoTimeRanges {
  private HashMap<Player, Long> playerTimes;

  public KirikoTimeRanges() {
    playerTimes = new HashMap<>();
  }

  public void addPlayerTime(Player p, long t) {
    playerTimes.put(p, t);
  }

  public long getPlayerTime(Player p) {
    return playerTimes.get(p);
  }

  public void printInfo() {
    for (Player p : playerTimes.keySet()) {
      Bukkit.broadcastMessage("Player: " + p.getName() + " Time: " + playerTimes.get(p));
    }
  }

  public boolean isInvulnerable(Player p, long time) {
    if (!playerTimes.containsKey(p)) {return false;}
    return time <= getPlayerTime(p);
  }
}
