package me.syari.ss.chat.direct

import me.syari.ss.chat.ChatEventListener.formatJp
import me.syari.ss.chat.ConfigLoader.dmFormat
import me.syari.ss.chat.converter.IMEConverter
import me.syari.ss.core.Main.Companion.console
import me.syari.ss.core.message.Message.send
import me.syari.ss.core.player.UUIDPlayer
import org.bukkit.command.CommandSender
import org.bukkit.command.ConsoleCommandSender

interface DirectMessageSender {
    val sender: CommandSender?

    val senderName
        get() = sender?.name.toString()

    var lastDMPartner
        get() = lastDMPartnerMap[this]
        set(value) {
            if(value != null){
                lastDMPartnerMap[this] = value
            } else {
                lastDMPartnerMap.remove(this)
            }
        }

    fun sendDM(sendTo: DirectMessageSender, message: String){
        val sendMessage = dmFormat.formatName(this, sendTo).replace("%message%", getMessage(message))
        sender?.send(sendMessage)
        sendTo.sender?.send(sendMessage)
        if(this !is Console && sendTo !is Console) console.send(sendMessage)
        lastDMPartner = sendTo
        sendTo.lastDMPartner = this
    }

    data class Player(val uuidPlayer: UUIDPlayer): DirectMessageSender {
        override val sender: CommandSender?
            get() = uuidPlayer.player
    }

    object Console: DirectMessageSender {
        override val sender: CommandSender
            get() = console
    }

    companion object {
        private val lastDMPartnerMap = mutableMapOf<DirectMessageSender, DirectMessageSender>()

        fun from(player: org.bukkit.entity.Player): DirectMessageSender {
            return Player(UUIDPlayer(player))
        }

        fun from(commandSender: CommandSender): DirectMessageSender? {
            return when(commandSender){
                is org.bukkit.entity.Player -> Player(UUIDPlayer(commandSender))
                is ConsoleCommandSender -> Console
                else -> null
            }
        }

        private fun String.formatName(sender: DirectMessageSender, sendTo: DirectMessageSender): String {
            return replace("%sender%", sender.senderName).replace("%sendTo%", sendTo.senderName)
        }

        private fun getMessage(message: String): String {
            val jpMessage = IMEConverter.convertWithIMEFromRoma(message)
            return if(jpMessage == message){
                message
            } else {
                formatJp(message, jpMessage)
            }
        }
    }
}