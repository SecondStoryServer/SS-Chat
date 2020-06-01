package me.syari.ss.chat

import me.syari.ss.chat.ConfigLoader.loadConfig
import me.syari.ss.chat.Main.Companion.chatPlugin
import me.syari.ss.chat.Main.Companion.enableDiscord
import me.syari.ss.chat.direct.DirectMessageSender
import me.syari.ss.core.auto.OnEnable
import me.syari.ss.core.command.create.CreateCommand.createCommand
import me.syari.ss.core.command.create.CreateCommand.element
import me.syari.ss.core.command.create.CreateCommand.onlinePlayers
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

        createCommand(chatPlugin, "tell", "SS-Chat",
            tab { _, _ -> onlinePlayers.joinIf(true, "Console") },
            alias = listOf("t")
        ){ sender, args ->
            val dmSender = DirectMessageSender.from(sender) ?: return@createCommand
            if(args.isEmpty) return@createCommand sendError("送信先を入力してください")
            val sendTo = if(args[0].equals("console", true)){
                DirectMessageSender.Console
            } else {
                val sendToPlayer = args.getPlayer(0, false) ?: return@createCommand
                DirectMessageSender.from(sendToPlayer)
            }
            val message = args.slice(1 until args.size).joinToString(" ")
            dmSender.sendDM(sendTo, message)
        }

        createCommand(chatPlugin, "reply", "SS-Chat", alias = listOf("r")){ sender, args ->
            val dmSender = DirectMessageSender.from(sender) ?: return@createCommand
            val lastDMPartner = dmSender.lastDMPartner ?: return@createCommand sendError("返信先がありません")
            val message = args.joinToString(" ")
            dmSender.sendDM(lastDMPartner, message)
        }
    }
}