package me.syari.ss.chat.direct

import org.bukkit.command.CommandSender

object DirectMessage {
    private val lastDirectMessagePartnerMap = mutableMapOf<DirectMessageSender, DirectMessageSender>()

    var CommandSender.lastDirectMessagePartner
        get() = DirectMessageSender.from(this)?.let { lastDirectMessagePartnerMap[it] }
        set(value) {
            val directMessageSender = DirectMessageSender.from(this) ?: return
            if(value != null){
                lastDirectMessagePartnerMap[directMessageSender] = value
            } else {
                lastDirectMessagePartnerMap.remove(directMessageSender)
            }
        }
}