package me.syari.ss.chat

import me.syari.ss.core.auto.Event
import me.syari.ss.core.auto.OnEnable
import org.bukkit.plugin.java.JavaPlugin

class Main: JavaPlugin() {
    companion object {
        internal lateinit var chatPlugin: JavaPlugin

        internal var enableDiscord = false
    }

    override fun onEnable() {
        chatPlugin = this
        enableDiscord = server.pluginManager.isPluginEnabled("SS-Discord")
        OnEnable.register(ConfigLoader, CommandCreator)
        Event.register(this, ChatEventListener)
        if(enableDiscord) Event.register(this, DiscordHook)
    }
}