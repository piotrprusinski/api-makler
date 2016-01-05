package pl.com.agora.makler.app.actors

import scala.concurrent.Future

import akka.actor.Actor
import pl.com.agora.makler.app.messages.HandleRequest
import spray.client.pipelining._
import spray.http._

class SecurityRequestSender extends Actor {
	val securityHost = "localhost"
	val securityPort = 8080
	val system = context.system
	import system.dispatcher

	def receive = {
		case request: HandleRequest => sendSecurityRequest(request)
	}

	val pipeline: HttpRequest => Future[HttpResponse] = sendReceive
	val failedResponse = StatusCodes.GatewayTimeout

	def sendSecurityRequest(request: HandleRequest) {
		val requestUrl = buildRequestUrl(request)
		val responseFuture = pipeline(Get(requestUrl));
		responseFuture.onComplete { responseTry =>
			if (responseTry.isSuccess) {
				responseTry.get.status
			} else {
				failedResponse
			}
		}
	}

	def buildRequestUrl(request: HandleRequest) = {
		""
	}
}