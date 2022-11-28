package me.tianyin.minecrafthacks;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.projectiles.ProjectileSource;

import java.util.Collection;
import java.util.List;

public class KirikoHackListener implements Listener {

  KirikoTimeRanges playerTimes;
  int INVULNERABILITY_TIME;
  double EFFECT_DISTANCE;

  public KirikoHackListener(KirikoTimeRanges playerTimes) {
    this.playerTimes = playerTimes;
    this.INVULNERABILITY_TIME = 60;
    this.EFFECT_DISTANCE = 5.0;
  }

  @EventHandler
  public void onProjectileHit(ProjectileHitEvent event) {

    Projectile proj = event.getEntity();
    EntityType projType = proj.getType();

    if (!(proj.getShooter() instanceof Player)) {return;} // Shooter must be player
    Entity shooter = (Entity) proj.getShooter();
    EntityType shooterType = shooter.getType();

    Location hitLoc;

    // Find location of snowball hit
    if (projType.equals(EntityType.SNOWBALL) && shooterType.equals(EntityType.PLAYER)) {
      World hitWorld = proj.getWorld();
      if (event.getHitBlock() != null) {
        Block hitBlock = event.getHitBlock();
        hitLoc = hitBlock.getLocation();
      } else {
        event.getEntity();
        Entity hitEntity = event.getHitEntity();
        hitLoc = hitEntity.getLocation();
      }

      // Get list of entities, if is Player, remove effects and apply glowing
      List<Entity> entities = hitWorld.getEntities();
      for (Entity entity:entities) {
        if (entity.getType().equals(EntityType.PLAYER) && hitLoc.distance(entity.getLocation()) < EFFECT_DISTANCE) {

          playerTimes.addPlayerTime((Player) entity, hitWorld.getGameTime()+INVULNERABILITY_TIME);

          // Remove all effects
          Player p = (Player) entity;
          Collection<PotionEffect> potionEffects = p.getActivePotionEffects();
          for (PotionEffect pe : potionEffects) {
            p.removePotionEffect(pe.getType());
          }

          // Glowing effect
          PotionEffect glowingEffect = new PotionEffect(PotionEffectType.GLOWING, INVULNERABILITY_TIME, 1);
          p.addPotionEffect(glowingEffect);

        }
      }
    }
  }
}
