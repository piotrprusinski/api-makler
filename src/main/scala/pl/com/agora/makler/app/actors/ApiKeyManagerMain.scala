package pl.com.agora.makler.app.actors

import akka.actor.ActorSystem
import akka.actor.Props
import pl.com.agora.makler.app.messages.HandleRequest
import pl.com.agora.makler.app.messages.LearnApiKey

object ApiKeyManagerMain {
  def main(args: Array[String]): Unit = {
    implicit val system = ActorSystem ()
    

    val secureResponseCache = system.actorOf(Props[SecureResponseCache] )
    val apiKeyManager = system.actorOf(Props(classOf[ApiKeyManager],secureResponseCache))

    apiKeyManager ! new HandleRequest(new ApiKey("adfasdfsdfasdf"), system.actorOf(Props[DumyActor]))
    apiKeyManager ! new HandleRequest(new ApiKey("adfasdfsdfasdf"), system.actorOf(Props[DumyActor] ))
    apiKeyManager ! new HandleRequest(new ApiKey("adfasdfsdfasdf2"), system.actorOf(Props[DumyActor] ))
    apiKeyManager ! new HandleRequest(new ApiKey("adfasdfsdfasdf3"), system.actorOf(Props[DumyActor] ))
    apiKeyManager ! new LearnApiKey(new ApiKey("adfasdfsdfasdf3"))
    apiKeyManager ! new LearnApiKey(new ApiKey("adfasdfsdfasdf4"))
    apiKeyManager ! new HandleRequest(new ApiKey("adfasdfsdfasdf4"), system.actorOf(Props[DumyActor] ))
    apiKeyManager ! new HandleRequest(new ApiKey("adfasdfsdfasdf4"), system.actorOf(Props[DumyActor] ))
    apiKeyManager ! new HandleRequest(new ApiKey("adfasdfsdfasdf3"), system.actorOf(Props[DumyActor] ))
    Thread.sleep(10000)
    system.terminate()
    
  }
}