package me.tianyin.minecrafthacks;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

public class ArrowListener implements Listener {

  @EventHandler
  public void onProjectileHit(ProjectileHitEvent event) {
    Entity target = event.getHitEntity();
    EntityType entityType = target.getType();
    if (entityType.equals(EntityType.PLAYER)) {
      Bukkit.broadcastMessage(target.getName() + " does not know how to strafe");
    }
  }
}
