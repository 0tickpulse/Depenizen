package com.denizenscript.depenizen.bukkit.commands.mythicmobs;

import com.denizenscript.denizen.objects.EntityTag;
import com.denizenscript.denizencore.objects.core.ElementTag;
import com.denizenscript.denizencore.objects.core.ListTag;
import com.denizenscript.denizencore.scripts.ScriptEntry;
import com.denizenscript.denizencore.scripts.commands.AbstractCommand;
import com.denizenscript.denizencore.scripts.commands.generator.ArgLinear;
import com.denizenscript.denizencore.scripts.commands.generator.ArgName;
import com.denizenscript.depenizen.bukkit.objects.mythicmobs.MythicMobsMobTag;
import io.lumine.mythic.bukkit.BukkitAdapter;

public class MythicSignalCommand extends AbstractCommand {

    public MythicSignalCommand() {
        setName("mythicsignal");
        setSyntax("mythicsignal [<mythicmob>|...] [<signal>] [source:<entity>]");
        setRequiredArguments(3, 3);
        autoCompile();
    }

    // <--[command]
    // @Name MythicSignal
    // @Syntax mythicsignal [<mythicmob>|...] [<signal>] [source:<entity>]
    // @Group Depenizen
    // @Plugin Depenizen, MythicMobs
    // @Required 3
    // @Maximum 3
    // @Short Sends a signal trigger to the target MythicMobs.
    //
    // @Description
    // This allows you to send a signal trigger to multiple MythicMobs.
    // If those mobs have any triggers configured for that signal, they will fire.
    // You must specify an entity that acts as the sender.
    // NOTE: signals are case sensitive.
    //
    // @Usage
    // Used to trigger the player's target's signal "attack".
    // - mythicsignal <player.target.mythicmob> attack source:<player>
    //
    // -->

    public static void autoExecute(ScriptEntry scriptEntry,
                                   @ArgLinear @ArgName("targets") ListTag targets,
                                   @ArgLinear @ArgName("signal") ElementTag signal,
                                   @ArgName("source") EntityTag source) {
        for (MythicMobsMobTag mob : targets.filter(MythicMobsMobTag.class, scriptEntry)) {
            mob.getMob().signalMob(BukkitAdapter.adapt(source.getBukkitEntity()), signal.asString());
        }
    }
}
