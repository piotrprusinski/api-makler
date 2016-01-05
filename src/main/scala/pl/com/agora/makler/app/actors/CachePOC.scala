package pl.com.agora.makler.app.actors

import scala.concurrent.Future
import akka.actor.ActorSystem
import spray.caching.Cache
import spray.caching.LruCache
import scala.concurrent.Promise
import scala.collection.mutable.Map

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

		val promises: Map[Any, Promise[Double]] = Map[Any, Promise[Double]]()
		// we can wrap the operation with caching support
		// (providing a caching key)
		def cachedOp[T](key: T): Future[Double] = {
			println(s"cachedOp($key)")
			cache(key) {
				x: Promise[Double] => {
					println(s"remembering promise $x for key $key")
					promises(key) = x
				}
			}
		}

		//		cache("abc")(Promise[Double]() => 3.2)
		println("getting fut")
		val fut: Future[Double] = cachedOp("abc");
		println("getting fut2")
		val fut2 = cachedOp("abc")
		fut.onSuccess {
			case x => println("fut callback " + x);
		}
		fut2.onSuccess {
			case x => println("fut2 callback " + x);
		}
		println("keeping promise")
		promises("abc") success 5.1

	}
}