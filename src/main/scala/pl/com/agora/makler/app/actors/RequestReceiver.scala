package pl.com.agora.makler.app.actors

import scala.annotation.implicitNotFound

import akka.actor.ActorRef
import akka.actor.ActorRefFactory
import akka.actor.Props
import akka.actor.actorRef2Scala
import pl.com.agora.makler.app.messages.MatchRequest
import spray.routing.Directives
import spray.routing.HttpServiceActor
import spray.routing.RequestContext
import spray.routing.Route

class RequestReceiver(route: Route) extends HttpServiceActor {
  def receive: Receive = runRoute(route)
}

object RequestReceiver extends Directives {

  def route(system: ActorRefFactory): Route = {

    val urlMatcher = system.actorOf(Props[UrlMatcher])
    (ctx : RequestContext) => {
     val responseSender =  prepareResponseSender(system, ctx)
     urlMatcher ! new MatchRequest(ctx.request.uri.path.toString(), responseSender)}
  }
  
  def prepareResponseSender(actorRefFactory: ActorRefFactory, ctx: RequestContext): ActorRef = {
    actorRefFactory.actorOf(Props(new ResponseSender(ctx)))
  }
}