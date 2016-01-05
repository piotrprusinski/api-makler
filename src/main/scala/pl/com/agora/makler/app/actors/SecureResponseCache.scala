package pl.com.agora.makler.app.actors

import akka.actor.Actor
import akka.actor.ActorLogging
import pl.com.agora.makler.app.messages.HandleRequest
import akka.actor.ActorRef
import akka.actor.Props
import spray.caching.Cache
import spray.caching.LruCache
import java.net.SecureCacheResponse
import scala.concurrent.impl.Future
import scala.concurrent.Future
import scala.concurrent.Promise

class SecureResponseCache extends Actor with ActorLogging {

	val securityResponseCache = LruCache[SecurityResponse]()
	val securityRequestCache = LruCache[Promise[SecurityResponse]]()
	val system = context.system
	import system.dispatcher

	def receive = {

		case msg: HandleRequest => {
			log.debug("Handling LearnApiKey message : {}", msg)
			getFromSecurityCacheOrCreateRequest(msg)
		}
		case securityResponse: SecurityResponse => {
			log.debug("Handling security response message : {}", securityResponse)
			updateCacheWithSecurityResponse(securityResponse);
		}

	}

	def getApiSecuritySender(): ActorRef = {
		context.actorOf(Props[DumyActor])
	}

	def getFromSecurityCacheOrCreateRequest(request: HandleRequest): Future[SecurityResponse] = {
		val cacheKey = new SecurityCacheKey(request.resourcePattern, request.apiKey.apiKey)
		securityResponseCache(cacheKey) {
			cacheElementDeliveryPromise: Promise[SecurityResponse] =>
				{
					remember(cacheKey, cacheElementDeliveryPromise)
					sendToSecurity(request)
				}
		}
	}

	def sendToSecurity(request: HandleRequest): Unit = {
		getApiSecuritySender ! request
	}

	def remember(key: SecurityCacheKey, cacheElementDeliveryPromise: Promise[SecurityResponse]) = {
		securityRequestCache(key) {
			cacheElementDeliveryPromise
		}
	}

	def respondWithSecurityResponse(x: AnyRef): Unit = {

	}

	def updateCacheWithSecurityResponse(securityResponse: SecurityResponse) = {
		val request = securityResponse.originalRequest
		val cacheKey = new SecurityCacheKey(request.resourcePattern, request.apiKey.apiKey)
		val extracted: Option[Future[Promise[SecurityResponse]]] = securityRequestCache.get(cacheKey)
		extracted.foreach {
			fut: Future[Promise[SecurityResponse]] => fut.onSuccess{ 
				case promise : Promise[SecurityResponse] => {
					promise.success(securityResponse)
				}
			}
		}
	}
}