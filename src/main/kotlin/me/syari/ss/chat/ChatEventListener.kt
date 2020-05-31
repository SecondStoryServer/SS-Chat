package me.syari.ss.chat

import me.syari.ss.chat.ConfigLoader.chatFormat
import me.syari.ss.chat.ConfigLoader.jpChatFormat
import me.syari.ss.chat.Main.Companion.enableDiscord
import me.syari.ss.chat.converter.IMEConverter
import me.syari.ss.core.auto.Event
import me.syari.ss.core.code.StringEditor.toColor
import me.syari.ss.core.code.StringEditor.toUncolor
import me.syari.ss.core.message.Message.broadcast
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.player.AsyncPlayerChatEvent

object ChatEventListener: Event {
    @EventHandler
    fun on(e: AsyncPlayerChatEvent) {
        val player = e.player
        val message = e.message
        e.isCancelled = true
        broadcast(format(player, message.toUncolor))
        if(enableDiscord) DiscordHook.send(player, message)
    }

    private fun format(sender: Player, message: String): String {
        return if(matchHalfWidthChar(message)){
            formatNormal(sender, message)
        } else {
            formatJapanese(sender, message)
        }
    }

    private fun formatNormal(sender: Player, message: String): String {
        return chatFormat.replaceWith(sender, message).toColor
    }

    private fun formatJapanese(sender: Player, message: String): String {
        val jpMessage = IMEConverter.convertWithIMEFromRoma(message)
        return if(jpMessage == message){
            formatNormal(sender, message)
        } else {
            jpChatFormat.replaceWith(sender, message).replaceJp(jpMessage).toColor
        }
    }

    private fun String.replaceWith(sender: Player, message: String): String {
        return replace("%sender%", sender.displayName).replace("%message%", message)
    }

    private fun String.replaceJp(jpMessage: String): String {
        return replace("%jp%", jpMessage)
    }

    private fun matchHalfWidthChar(text: String): Boolean {
        return text.matches("^[a-zA-Z0-9!-/:-@\\[-`{-~]*\$".toRegex())
    }
}