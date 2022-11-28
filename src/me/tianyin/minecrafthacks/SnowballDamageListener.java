package me.tianyin.minecrafthacks;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ProjectileHitEvent;

public class SnowballDamageListener implements Listener {

  KirikoTimeRanges playerTimes;

  public SnowballDamageListener(KirikoTimeRanges playerTimes) {
    this.playerTimes = playerTimes;
  }

  @EventHandler
  public void onSnowballHit(EntityDamageEvent event) {
    Entity target = event.getEntity();
    if (!target.getType().equals(EntityType.PLAYER)) {
      return;
    }
    Player p = (Player) target;
    if (playerTimes.isInvulnerable(p, target.getWorld().getGameTime())) {
      event.setCancelled(true);
      Bukkit.broadcastMessage("Snowball damage cancelled");
    }
  }
}
