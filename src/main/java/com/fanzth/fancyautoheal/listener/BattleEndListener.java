package com.fanzth.fancyautoheal.listener;

import com.pixelmonmod.pixelmon.api.events.battles.BattleEndEvent;
import com.pixelmonmod.pixelmon.battles.controller.BattleController;
import com.pixelmonmod.pixelmon.battles.controller.participants.PlayerParticipant;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.server.permission.PermissionAPI;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class BattleEndListener {

    @SubscribeEvent
    public void onBattleEnd(BattleEndEvent event) {
        BattleController controller = event.getBattleController();
        List<PlayerParticipant> players = controller.getPlayers();

        if (players.size() == 1) {
            ServerPlayerEntity player = (ServerPlayerEntity) players.get(0).getEntity();
            handleAutoHeal(player);
        }
        else if (players.size() == 2) {
            for (PlayerParticipant participant : players) {
                ServerPlayerEntity player = (ServerPlayerEntity) participant.getEntity();
                handleAutoHeal(player);
            }
        }
    }

    private void handleAutoHeal(ServerPlayerEntity player) {
        String permissionNode = "fancy.autoheal";
        Player bukkitPlayer = Bukkit.getPlayer(player.getUUID());

        if (bukkitPlayer == null) return;
        if (!hasPermission(player, permissionNode)) return;

        String title = "§eAUTO HEAL";              // สีเหลือง
        String subtitle = "§fReady For Battle";     // สีขาว
        int fadeIn = 10;     // ticks
        int stay = 60;
        int fadeOut = 20;

        bukkitPlayer.sendTitle(title, subtitle, fadeIn, stay, fadeOut);
        bukkitPlayer.performCommand("pokeheal");
        bukkitPlayer.playSound(
                bukkitPlayer.getLocation(),       // ตำแหน่งของผู้เล่น
                "minecraft:entity.player.levelup", // ชื่อเสียง
                1.0f,                              // ความดัง
                1.0f                               // ความเร็ว/โทน
        );
    }

    public static boolean hasPermission(ServerPlayerEntity player, String permissionNode) {
        return PermissionAPI.hasPermission(player, permissionNode);
    }
}
