package pl.com.agora.makler.app.actors

import akka.actor.ActorSystem
import akka.actor.Props
import pl.com.agora.makler.app.messages.HandleRequest
import pl.com.agora.makler.app.messages.LearnApiKey

object ApiKeyManagerMain {
  def main(args: Array[String]): Unit = {
    implicit val system = ActorSystem ()
    
    val actor = system.actorOf(Props[ApiKeyManager] )
    actor ! new HandleRequest(new ApiKey("adfasdfsdfasdf"), system.actorOf(Props[DumyActor]))
    actor ! new HandleRequest(new ApiKey("adfasdfsdfasdf"), system.actorOf(Props[DumyActor] ))
    actor ! new HandleRequest(new ApiKey("adfasdfsdfasdf2"), system.actorOf(Props[DumyActor] ))
    actor ! new HandleRequest(new ApiKey("adfasdfsdfasdf3"), system.actorOf(Props[DumyActor] ))
    actor ! new LearnApiKey(new ApiKey("adfasdfsdfasdf3"))
    actor ! new LearnApiKey(new ApiKey("adfasdfsdfasdf4"))
    actor ! new HandleRequest(new ApiKey("adfasdfsdfasdf4"), system.actorOf(Props[DumyActor] ))
    Thread.sleep(1000)
    system.terminate()
    
  }
}