package pl.com.agora.makler.app.actors

import scala.concurrent.Future

import akka.actor.ActorSystem
import spray.caching.Cache
import spray.caching.LruCache

object CachePOC {
	def main(args: Array[String]): Unit = {
		val system = ActorSystem()
		import system.dispatcher

		// if we have an "expensive" operation
		def expensiveOp(): Double = {
			val result = new util.Random().nextDouble()
			println(s"generated $result")
			Thread.sleep(1000)
			result;
		}

		// and a Cache for its result type
		val cache: Cache[Double] = LruCache()

		// we can wrap the operation with caching support
		// (providing a caching key)
		def cachedOp[T](key: T): Future[Double] = cache(key) {
			expensiveOp()
		}

		// and profit
		val fut:Future[Double] = cachedOp("abc");
		val fut2 = cachedOp("abc")
		fut.onSuccess {
			case x => println(x);
		}
		fut2.onSuccess {
			case x => println(x);
		}

	}
}