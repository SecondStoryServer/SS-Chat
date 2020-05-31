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
            tab { _, _ -> element("discord", "reload") }
        ){ sender, args ->
            when(args.whenIndex(0)){
                "discord" -> {
                    val message = if(enableDiscord) {
                        "&aDiscord は有効になっています &7(接続先: #${DiscordHook.hookChannel})"
                    } else{
                        "&cDiscord は無効になっています"
                    }
                    sendWithPrefix(message)
                }
                "reload" -> {
                    sendWithPrefix("&fコンフィグを再読み込みします")
                    loadConfig(sender)
                    sendWithPrefix("&fコンフィグを再読み込みしました")
                }
                else -> sendHelp(
                    "chat discord" to "Discord が有効かどうかを取得します",
                    "chat reload" to "コンフィグを再読み込みします"
                )
            }
        }
    }
}