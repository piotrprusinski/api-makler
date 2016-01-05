package pl.com.agora.makler.app.actors

import akka.actor.Actor
import akka.actor.Props
import akka.actor.actorRef2Scala
import pl.com.agora.makler.app.messages.HandleRequest

class SecurityGuard extends Actor {

	def receive = {
		case apiRequest: HandleRequest => handleRequest(apiRequest)
		case securityResponse: SecurityResponse => handleSecurityResponse(securityResponse)
	}

	def handleRequest(apiRequest: HandleRequest) = {
		getSecurityCheck ! apiRequest
	}

	def handleSecurityResponse(securityResponse: SecurityResponse) = {
		if (securityResponse.successful) {
			sendBackendRequest(securityResponse.originalRequest)
		}
	}

	def sendBackendRequest(request: HandleRequest) = {
		getBackendRequestSender ! request
	}

	def getSecurityCheck = {
		context.actorOf(Props[SecureResponseCache])
	}

	def getBackendRequestSender = {
		context.actorOf(Props[BackendRequestSender])
	}
}