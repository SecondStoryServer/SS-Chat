package me.syari.ss.chat.direct

import me.syari.ss.core.Main.Companion.console
import me.syari.ss.core.player.UUIDPlayer
import org.bukkit.command.CommandSender
import org.bukkit.command.ConsoleCommandSender

interface DirectMessageSender {
    val sender: CommandSender?

    var lastDMPartner
        get() = lastDirectMessagePartnerMap[this]
        set(value) {
            if(value != null){
                lastDirectMessagePartnerMap[this] = value
            } else {
                lastDirectMessagePartnerMap.remove(this)
            }
        }

    fun sendDM(sendTo: DirectMessageSender, message: String){
        // 送信処理
        lastDMPartner = sendTo
        sendTo.lastDMPartner = this
    }

    data class Player(val uuidPlayer: UUIDPlayer): DirectMessageSender {
        override val sender: CommandSender?
            get() = uuidPlayer.player
    }

    object Console: DirectMessageSender {
        override val sender: CommandSender?
            get() = console
    }

    companion object {
        private val lastDirectMessagePartnerMap = mutableMapOf<DirectMessageSender, DirectMessageSender>()

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
    }
}