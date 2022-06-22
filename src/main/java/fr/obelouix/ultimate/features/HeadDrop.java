package fr.obelouix.ultimate.features;

import fr.obelouix.ultimate.api.InventoryAPI;
import fr.obelouix.ultimate.utils.MobHeadsSkin;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class HeadDrop implements Listener {

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {

        final LivingEntity entity = event.getEntity();
        final EntityType entityType = entity.getType();
        final Player player = event.getEntity().getKiller();

        if (player != null) {

            final ItemStack itemStack = player.getInventory().getItemInMainHand();

            if (itemStack.containsEnchantment(Enchantment.DAMAGE_ALL)) {
                final int enchantmentLevel = itemStack.getEnchantmentLevel(Enchantment.DAMAGE_ALL);
                float random = new Random().nextFloat(0f, 1.01f);

                final ItemStack head = switch (entityType) {
                    default -> null;
                    case ELDER_GUARDIAN ->
                            InventoryAPI.addCustomSkull(MobHeadsSkin.ELDER_GUARDIAN, Component.translatable("entity.minecraft.elder_guardian"));
                    case WITHER_SKELETON -> new ItemStack(Material.WITHER_SKELETON_SKULL);
                    case STRAY ->
                            InventoryAPI.addCustomSkull(MobHeadsSkin.STRAY, Component.translatable("entity.minecraft.stray"));
                    case HUSK ->
                            InventoryAPI.addCustomSkull(MobHeadsSkin.HUSK, Component.translatable("entity.minecraft.husk"));
                    case ZOMBIE_VILLAGER ->
                            InventoryAPI.addCustomSkull(MobHeadsSkin.ZOMBIE_VILLAGER_NULL, Component.translatable("entity.minecraft.zombie_villager"));
                    case SKELETON_HORSE ->
                            InventoryAPI.addCustomSkull(MobHeadsSkin.SKELETON_HORSE, Component.translatable("entity.minecraft.skeleton_horse"));
                    case ZOMBIE_HORSE ->
                            InventoryAPI.addCustomSkull(MobHeadsSkin.ZOMBIE_HORSE, Component.translatable("entity.minecraft.zombie_horse"));
                    case DONKEY ->
                            InventoryAPI.addCustomSkull(MobHeadsSkin.DONKEY, Component.translatable("entity.minecraft.donkey"));
                    case MULE ->
                            InventoryAPI.addCustomSkull(MobHeadsSkin.MULE, Component.translatable("entity.minecraft.mule"));
                    case EVOKER ->
                            InventoryAPI.addCustomSkull(MobHeadsSkin.EVOKER, Component.translatable("entity.minecraft.evoker"));
                    case VEX ->
                            InventoryAPI.addCustomSkull(MobHeadsSkin.VEX, Component.translatable("entity.minecraft.vex"));
                    case VINDICATOR ->
                            InventoryAPI.addCustomSkull(MobHeadsSkin.VINDICATOR, Component.translatable("entity.minecraft.vindicator"));
                    case ILLUSIONER ->
                            InventoryAPI.addCustomSkull(MobHeadsSkin.ILLUSIONER, Component.translatable("entity.minecraft.illusioner"));
                    case CREEPER -> new ItemStack(Material.CREEPER_HEAD);
                    case SKELETON -> new ItemStack(Material.SKELETON_SKULL);
                    case SPIDER ->
                            InventoryAPI.addCustomSkull(MobHeadsSkin.SPIDER, Component.translatable("entity.minecraft.spider"));
                    case GIANT ->
                            InventoryAPI.addCustomSkull(MobHeadsSkin.GIANT, Component.translatable("entity.minecraft.giant"));
                    case ZOMBIE -> new ItemStack(Material.ZOMBIE_HEAD);
                    case SLIME ->
                            InventoryAPI.addCustomSkull(MobHeadsSkin.SLIME, Component.translatable("entity.minecraft.slime"));
                    case GHAST ->
                            InventoryAPI.addCustomSkull(MobHeadsSkin.GHAST, Component.translatable("entity.minecraft.ghast"));
                    case ZOMBIFIED_PIGLIN ->
                            InventoryAPI.addCustomSkull(MobHeadsSkin.ZOMBIFIED_PIGLIN, Component.translatable("entity.minecraft.zombified_piglin"));
                    case ENDERMAN ->
                            InventoryAPI.addCustomSkull(MobHeadsSkin.ENDERMAN, Component.translatable("entity.minecraft.enderman"));
                    case CAVE_SPIDER ->
                            InventoryAPI.addCustomSkull(MobHeadsSkin.CAVE_SPIDER, Component.translatable("entity.minecraft.cave_spider"));
                    case SILVERFISH ->
                            InventoryAPI.addCustomSkull(MobHeadsSkin.SILVERFISH, Component.translatable("entity.minecraft.silverfish"));
                    case BLAZE ->
                            InventoryAPI.addCustomSkull(MobHeadsSkin.BLAZE, Component.translatable("entity.minecraft.blaze"));
                    case MAGMA_CUBE ->
                            InventoryAPI.addCustomSkull(MobHeadsSkin.MAGMA_CUBE, Component.translatable("entity.minecraft.magma_cube"));
                    case BAT ->
                            InventoryAPI.addCustomSkull(MobHeadsSkin.BAT, Component.translatable("entity.minecraft.bat"));
                    case BEE ->
                            InventoryAPI.addCustomSkull(MobHeadsSkin.BEE, Component.translatable("entity.minecraft.bee"));
                    case WITCH ->
                            InventoryAPI.addCustomSkull(MobHeadsSkin.WITCH, Component.translatable("entity.minecraft.witch"));
                    case ENDERMITE ->
                            InventoryAPI.addCustomSkull(MobHeadsSkin.ENDERMITE, Component.translatable("entity.minecraft.endermite"));
                    case GUARDIAN ->
                            InventoryAPI.addCustomSkull(MobHeadsSkin.GUARDIAN, Component.translatable("entity.minecraft.guardian"));
                    case SHULKER ->
                            InventoryAPI.addCustomSkull(MobHeadsSkin.SHULKER, Component.translatable("entity.minecraft.shulker"));
                    case PIG ->
                            InventoryAPI.addCustomSkull(MobHeadsSkin.PIG, Component.translatable("entity.minecraft.pig"));
                    case SHEEP ->
                            InventoryAPI.addCustomSkull(MobHeadsSkin.SHEEP_WHITE, Component.translatable("entity.minecraft.sheep"));
                    case COW ->
                            InventoryAPI.addCustomSkull(MobHeadsSkin.COW, Component.translatable("entity.minecraft.cow"));
                    case CHICKEN ->
                            InventoryAPI.addCustomSkull(MobHeadsSkin.CHICKEN, Component.translatable("entity.minecraft.chicken"));
                    case SQUID ->
                            InventoryAPI.addCustomSkull(MobHeadsSkin.SQUID, Component.translatable("entity.minecraft.squid"));
                    case WOLF ->
                            InventoryAPI.addCustomSkull(MobHeadsSkin.WOLF, Component.translatable("entity.minecraft.wolf"));
                    case MUSHROOM_COW ->
                            InventoryAPI.addCustomSkull(MobHeadsSkin.MUSHROOM_COW_RED, Component.translatable("entity.minecraft.mushroom_cow"));
                    case SNOWMAN ->
                            InventoryAPI.addCustomSkull(MobHeadsSkin.SNOWMAN, Component.translatable("entity.minecraft.snowman"));
                    case OCELOT ->
                            InventoryAPI.addCustomSkull(MobHeadsSkin.OCELOT, Component.translatable("entity.minecraft.ocelot"));
                    case IRON_GOLEM ->
                            InventoryAPI.addCustomSkull(MobHeadsSkin.IRON_GOLEM, Component.translatable("entity.minecraft.iron_golem"));
                    case HORSE ->
                            InventoryAPI.addCustomSkull(MobHeadsSkin.HORSE_BLACK, Component.translatable("entity.minecraft.horse"));
                    case RABBIT ->
                            InventoryAPI.addCustomSkull(MobHeadsSkin.RABBIT_WHITE, Component.translatable("entity.minecraft.rabbit"));
                    case POLAR_BEAR ->
                            InventoryAPI.addCustomSkull(MobHeadsSkin.POLAR_BEAR, Component.translatable("entity.minecraft.polar_bear"));
                    case LLAMA ->
                            InventoryAPI.addCustomSkull(MobHeadsSkin.LLAMA_WHITE, Component.translatable("entity.minecraft.llama"));
                    case PARROT ->
                            InventoryAPI.addCustomSkull(MobHeadsSkin.PARROT_BLUE, Component.translatable("entity.minecraft.parrot"));
                    case VILLAGER ->
                            InventoryAPI.addCustomSkull(MobHeadsSkin.VILLAGER_NULL, Component.translatable("entity.minecraft.villager"));
                    case TURTLE ->
                            InventoryAPI.addCustomSkull(MobHeadsSkin.TURTLE, Component.translatable("entity.minecraft.turtle"));
                    case PHANTOM ->
                            InventoryAPI.addCustomSkull(MobHeadsSkin.PHANTOM, Component.translatable("entity.minecraft.phantom"));
                    case COD ->
                            InventoryAPI.addCustomSkull(MobHeadsSkin.COD, Component.translatable("entity.minecraft.cod"));
                    case SALMON ->
                            InventoryAPI.addCustomSkull(MobHeadsSkin.SALMON, Component.translatable("entity.minecraft.salmon"));
                    case PUFFERFISH ->
                            InventoryAPI.addCustomSkull(MobHeadsSkin.PUFFERFISH, Component.translatable("entity.minecraft.pufferfish"));
                    case TROPICAL_FISH ->
                            InventoryAPI.addCustomSkull(MobHeadsSkin.TROPICAL_FISH_BLACK, Component.translatable("entity.minecraft.tropical_fish"));
                    case DROWNED ->
                            InventoryAPI.addCustomSkull(MobHeadsSkin.DROWNED, Component.translatable("entity.minecraft.drowned"));
                    case DOLPHIN ->
                            InventoryAPI.addCustomSkull(MobHeadsSkin.DOLPHIN, Component.translatable("entity.minecraft.dolphin"));
                    case CAT ->
                            InventoryAPI.addCustomSkull(MobHeadsSkin.CAT_BLACK, Component.translatable("entity.minecraft.cat"));
                    case PANDA ->
                            InventoryAPI.addCustomSkull(MobHeadsSkin.PANDA, Component.translatable("entity.minecraft.panda"));
                    case PILLAGER ->
                            InventoryAPI.addCustomSkull(MobHeadsSkin.PILLAGER, Component.translatable("entity.minecraft.pillager"));
                    case RAVAGER ->
                            InventoryAPI.addCustomSkull(MobHeadsSkin.RAVAGER, Component.translatable("entity.minecraft.ravager"));
                    case TRADER_LLAMA ->
                            InventoryAPI.addCustomSkull(MobHeadsSkin.TRADER_LLAMA_BROWN, Component.translatable("entity.minecraft.trader_llama"));
                    case WANDERING_TRADER ->
                            InventoryAPI.addCustomSkull(MobHeadsSkin.WANDERING_TRADER, Component.translatable("entity.minecraft.wandering_trader"));
                    case FOX ->
                            InventoryAPI.addCustomSkull(MobHeadsSkin.FOX, Component.translatable("entity.minecraft.fox"));
                    case HOGLIN ->
                            InventoryAPI.addCustomSkull(MobHeadsSkin.HOGLIN, Component.translatable("entity.minecraft.hoglin"));
                    case PIGLIN ->
                            InventoryAPI.addCustomSkull(MobHeadsSkin.PIGLIN, Component.translatable("entity.minecraft.piglin"));
                    case STRIDER ->
                            InventoryAPI.addCustomSkull(MobHeadsSkin.STRIDER, Component.translatable("entity.minecraft.strider"));
                    case ZOGLIN ->
                            InventoryAPI.addCustomSkull(MobHeadsSkin.ZOGLIN, Component.translatable("entity.minecraft.zoglin"));
                    case PIGLIN_BRUTE ->
                            InventoryAPI.addCustomSkull(MobHeadsSkin.PIGLIN_BRUTE, Component.translatable("entity.minecraft.piglin_brute"));
                    case AXOLOTL ->
                            InventoryAPI.addCustomSkull(MobHeadsSkin.AXOLOTL_BLUE, Component.translatable("entity.minecraft.axolotl"));
                    case GLOW_SQUID ->
                            InventoryAPI.addCustomSkull(MobHeadsSkin.GLOW_SQUID, Component.translatable("entity.minecraft.glow_squid"));
                    case GOAT ->
                            InventoryAPI.addCustomSkull(MobHeadsSkin.GOAT, Component.translatable("entity.minecraft.goat"));
                };

                if (head != null) {
                    switch (enchantmentLevel) {
                        case 1 -> {
                            if (random <= 0.15f) event.getDrops().add(head);
                        }
                        case 2 -> {
                            if (random <= 0.3f) event.getDrops().add(head);
                        }
                        case 3 -> {
                            if (random <= 0.5f) event.getDrops().add(head);
                        }
                        case 4 -> {
                            if (random <= 0.75f) event.getDrops().add(head);
                        }
                        case 5 -> event.getDrops().add(head);
                    }
                }

            }
        }

    }

}
