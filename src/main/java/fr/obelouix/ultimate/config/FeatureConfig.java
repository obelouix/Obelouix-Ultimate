package fr.obelouix.ultimate.config;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.spigotmc.SpigotConfig;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;

import java.util.ArrayList;
import java.util.Objects;

class FeatureConfig extends BaseConfig {

    private final CommentedConfigurationNode featuresNode = Config.getRoot().node("features");

    @Override
    protected void addToConfig() throws SerializationException {

        if (featuresNode.node("anvil", "infinite-repair").empty()) {
            featuresNode.node("anvil", "infinite-repair").set(true)
                    .commentIfAbsent("Cap the max experience repair cost to 39 levels");
        }

        if (featuresNode.node("coordinates", "enable").empty()) {
            featuresNode.node("coordinates", "enable").set(true)
                    .commentIfAbsent("Enable the coordinates system");
            featuresNode.node("coordinates", "worlds", "blacklist").setList(String.class, new ArrayList<>())
                    .commentIfAbsent("""
                            Add here the worlds were you wish to disable the coordinates system
                            Like this: blacklist=["world1","world2"]
                            """);
        }

        if (featuresNode.node("custom-server-brand").empty()) {
            featuresNode.node("custom-server-brand").set(Bukkit.getName())
                    .commentIfAbsent("""
                            Allows you to fake the server brand in the F3 menu
                            You can use colors codes like &a,&1,&2,...
                            Please put the brand between double quotes if you plan to use colors for this to work
                            """);
        }


        if (featuresNode.node("enderdragon", "share-xp").empty()) {
            final double expMergeRadius = SpigotConfig.config.getDouble("world-settings.default.merge-radius.exp");
            //auto enable if experience merge radius is not disabled
            featuresNode.node("enderdragon", "share-xp").set(expMergeRadius > 0.)
                    .commentIfAbsent("""
                            Give Ender Dragon's experience to every player that are present in the end.
                            You can enable this if your server merge experience orbs (see spigot.yml).
                            By default it is enabled when this configuration generate if your server merge experience
                            """);
        }

        if (featuresNode.node("fast-leaf-decay").empty()) {
            featuresNode.node("fast-leaf-decay").set(false);
        }

        if (featuresNode.node("potions", "revive-coral-blocks").empty()) {
            featuresNode.node("potions", "revive-coral-blocks").set(true)
                    .commentIfAbsent("Send a healing splash potion on dead coral blocks to revive them");
        }


    }

    @Override
    protected void readConfig() {
        Config.shareEnderDragonExperience = featuresNode.node("enderdragon", "share-xp").getBoolean();
        Config.infiniteAnvilRepair = featuresNode.node("anvil", "infinite-repair").getBoolean();
        Config.enableCoordinates = featuresNode.node("coordinates", "enable").getBoolean();

        if (Config.enableCoordinates) {
            try {
                Objects.requireNonNull(featuresNode.node("coordinates", "worlds", "blacklist").getList(String.class))
                        .forEach(worldName -> {
                            final World world = Bukkit.getWorld(worldName);
                            if (world != null) Config.coordinatesBlacklist.add(world);
                            else {
                                Config.plugin.getComponentLogger().error(Component.text("[Coordinates] Could not add world worldname to blacklist. Does it exist ?", NamedTextColor.RED)
                                        .replaceText(builder -> builder.matchLiteral("worldname").replacement(Component.text(worldName, NamedTextColor.DARK_RED))));
                            }
                        });
            } catch (SerializationException e) {
                throw new RuntimeException(e);
            }
        }

        Config.customServerBrand = featuresNode.node("custom-server-brand").getString();
        Config.fastLeafDecay = featuresNode.node("fast-leaf-decay").getBoolean();
        Config.reviveCoralBlock = featuresNode.node("potions", "revive-coral-blocks").getBoolean();
    }

}
