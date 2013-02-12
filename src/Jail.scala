import akka.actor.Actor

case object RequestGateCount
case class JailCount(count: Int)

class Jail extends Actor {

  var jailCount = 0
  
  def receive = {
    case Ticket(id) =>
      jailCount += 1
      Driver.gate ! RequestGateCount
    case RequestJailCount =>
      sender ! JailCount(jailCount)
    case GateCount(count) =>
      if (count + jailCount == Driver.NumPassengers) {
        context.system.shutdown
      }
  }
  
  override def postStop() {
	println("Jail closes")
  }
  
}