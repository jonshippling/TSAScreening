import akka.actor.Actor

case object RequestJailCount
case class GateCount(count: Int)

class Gate extends Actor {
  var gateCount = 0
  
  def receive = {
    case Ticket(id) =>
      gateCount += 1
      Driver.jail ! RequestJailCount
    case RequestGateCount =>
      sender ! GateCount(gateCount)
    case JailCount(count) =>
      if (count + gateCount == Driver.NumPassengers) {
        context.system.shutdown
      }
  }
  
  override def postStop() {
	println("Gate closes")
  }
  
}