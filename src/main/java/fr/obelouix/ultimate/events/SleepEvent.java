package fr.obelouix.ultimate.events;

import fr.obelouix.ultimate.api.MessagesAPI;
import net.kyori.adventure.text.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.progress.ChunkProgressListener;
import net.minecraft.server.players.SleepStatus;
import net.minecraft.world.level.CustomSpawner;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.storage.LevelStorageSource;
import net.minecraft.world.level.storage.PrimaryLevelData;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.event.Listener;
import org.bukkit.generator.BiomeProvider;
import org.bukkit.generator.ChunkGenerator;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;

public class SleepEvent implements Listener {

    /*    @EventHandler
        public void onPlayerSleep(SleepStatus event){
            int percentage = event.getPlayer().getWorld().getGameRuleValue(GameRule.PLAYERS_SLEEPING_PERCENTAGE);
            final BossBar bossBar = BossBar.bossBar(Component.text(percentage), 0.2f, BossBar.Color.GREEN, BossBar.Overlay.PROGRESS);
            MessagesAPI.sendMessage(event.getPlayer(), Component.text("test"));
            MessagesAPI.showBossBar(event.getPlayer(), bossBar);
        }*/
    public static class CustomServerLevel extends ServerLevel {

        private SleepStatus sleepStatus;

        public CustomServerLevel(MinecraftServer minecraftserver, Executor executor, LevelStorageSource.LevelStorageAccess convertable_conversionsession, PrimaryLevelData iworlddataserver, ResourceKey<Level> resourcekey, LevelStem worlddimension, ChunkProgressListener worldloadlistener, boolean flag, long i, List<CustomSpawner> list, boolean flag1, World.Environment env, ChunkGenerator gen, BiomeProvider biomeProvider) {
            super(minecraftserver, executor, convertable_conversionsession, iworlddataserver, resourcekey, worlddimension, worldloadlistener, flag, i, list, flag1, env, gen, biomeProvider);
        }

        @Override
        public void updateSleepingPlayerList() {
            if (!this.players.isEmpty() && this.sleepStatus.update(this.players)) {
                this.players.forEach(serverPlayer -> MessagesAPI.sendMessage(Objects.requireNonNull(Bukkit.getPlayer(serverPlayer.getUUID())), Component.text("test")));
            }
        }
    }
}
