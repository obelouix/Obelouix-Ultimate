package fr.obelouix.ultimate.entity;

import net.minecraft.core.DefaultedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class EntityRegistry {

    public EntityRegistry() {
        Registry.ENTITY_TYPE.get(EntityType.getKey(EntityType.VILLAGER));
        register(Registry.ENTITY_TYPE, "villager", EntityType.Builder.of(CustomVillager::new, MobCategory.MISC).sized(0.6F, 1.95F).clientTrackingRange(10));
    }

    private static <T extends Entity> EntityType<T> register(DefaultedRegistry<EntityType<?>> entityType, String id, EntityType.Builder type) { // CraftBukkit - decompile error
        return Registry.register(Registry.ENTITY_TYPE, id, (EntityType<T>) type.build(id)); // CraftBukkit - decompile error
    }

   /* public static void addAttribute(Creature creature, Attribute attribute) {
        final Creature nmsEntity = (Creature) ((CraftCreature) creature).getHandle();
        try {
            final AttributeProvider attributeProvider =
            final AttributeBase attributeBase = toMinecraftAttribute(attribute);
            if (attributeBase == null) {
                throw new IllegalStateException(attribute.name() + " is not an valid minecraft attribute");
            }
            final ImmutableMap attributes = ImmutableMap.builder()
                    .putAll(Reflection.getField(attributeProvider, "a", Map.class))
                    .put(attributeBase, new AttributeModifiable(attributeBase, attributeModifiable -> {
                        throw new UnsupportedOperationException("Tried to change value for default attribute instance: " +
                                IRegistry.ATTRIBUTE.getKey(attributeBase));
                    })).build();
            Reflection.setField(attributeProvider, "a", attributes);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new IllegalStateException("Could not add " + attribute.name() + " to entity", e);
        }
    }*/

}
