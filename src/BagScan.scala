import akka.actor.Actor
import akka.actor.ActorRef

case class BagReport(id: Int, passed: Boolean)

class BagScan(lineNum: Int, security: ActorRef) extends Actor {
  def receive = {
	  case Bag(id) =>
	    println("The Bag Scan in line " + lineNum + " receives Passenger " + id + "'s bag and sends security a report")
	    security ! BagReport(id, true) // TODO: rand
  }
  
}