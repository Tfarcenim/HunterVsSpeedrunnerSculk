package tfar.speedrunnervshuntersculk;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tfar.speedrunnervshuntersculk.platform.Services;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Items;

import java.util.UUID;

// This class is part of the common project meaning it is shared between all supported loaders. Code written here can only
// import and access the vanilla codebase, libraries used by vanilla, and optionally third party libraries that provide
// common compatible binaries. This means common code can not directly use loader specific concepts such as Forge events
// however it will be compatible with all supported mod loaders.
public class SpeedrunnerVsHunterSculk {

    public static final String MOD_ID = "speedrunnervshuntersculk";
    public static final String MOD_NAME = "SpeedrunnerVsHunterSculk";
    public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);

    public static UUID speedrunner;

    // The loader specific projects are able to import and use any code from the common project. This allows you to
    // write the majority of your code here and load it from your loader specific projects. This example has some
    // code that gets invoked by the entry point of the loader specific projects.
    public static void init() {

        LOG.info("Hello from Common init on {}! we are currently in a {} environment!", Services.PLATFORM.getPlatformName(), Services.PLATFORM.getEnvironmentName());
        LOG.info("The ID for diamonds is {}", BuiltInRegistries.ITEM.getKey(Items.DIAMOND));

        // It is common for all supported loaders to provide a similar feature that can not be used directly in the
        // common code. A popular way to get around this is using Java's built-in service loader feature to create
        // your own abstraction layer. You can learn more about this in our provided services class. In this example
        // we have an interface in the common code and use a loader specific implementation to delegate our call to
        // the platform specific approach.
    }

    //mod info

    // This mod would be used in a minecraft manhunt gamemode
    //
    //can't use teams to desgniate players on teams as it would conflict with my manhunt datapack
    //
    //description of the mod: speedrunner is given a skulk sensor that can be upgraded, it is triggered by hunters only,
    // it would increase the health, reach, knockback and "void power level" of the speedrunner based on which version of the skulk sensor is currently being used. the speedrunner would always have this skulk sensor in his inventory and could place it down 1 time per minute, and to change which version of the sensor is being placed you would crouch and scroll while the skulk sensor is in your hand
    //
    //upgrades are unlocked through commands for each version (health, reach, knockback, void level)
    //
    //Void Power level:
    //this is a indicatior to show what level of void power i have and at specific stages of void power i gain specific abilities that are "void" related
    //the levels increase via a boss bar at the top of the screen to represent when ill reach each level up
    //
    //Void Powers:
    //level 1: can shoot a void hole where ever i am looking that slowly deletes a 20 diameter sphere of blocks around it and transfers the blocks to my inventory
    //level 2: can teleport to anywhere i right click/ am looking at while crouched
    //level 3: can stop time for 10 seconds

}