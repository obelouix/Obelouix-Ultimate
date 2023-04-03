package fr.obelouix.ultimate.features.entity;

import fr.obelouix.ultimate.I18N.Translator;
import fr.obelouix.ultimate.api.MessagesAPI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.bukkit.entity.Tameable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityTameEvent;

public class PetProtection implements Listener {

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void blockPetDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Tameable tameable && tameable.isTamed()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void alertTamer(EntityTameEvent event) {

        final Player player = (Player) event.getOwner();
        final Component entity = switch (event.getEntityType()) {
            case CAT -> Component.translatable("entity.minecraft.cat");
            case WOLF -> Component.translatable("entity.minecraft.wolf");
            case PARROT -> Component.translatable("entity.minecraft.parrot");
            default -> Component.empty();
        };

        final Component message = Translator.translate(Component.translatable("obelouix.animal.new.tamed.protection", NamedTextColor.AQUA), player.locale());

        MessagesAPI.sendMessage(player, message
                .replaceText(TextReplacementConfig.builder()
                        .matchLiteral("%tamedentity%")
                        .replacement(entity.color(NamedTextColor.GREEN))
                        .build())
        );
    }

}
