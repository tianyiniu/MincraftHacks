package me.tianyin.minecrafthacks;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import java.util.*;

public class CrouchTeleportListener implements Listener {
  HashMap<Player, Queue<Long>> hp;
  long INTERVAL;
  int TRANSITIONS;

  public CrouchTeleportListener(HashMap<Player, Queue<Long>> hp) {
    this.hp = hp;
    this.INTERVAL = 20 * 5;
    this.TRANSITIONS = 6;
  }

  private double calculateAngle(double sightX, double sightZ, double sightY, double locX, double locZ, double locY) {
    double dot = sightX*locX + sightZ*locZ + sightY*locY;
    double lengthSight = Math.sqrt(Math.pow(sightX,2) + Math.pow(sightZ,2) + Math.pow(sightY,2));
    double lengthLoc = Math.sqrt(Math.pow(locX,2) + Math.pow(locZ,2) + Math.pow(locY,2));
    return Math.acos(dot/(lengthSight*lengthLoc));
  }


  @EventHandler
  public void onCrouchEvent(PlayerToggleSneakEvent event) {
    Player p = event.getPlayer();
    Long currTime = p.getWorld().getGameTime();
    Queue<Long> playerQ;

    // New player
    if (!hp.containsKey(p)) {
      playerQ = new LinkedList<>();
      for (int i=0; i< TRANSITIONS-2; i++) {
        playerQ.offer(-1 * INTERVAL);
      }
      playerQ.offer(currTime);
      hp.put(p, playerQ);
      return;
    }

    playerQ = hp.get(p);
    long startingTime = playerQ.poll();
    playerQ.offer(currTime);

    if (!(currTime-startingTime < INTERVAL)) {
      return;
    }

    double sightYaw = p.getLocation().getYaw();
    double sightPitch = p.getLocation().getPitch();
    double sightX = -Math.sin(Math.toRadians(sightYaw));
    double sightZ = Math.cos(Math.toRadians(sightYaw));
    double sightY = -Math.sin(Math.toRadians(sightPitch));

    List<Entity> entities = p.getWorld().getEntities();
    double minAngle = 2*Math.PI; // In radians
    Entity minPlayer = p;

    for (Entity entity:entities) {
      if (entity.getType().equals(EntityType.PLAYER) && entity != p) {
        double locX = entity.getLocation().getX() - p.getLocation().getX();
        double locZ = entity.getLocation().getZ() - p.getLocation().getZ();
        double locY = entity.getLocation().getY() - p.getLocation().getY();

        double angle = calculateAngle(sightX, sightZ, sightY, locX, locZ, locY);
        if (angle < minAngle) {
          minAngle = angle;
          minPlayer = entity;
        }
      }
    }

    Location newLoc = minPlayer.getLocation();
    newLoc.setYaw((float) sightYaw);
    newLoc.setPitch((float) sightPitch);
    p.teleport(newLoc);
    hp.remove(p);
  }
}

