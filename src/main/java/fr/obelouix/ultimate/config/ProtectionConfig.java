package fr.obelouix.ultimate.config;

import org.spongepowered.configurate.serialize.SerializationException;

class ProtectionConfig extends BaseConfig {

    @Override
    protected void addToConfig() throws SerializationException {
        if (Config.getRoot().node("protection", "explosions", "wither", "disable-block-damage").empty()) {
            Config.getRoot().node("protection", "explosions", "wither", "disable-block-damage").set(false)
                    .commentIfAbsent("Disable damages on blocks from the wither boss");

            Config.getRoot().node("protection", "explosions", "wither", "show-wither-skull-explosions-particles").set(true)
                    .commentIfAbsent("""
                            Show smoke at the location where wither skulls explodes
                            only works if block damages are blocked""");

        }
    }

    @Override
    protected void readConfig() {
        Config.witherBlockDamage = Config.getRoot().node("protection", "explosions", "wither", "disable-block-damage").getBoolean();
        if (Config.witherBlockDamage) {
            Config.showWitherSkullExplosionsParticles = Config.getRoot().node("protection", "explosions", "wither", "show-wither-skull-explosions-particles").getBoolean();
        }
    }

}
