package pl.com.agora.makler.app.messages

import pl.com.agora.makler.app.actors.ApiKey

class HandleRequest(val apiKey:ApiKey) {
  
    override def toString() : String = {
    "HandleRequest with apiKey: " + apiKey.apiKey
  }
  
}