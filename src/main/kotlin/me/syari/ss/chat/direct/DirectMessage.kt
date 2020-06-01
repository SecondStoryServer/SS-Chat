package me.syari.ss.chat.direct

object DirectMessage {
    private val lastDirectMessagePartnerMap = mutableMapOf<DirectMessageSender, DirectMessageSender>()

    var DirectMessageSender.lastDirectMessagePartner
        get() = lastDirectMessagePartnerMap[this]
        set(value) {
            if(value != null){
                lastDirectMessagePartnerMap[this] = value
            } else {
                lastDirectMessagePartnerMap.remove(this)
            }
        }
}