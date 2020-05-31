package me.syari.ss.chat

import me.syari.ss.core.auto.Event
import me.syari.ss.core.auto.OnEnable
import me.syari.ss.core.message.ConsoleLogger
import org.bukkit.plugin.java.JavaPlugin

class Main: JavaPlugin() {
    companion object {
        internal lateinit var chatPlugin: JavaPlugin

        internal lateinit var chatLogger: ConsoleLogger

        internal var enableDiscord = false
    }

    override fun onEnable() {
        chatPlugin = this
        chatLogger = ConsoleLogger(this)
        enableDiscord = server.pluginManager.isPluginEnabled("SS-Discord")
        OnEnable.register(ConfigLoader, CommandCreator)
        Event.register(this, ChatEventListener)
        if(enableDiscord) {
            Event.register(this, DiscordHook)
            chatLogger.info("[SS-Chat] Discord を有効にしました")
        } else {
            chatLogger.info("[SS-Chat] Discord を無効にしました")
        }
    }
}