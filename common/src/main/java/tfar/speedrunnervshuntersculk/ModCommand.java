package tfar.speedrunnervshuntersculk;

import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.GameProfileArgument;
import net.minecraft.server.level.ServerPlayer;

import java.util.Collection;

public class ModCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                LiteralArgumentBuilder.<CommandSourceStack>literal("speedrunner")
                        .executes(ModCommand::speedrunner)
                        .then(Commands.argument("players", GameProfileArgument.gameProfile())
                                .executes(ModCommand::alsoSpeedrunner)));
    }

    public static int speedrunner(CommandContext<CommandSourceStack> context) {
        //assume the player is the one using the command
        ServerPlayer serverPlayer = context.getSource().getPlayer();
        SpeedrunnerVsHunterSculk.speedrunner = serverPlayer.getUUID();
        return 1;
    }

    public static int alsoSpeedrunner(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Collection<GameProfile> gameProfile = GameProfileArgument.getGameProfiles(context, "players");
        ServerPlayer serverPlayer = context.getSource().getServer().getPlayerList()
                .getPlayer(gameProfile.stream().findFirst().get().getId());

        SpeedrunnerVsHunterSculk.speedrunner = serverPlayer.getUUID();
        return 1;
    }
}
