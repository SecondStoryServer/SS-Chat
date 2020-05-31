package me.syari.ss.chat

import me.syari.ss.chat.ConfigLoader.loadConfig
import me.syari.ss.chat.Main.Companion.chatPlugin
import me.syari.ss.chat.Main.Companion.enableDiscord
import me.syari.ss.core.auto.OnEnable
import me.syari.ss.core.command.create.CreateCommand.createCommand
import me.syari.ss.core.command.create.CreateCommand.element
import me.syari.ss.core.command.create.CreateCommand.tab

object CommandCreator: OnEnable {
    override fun onEnable() {
        createCommand(chatPlugin, "chat", "SS-Chat",
            tab { _, _ -> element("discord") }
        ){ sender, args ->
            when(args.whenIndex(0)){
                "discord" -> {
                    sendWithPrefix(if(enableDiscord) "&aDiscord は有効になっています" else "&cDiscord は無効になっています")
                }
                "reload" -> {
                    sendWithPrefix("&fコンフィグを再読み込みします")
                    loadConfig(sender)
                    sendWithPrefix("&fコンフィグを再読み込みしました")
                }
            }
        }
    }
}