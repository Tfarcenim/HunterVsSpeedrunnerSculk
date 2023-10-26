package tfar.speedrunnervshuntersculk.data;

import com.google.gson.JsonElement;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.blockstates.*;
import net.minecraft.data.models.model.ModelLocationUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.SculkSensorPhase;
import tfar.speedrunnervshuntersculk.Init;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ModBlockModelProvider extends BlockModelGenerators {

    public ModBlockModelProvider(Consumer<BlockStateGenerator> pBlockStateOutput, BiConsumer<ResourceLocation, Supplier<JsonElement>> pModelOutput, Consumer<Item> pSkippedAutoModelsOutput) {
        super(pBlockStateOutput, pModelOutput, pSkippedAutoModelsOutput);
    }

    @Override
    public void run() {
        createSpeedrunnerSculkSensor();
    }

    public void createSpeedrunnerSculkSensor() {
        ResourceLocation resourcelocation = ModelLocationUtils.getModelLocation(Blocks.SCULK_SENSOR, "_inactive");
        ResourceLocation resourcelocation1 = ModelLocationUtils.getModelLocation(Blocks.SCULK_SENSOR, "_active");
        this.delegateItemModel(Init.SPEEDRUNNER_SCULK_SENSOR, resourcelocation);
        this.blockStateOutput.accept(MultiVariantGenerator.multiVariant(Init.SPEEDRUNNER_SCULK_SENSOR)
                .with(PropertyDispatch.property(BlockStateProperties.SCULK_SENSOR_PHASE).generate((p_284650_) -> Variant.variant().with(VariantProperties.MODEL, p_284650_ != SculkSensorPhase.ACTIVE && p_284650_ != SculkSensorPhase.COOLDOWN ? resourcelocation : resourcelocation1))));
    }

}
