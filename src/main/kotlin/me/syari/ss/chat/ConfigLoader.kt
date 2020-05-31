package me.syari.ss.chat

import me.syari.ss.chat.Main.Companion.chatPlugin
import me.syari.ss.core.Main.Companion.console
import me.syari.ss.core.auto.OnEnable
import me.syari.ss.core.config.CreateConfig.config
import me.syari.ss.core.config.dataType.ConfigDataType
import org.bukkit.command.CommandSender

object ConfigLoader: OnEnable {
    override fun onEnable() {
        loadConfig(console)
    }

    var chatFormat = "%sender%: %message%"
        private set

    var discordHookChannel: Long? = null
        private set

    var discordSendFormat = "**%sender%**: %message%"
        private set

    var discordReceiveFormat = "&5[Discord] &f%sender%: %message%"
        private set

    fun loadConfig(output: CommandSender) {
        config(chatPlugin, output, "config.yml") {
            chatFormat = get("format", ConfigDataType.STRING, chatFormat, false)
            discordHookChannel = get("discord.channel", ConfigDataType.LONG, false)
            discordSendFormat = get("discord.format.send", ConfigDataType.STRING, discordSendFormat, false)
            discordReceiveFormat = get("discord.format.receive", ConfigDataType.STRING, discordReceiveFormat, false)
        }
    }
}