package me.syari.ss.chat

import me.syari.ss.chat.ConfigLoader.discordHookChannel
import me.syari.ss.chat.ConfigLoader.discordReceiveFormat
import me.syari.ss.chat.ConfigLoader.discordSendFormat
import me.syari.ss.core.auto.Event
import me.syari.ss.core.code.StringEditor.toUncolor
import me.syari.ss.core.message.Message.broadcast
import me.syari.ss.discord.entities.TextChannel
import me.syari.ss.discord.paper.DiscordMessageReceiveEvent
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler

object DiscordHook: Event {
    fun send(player: Player, message: String){
        discordHookChannel?.let {
            TextChannel.get(it)?.sendMessage(formatSend(player, message))
        }
    }

    private fun formatSend(player: Player, message: String): String {
        return discordSendFormat.replace("%sender%", player.displayName).replace("%message%", message).toUncolor
    }

    @EventHandler
    fun on(e: DiscordMessageReceiveEvent){
        val channel = e.channel
        if(channel.idLong == discordHookChannel){
            broadcast(formatReceive(e))
        }
    }

    private fun formatReceive(e: DiscordMessageReceiveEvent): String {
        val senderName = e.member?.displayName ?: e.user.name
        val message = e.contentDisplay
        return discordReceiveFormat.replace("%sender%", senderName).replace("%message%", message)
    }
}