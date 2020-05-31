package me.syari.ss.chat

import me.syari.ss.chat.ChatEventListener.replaceJp
import me.syari.ss.chat.ChatEventListener.replaceWith
import me.syari.ss.chat.ConfigLoader.discordHookChannel
import me.syari.ss.chat.ConfigLoader.discordReceiveFormat
import me.syari.ss.chat.ConfigLoader.discordSendFormat
import me.syari.ss.chat.ConfigLoader.discordSendJpFormat
import me.syari.ss.core.auto.Event
import me.syari.ss.core.code.StringEditor.toUncolor
import me.syari.ss.core.message.Message.broadcast
import me.syari.ss.discord.entities.TextChannel
import me.syari.ss.discord.paper.DiscordMessageReceiveEvent
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler

object DiscordHook: Event {
    fun send(sender: Player, message: String){
        TextChannel.get(discordHookChannel)?.sendMessage(formatSend(sender, message))
    }

    fun send(sender: Player, message: String, jpMessage: String){
        TextChannel.get(discordHookChannel)?.sendMessage(formatSend(sender, message, jpMessage))
    }

    private fun formatSend(sender: Player, message: String): String {
        return discordSendFormat.replaceWith(sender, message).toUncolor
    }

    private fun formatSend(sender: Player, message: String, jpMessage: String): String {
        return discordSendJpFormat.replaceWith(sender, message).replaceJp(jpMessage).toUncolor
    }

    @EventHandler
    fun on(e: DiscordMessageReceiveEvent){
        if(e.user.isBot) return
        val channel = e.channel
        if(channel.idLong == discordHookChannel){
            broadcast(formatReceive(e))
        }
    }

    private fun formatReceive(e: DiscordMessageReceiveEvent): String {
        val senderName = e.member?.displayName ?: e.user.name
        val message = e.contentDisplay
        return discordReceiveFormat.replace("%sender%", senderName).replace("%message%", message.toUncolor)
    }
}