package pl.com.agora.makler.app.actors

class ApiKey (val apiKey : String) extends Equals {
  
  override def toString() : String = {
    apiKey
  }

  def canEqual(other: Any) = {
    other.isInstanceOf[pl.com.agora.makler.app.actors.ApiKey]
  }

  override def equals(other: Any) = {
    other match {
      case that: pl.com.agora.makler.app.actors.ApiKey => that.canEqual(ApiKey.this) && apiKey == that.apiKey
      case _ => false
    }
  }

  override def hashCode() = {
    val prime = 41
    prime + apiKey.hashCode
  }
  
}