package me.tianyin.minecrafthacks;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;


public class ArrowDamageEvent implements Listener {


  @EventHandler
  public void onProjectileHit(ProjectileHitEvent event) {

    Entity target = event.getHitEntity();
    if (target == null) {return;}

    Projectile proj = event.getEntity();

    EntityType entityType = target.getType();
    EntityType projType = proj.getType();

    if (!(proj.getShooter() instanceof Player)) {return;} // Shooter must be player
    Entity shooter = (Entity) proj.getShooter();
    EntityType shooterType = shooter.getType();

    if (entityType.equals(EntityType.PLAYER)
        && projType.equals(EntityType.ARROW)
        && shooterType.equals(EntityType.PLAYER)) {

      event.setCancelled(true);
      double targetHealth = ((Player) target).getHealth();
      double targetMaxHealth = ((Player) target).getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
      //Bukkit.broadcastMessage(target.getName() + " heal. Current health: "+targetHealth);

      double newHealth = targetHealth + targetMaxHealth*0.2;
      ((Player) target).setHealth(Math.min(newHealth, targetMaxHealth));
    }
  }
}
