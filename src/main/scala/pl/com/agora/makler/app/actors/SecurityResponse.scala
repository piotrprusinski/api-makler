package pl.com.agora.makler.app.actors

import pl.com.agora.makler.app.messages.HandleRequest

class SecurityResponse(val successful : Boolean, val originalRequest : HandleRequest) {  
}