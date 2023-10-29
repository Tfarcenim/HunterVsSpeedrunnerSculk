package tfar.speedrunnervshuntersculk;

import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.GameProfileArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public class ModCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                LiteralArgumentBuilder.<CommandSourceStack>literal("speedrunner")
                        .executes(ModCommand::speedrunner)
                        .then(Commands.argument("players", GameProfileArgument.gameProfile())
                                .executes(ModCommand::alsoSpeedrunner))
                        .then(Commands.literal("unlock")
                                .then(Commands.argument("buff", StringArgumentType.string()).suggests(VALID_UNLOCK_ABILITIES)
                                        .executes(ModCommand::unlockBuff)
        )));
    }

    private static final SuggestionProvider<CommandSourceStack> VALID_UNLOCK_ABILITIES =
            (context, builder) -> SharedSuggestionProvider.suggest(Arrays.stream(BuffType.values()).map(Enum::name).collect(Collectors.toList()), builder);

    public static int speedrunner(CommandContext<CommandSourceStack> context) {
        //assume the player is the one using the command
        ServerPlayer serverPlayer = context.getSource().getPlayer();
        SpeedrunnerVsHunterSculk.speedrunner = serverPlayer.getUUID();
        return 1;
    }

    public static int unlockBuff(CommandContext<CommandSourceStack> context) {
        String string = StringArgumentType.getString(context,"buff");
        try {
            BuffType buffType = BuffType.valueOf(string);
            Utils.unlockBuffType(buffType);
        } catch (Exception e) {
            context.getSource().sendFailure(Component.literal("Invalid buff: "+string));
            return 0;
        }

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
