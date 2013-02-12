import akka.actor.Actor
import akka.actor.ActorRef

case class Status(available: Boolean)
case object BodyScanAvailable
case class BodyReport(id: Int, passed: Boolean)

class BodyScan(lineNum: Int, security: ActorRef) extends Actor {
	var isAvailable = true
	
	
  def receive = {
	  case StatusRequest =>
	    sender ! Status(isAvailable)
	  case Ticket(id) =>
	    println("The Body Scan in line " + lineNum + " is unavailable")
	    isAvailable = false
	    println("The Body Scan in line " + lineNum + " sends security in line " + lineNum + " a report")
	    security ! BodyReport(id, true) //TODO: 20 percent rand
	    println("The Body Scan in line " + lineNum + " is available and notifies the line")
	    isAvailable = true
	    sender ! BodyScanAvailable
  }
  
}