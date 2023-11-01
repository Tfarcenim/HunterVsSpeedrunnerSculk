package tfar.speedrunnervshuntersculk;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.bossevents.CustomBossEvent;
import net.minecraft.server.bossevents.CustomBossEvents;
import net.minecraft.server.level.ServerPlayer;

public class VoidPowerManager {

    public static final ResourceLocation ID = new ResourceLocation(SpeedrunnerVsHunterSculk.MOD_ID,"void_power");

    public static CustomBossEvent createBar(ServerPlayer player) {
        MinecraftServer server = player.server;
        CustomBossEvents customBossEvents = server.getCustomBossEvents();
        CustomBossEvent customBossEvent = customBossEvents.get(ID);
        if (customBossEvent == null) {
            try {
                customBossEvent = customBossEvents.create(ID, ComponentUtils.updateForEntity(server.createCommandSourceStack(),
                        Component.literal("Void Power 0"), null, 0));
            } catch (CommandSyntaxException e) {
                throw new RuntimeException(e);
            }
            customBossEvent.setMax(powerRequiredForNextLevel(0));
        }
        //customBossEvent.addPlayer(player);
        return customBossEvent;
        //source.sendSuccess(() -> Component.translatable("commands.bossbar.create.success", customBossEvent.getDisplayName()), true);
    }

    //Void Powers:
    //level 1: can shoot a void hole where ever i am looking that slowly deletes a 20 diameter sphere of blocks around it and transfers the blocks to my inventory
    //level 2: can teleport to anywhere i right click/ am looking at while crouched
    //level 3: can stop time for 10 seconds

    public static void addPower(ServerPlayer player,int amount) {
        CustomBossEvent customBossEvent = createBar(player);

        customBossEvent.setValue(customBossEvent.getValue() + amount);
        if (customBossEvent.getProgress() >= 1) {
            String[] parts = customBossEvent.getName().getString().split(" ");
            int i = Integer.parseInt(parts[2]);
            customBossEvent.setName(Component.literal("Void Power "+(i+1)));
            customBossEvent.setValue(0);
            customBossEvent.setMax(powerRequiredForNextLevel(i+1));
        }
    }

    public static int powerRequiredForNextLevel(int level) {
        return 3;
    }

    public static int getVoidPower(ServerPlayer player) {
        MinecraftServer server = player.server;
        CustomBossEvents customBossEvents = server.getCustomBossEvents();
        CustomBossEvent customBossEvent = customBossEvents.get(ID);
        if (customBossEvent != null)  {
            String[] parts = customBossEvent.getName().getString().split(" ");
            if (parts.length == 3) {
                return Integer.parseInt(parts[2]);
            }
        }
        return 0;
    }
}
