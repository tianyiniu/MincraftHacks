package me.tianyin.minecrafthacks;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

import java.util.List;

public class KirikoHackListener implements Listener {

  KirikoTimeRanges playerTimes;
  int INVULNERABILITY_TIME;

  public KirikoHackListener(KirikoTimeRanges playerTimes) {
    this.playerTimes = playerTimes;
    this.INVULNERABILITY_TIME = 40;
  }

  @EventHandler
  public void onProjectileHit(ProjectileHitEvent event) {

//    Entity target = event.getHitEntity();
//    EntityType entityType = target.getType();

    Projectile proj = event.getEntity();
    EntityType projType = proj.getType();

    Entity shooter = (Entity) proj.getShooter();
    EntityType shooterType = shooter.getType();

    Location hitLoc;

    if (projType.equals(EntityType.SNOWBALL) && shooterType.equals(EntityType.PLAYER)) {
      World hitWorld = proj.getWorld();
      if (event.getHitBlock() != null) {
        Block hitBlock = event.getHitBlock();
        hitLoc = hitBlock.getLocation();
        Bukkit.broadcastMessage("Kiriko E hit block " + hitBlock.getType());
      } else if (event.getEntity() != null) {
        Entity hitEntity = event.getHitEntity();
        hitLoc = hitEntity.getLocation();
        Bukkit.broadcastMessage("Kiriko E hit entity " + hitEntity.getType());
      } else {
        return;
      }

      List<Entity> entities = hitWorld.getEntities();
      for (Entity entity:entities) {
        if (entity.getType().equals(EntityType.PLAYER) && hitLoc.distance(entity.getLocation()) < 3.0) {
          Bukkit.broadcastMessage(entity.getName() + " has been affect by Kiriko E");
          //Bukkit.broadcastMessage("Current world time " + hitWorld.getGameTime());
          playerTimes.addPlayerTime((Player) entity, hitWorld.getGameTime()+INVULNERABILITY_TIME);
          //playerTimes.printInfo();
        }
      }


//      event.setCancelled(true);
//      double targetHealth = ((Player) target).getHealth();
//      double targetMaxHealth = ((Player) target).getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
//      //Bukkit.broadcastMessage(target.getName() + " heal. Current health: "+targetHealth);
//
//      double newHealth = targetHealth + targetMaxHealth*0.2;
//      ((Player) target).setHealth(Math.min(newHealth, targetMaxHealth));
    }
  }
}
