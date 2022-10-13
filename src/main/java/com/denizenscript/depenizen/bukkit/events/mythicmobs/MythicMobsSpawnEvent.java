package com.denizenscript.depenizen.bukkit.events.mythicmobs;

import com.denizenscript.denizencore.objects.*;
import com.denizenscript.denizencore.objects.core.ElementTag;
import com.denizenscript.depenizen.bukkit.objects.mythicmobs.MythicMobsMobTag;
import com.denizenscript.denizen.events.BukkitScriptEvent;
import com.denizenscript.denizen.objects.EntityTag;
import com.denizenscript.denizen.objects.LocationTag;
import io.lumine.mythic.api.adapters.AbstractLocation;
import io.lumine.mythic.api.mobs.entities.SpawnReason;
import io.lumine.mythic.bukkit.BukkitAdapter;
import io.lumine.mythic.bukkit.events.MythicMobSpawnEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;


public class MythicMobsSpawnEvent extends BukkitScriptEvent implements Listener {

    // <--[event]
    // @Events
    // mythicmob <'mob'> spawns
    //
    // @Location true
    //
    // @Triggers when a MythicMob spawns.
    //
    // @Context
    // <context.mob> Returns the MythicMob that is spawning.
    // <context.entity> Returns the EntityTag for the MythicMob.
    // <context.location> Returns the LocationTag of where the MythicMob will spawn.
    // <context.from_spawner> Returns true if the mob was from a spawner.
    // <context.spawner_location> Returns the LocationTag of the spawner that spawned the mob, if any.
    // <context.spawn_reason> Returns the reason for the MythicMob's spawning. Can be NATURAL, COMMAND, SPAWNER, SUMMON, OTHER, or DISPENSER.
    //
    // @Plugin Depenizen, MythicMobs
    //
    // @Group Depenizen
    //
    // -->

    public MythicMobsSpawnEvent() {
        registerCouldMatcher("mythicmob <'mob'> spawns");
    }

    public static MythicMobsSpawnEvent instance;
    public MythicMobSpawnEvent event;
    public MythicMobsMobTag mythicmob;
    public LocationTag location;

    @Override
    public boolean matches(ScriptPath path) {
        String mob = path.eventArgLowerAt(1);
        if (!mob.equals("mob") && !runGenericCheck(mob, mythicmob.getMobType().getInternalName())) {
            return false;
        }
        if (!runInCheck(path, location)) {
            return false;
        }
        return super.matches(path);
    }

    @Override
    public ObjectTag getContext(String name) {
        switch (name) {
            case "mob":
                return mythicmob;
            case "entity":
                return new EntityTag(event.getEntity());
            case "location":
                return location;
            case "from_spawner":
                return new ElementTag(event.isFromMythicSpawner());
            case "spawner_location":
                if (!event.isFromMythicSpawner()) {
                    return null;
                }
                return new LocationTag(BukkitAdapter.adapt(event.getMythicSpawner().getLocation()));
            case "spawn_reason":
                return new ElementTag(event.getSpawnReason().toString());
            default:
                return super.getContext(name);
        }
    }

    @EventHandler
    public void onMythicMobSpawns(MythicMobSpawnEvent event) {
        mythicmob = new MythicMobsMobTag(event.getMob());
        location = new LocationTag(event.getLocation());
        this.event = event;
        EntityTag.rememberEntity(event.getEntity());
        fire(event);
        EntityTag.forgetEntity(event.getEntity());
    }
}
