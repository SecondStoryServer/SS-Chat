package me.syari.ss.chat.direct

import me.syari.ss.core.Main.Companion.console
import me.syari.ss.core.player.UUIDPlayer
import org.bukkit.command.CommandSender
import org.bukkit.command.ConsoleCommandSender
import org.bukkit.entity.Player

interface DirectMessageSender {
    val sender: CommandSender?

    data class Player(val uuidPlayer: UUIDPlayer): DirectMessageSender {
        override val sender: CommandSender?
            get() = uuidPlayer.player
    }

    object Console: DirectMessageSender {
        override val sender: CommandSender?
            get() = console
    }

    companion object {
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