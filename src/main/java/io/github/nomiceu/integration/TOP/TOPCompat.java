package io.github.nomiceu.integration.TOP;

import io.github.nomiceu.integration.TOP.provider.DrawerLogoProvider;
import mcjty.theoneprobe.TheOneProbe;
import mcjty.theoneprobe.api.ITheOneProbe;

public class TOPCompat {
    public static void registerProviders() {
        ITheOneProbe TOP = TheOneProbe.theOneProbeImp;
        TOP.registerBlockDisplayOverride(new DrawerLogoProvider());
    }
}
