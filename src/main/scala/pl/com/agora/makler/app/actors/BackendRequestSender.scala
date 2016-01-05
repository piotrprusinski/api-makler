package pl.com.agora.makler.app.actors

import pl.com.agora.makler.app.messages.BackendResponse
import akka.actor.Actor
import akka.actor.ActorLogging
import akka.actor.ActorRef
import akka.actor.Props
import pl.com.agora.makler.app.messages.HandleRequest
import pl.com.agora.makler.app.messages.BackendResponse
import scala.concurrent.Future
import spray.http._
import akka.io.IO
import spray.can.Http
import HttpMethods._

import akka.actor.ActorSystem
import akka.util.Timeout

class BackendRequestSender extends Actor with ActorLogging {
implicit val system: ActorSystem = ActorSystem()
//implicit val timeout: Timeout = Timeout(15, SECONDS)


  def receive  = {
    
    case msg : HandleRequest => {
      log.debug("Handling LearnApiKey message : {}", msg)
      getNewInstanceBackendRequestReciver() ! new BackendResponse(msg)
      

      
      //val response: Future[HttpResponse] =
        //(IO(Http) ? HttpRequest(GET, Uri("http://spray.io"))).mapTo[HttpResponse]
      
      context.stop(self)
    }
  }
  
   def getNewInstanceBackendRequestReciver ():ActorRef = {
    context.actorOf(Props[BackendRequestReceiver])
  }

  override def postStop = {
      log.debug("post selfkill")
    }   
   
}