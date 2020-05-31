package me.syari.ss.chat

import me.syari.ss.chat.ConfigLoader.chatFormat
import me.syari.ss.chat.Main.Companion.enableDiscord
import me.syari.ss.core.auto.Event
import me.syari.ss.core.code.StringEditor.toColor
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.player.AsyncPlayerChatEvent

object ChatEventListener: Event {
    @EventHandler
    fun on(e: AsyncPlayerChatEvent) {
        val player = e.player
        val message = e.message
        e.message = format(player, message)
        if(enableDiscord) DiscordHook.send(player, message)
    }

    private fun format(sender: Player, message: String): String {
        return chatFormat.replace("%sender%", sender.displayName).replace("%message%", message).toColor
    }
}