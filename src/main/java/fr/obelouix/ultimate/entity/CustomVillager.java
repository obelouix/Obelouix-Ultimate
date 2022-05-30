package fr.obelouix.ultimate.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_18_R2.CraftWorld;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class CustomVillager extends net.minecraft.world.entity.npc.Villager {

    public CustomVillager(EntityType entityType, Level world) {
        super(entityType, world);
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.0, Ingredient.of(Blocks.EMERALD_BLOCK.asItem()), false));
        this.targetSelector.addGoal(3, new TemptGoal(this, 1.0, Ingredient.of(Blocks.EMERALD_BLOCK.asItem()), false));
        this.getAttributes().registerAttribute(Attributes.FOLLOW_RANGE);
        Objects.requireNonNull(this.getAttribute(Attributes.FOLLOW_RANGE)).setBaseValue(10);
        this.setHealth(20);
        Objects.requireNonNull(this.getAttribute(Attributes.FOLLOW_RANGE)).setBaseValue(10);
        ((GroundPathNavigation) this.getNavigation()).setCanOpenDoors(true);
        this.getNavigation().setCanFloat(true);
        this.setCanPickUpLoot(true);
    }

    public static void create(Location location, String entityName, Boolean invulnerable) {
        Level world = ((CraftWorld) location.getWorld()).getHandle();
        CustomVillager villager = new CustomVillager(EntityType.VILLAGER, world);
        villager.setPos(location.getX(), location.getY(), location.getZ());
        villager.removeAI();
        villager.goalSelector.addGoal(3, new TemptGoal(villager, 1.0, Ingredient.of(Blocks.EMERALD_BLOCK.asItem()), false));
        world.addFreshEntity(villager);
    }

    @Override
    public @NotNull AttributeMap getAttributes() {
        return super.getAttributes();
    }

    private void removeAI() {
        this.goalSelector.getAvailableGoals().clear();
        this.targetSelector.getAvailableGoals().clear();
    }

}
