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

    var jpFormat = "%jp% (%message%)"
        private set

    var chatFormat = "%sender%: %message%"
        private set

    var dmFormat = "%sendTo% << %sender%: %message%"
        private set

    var discordHookChannel = 0L
        private set

    var discordSendFormat = "**%sender%**: %message%"
        private set

    var discordReceiveFormat = "&5[Discord] &f%sender%: %message%"
        private set

    private val defaultConfig = mapOf(
        "format.jp" to jpFormat,
        "format.chat" to chatFormat,
        "format.dm" to dmFormat,
        "discord.channel" to discordHookChannel,
        "discord.format.send" to discordSendFormat,
        "discord.format.receive" to discordReceiveFormat
    )

    fun loadConfig(output: CommandSender) {
        config(chatPlugin, output, "config.yml", default = defaultConfig) {
            jpFormat = get("format.jp", ConfigDataType.STRING, jpFormat, false)
            chatFormat = get("format.chat", ConfigDataType.STRING, chatFormat, false)
            dmFormat = get("format.dm", ConfigDataType.STRING, dmFormat, false)
            discordHookChannel = get("discord.channel", ConfigDataType.LONG, discordHookChannel, false)
            discordSendFormat = get("discord.format.send", ConfigDataType.STRING, discordSendFormat, false)
            discordReceiveFormat = get("discord.format.receive", ConfigDataType.STRING, discordReceiveFormat, false)
        }
    }
}