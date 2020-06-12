package me.syari.ss.chat

import me.syari.discord.entity.api.TextChannel
import me.syari.ss.chat.ChatEventListener.formatChat
import me.syari.ss.discord.paper.DiscordMessageReceiveEvent
import me.syari.ss.chat.ConfigLoader.discordHookChannel
import me.syari.ss.chat.ConfigLoader.discordReceiveFormat
import me.syari.ss.chat.ConfigLoader.discordSendFormat
import me.syari.ss.core.auto.Event
import me.syari.ss.core.code.StringEditor.toUncolor
import me.syari.ss.core.message.Message.broadcast
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler

object DiscordHook: Event {
    val hookChannel
        get() = TextChannel.get(discordHookChannel)?.name

    fun send(sender: Player, message: String){
        TextChannel.get(discordHookChannel)?.send(formatChat(sender, message))
    }

    private fun formatChat(sender: Player, message: String): String {
        return discordSendFormat.formatChat(sender, message).toUncolor
    }

    @EventHandler
    fun on(e: DiscordMessageReceiveEvent){
        if(e.author.isBot) return
        val channel = e.channel
        if(channel.id == discordHookChannel){
            broadcast(formatReceive(e))
        }
    }

    private fun formatReceive(e: DiscordMessageReceiveEvent): String {
        val senderName = e.author.displayName
        val message = e.contentDisplay
        return discordReceiveFormat.replace("%sender%", senderName).replace("%message%", message.toUncolor)
    }
}