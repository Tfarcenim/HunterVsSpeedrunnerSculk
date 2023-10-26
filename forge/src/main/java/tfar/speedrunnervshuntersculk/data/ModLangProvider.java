package tfar.speedrunnervshuntersculk.data;

import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;
import tfar.speedrunnervshuntersculk.Constants;
import tfar.speedrunnervshuntersculk.Init;

public class ModLangProvider extends LanguageProvider {
    public ModLangProvider(PackOutput output) {
        super(output, Constants.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        add(Init.SPEEDRUNNER_SCULK_SENSOR,"Speedrunner Sculk Sensor");

    }
}
