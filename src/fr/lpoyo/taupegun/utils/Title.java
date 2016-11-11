package fr.lpoyo.taupegun.utils;

import net.minecraft.server.v1_10_R1.IChatBaseComponent;
import net.minecraft.server.v1_10_R1.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_10_R1.PacketPlayOutChat;
import net.minecraft.server.v1_10_R1.PacketPlayOutTitle;
import net.minecraft.server.v1_10_R1.PacketPlayOutTitle.EnumTitleAction;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class Title {

    public static void sendTitle(Player player, String title, String subtitle, int ticks) {

        IChatBaseComponent chatTitle = ChatSerializer.a("{\"text\": \"" + title + "\"}");
        IChatBaseComponent chatSubTitle = ChatSerializer.a("{\"text\": \"" + subtitle + "\"}");

        PacketPlayOutTitle titre = new PacketPlayOutTitle(EnumTitleAction.TITLE, chatTitle);
        PacketPlayOutTitle soustitre = new PacketPlayOutTitle(EnumTitleAction.SUBTITLE, chatSubTitle);

        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(titre);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(soustitre);

        sendTime(player, ticks);

    }

    private static void sendTime(Player player, int ticks) {

        PacketPlayOutTitle p = new PacketPlayOutTitle(EnumTitleAction.TIMES, null, 20, ticks, 20);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(p);

    }

    public static void sendActionBar(Player player, String message) {
        IChatBaseComponent actionBar = ChatSerializer.a("{\"text\": \"" + message + "\"}");
        PacketPlayOutChat ab = new PacketPlayOutChat(actionBar, (byte) 2);

        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(ab);
    }
}
