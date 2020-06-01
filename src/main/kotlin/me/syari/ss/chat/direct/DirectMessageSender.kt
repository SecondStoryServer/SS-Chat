package me.syari.ss.chat.direct

import me.syari.ss.core.Main.Companion.console
import me.syari.ss.core.player.UUIDPlayer
import org.bukkit.command.CommandSender

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
}