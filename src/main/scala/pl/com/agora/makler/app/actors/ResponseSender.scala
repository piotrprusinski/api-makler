package pl.com.agora.makler.app.actors

import scala.concurrent.duration.SECONDS
import scala.concurrent.duration.pairIntToDuration

import akka.actor.Actor
import akka.actor.ActorLogging
import akka.actor.PoisonPill
import akka.actor.ReceiveTimeout
import akka.actor.actorRef2Scala
import pl.com.agora.makler.app.messages.ErrorProcesingRequest
import pl.com.agora.makler.app.messages.SetTimeout
import spray.http.HttpResponse
import spray.routing.RequestContext

class ResponseSender(ctx: RequestContext) extends Actor with ActorLogging {

  context.setReceiveTimeout(20, SECONDS)

  def receive: Receive = {
    
    case respons: HttpResponse => {
    	log.debug("return response")
      ctx.complete(respons)
      self ! PoisonPill
    }
    
    case ReceiveTimeout => {
      log.debug("kill yourself by poison!")
      ctx.complete("{error:1, msg:timeout}")
      self ! PoisonPill
    }
    case msgTimeout: SetTimeout => {
      log.debug("setTimeout from space")
      context.setReceiveTimeout(msgTimeout.duration)
      ctx.complete("{error:1, msg:timeout}")
    }
    case a : ErrorProcesingRequest => {
      ctx.complete("{error:1, msg:'${a.msg}'}")
      self ! PoisonPill
    }
    case _ => 
      ctx.complete("{error:1, msg:'never happened'}")
      self ! PoisonPill

  }

}