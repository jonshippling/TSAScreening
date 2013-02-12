import akka.actor.Actor
import akka.actor.ActorSystem
import akka.actor.Props
import scala.collection.mutable.Queue

case object StatusRequest
case class Bag(id: Int)

class Line(lineNum: Int) extends Actor {
  	val security = context.system.actorOf(Props(new Security(lineNum)), name = "security" + lineNum)
	val bodyScan = context.actorOf(Props(new BodyScan(lineNum, security)), name = "bodyScan")
	val bagScan = context.actorOf(Props(new BagScan(lineNum, security)), name = "bagScan")
	
	val ticketQueue: Queue[Ticket] = new Queue();


	def receive = {
	  case Ticket(id) =>
	    println("Passenger " + id + "'s bag is sent to the Bag Scan in line " + lineNum)
	    bagScan ! Bag(id)
	  	context.system.actorOf(Props(new Actor {
	  	  override def preStart(){
	  	    println("Passenger " + id + " checks to see if the Body Scan is available in line " + lineNum)
	  		bodyScan ! StatusRequest
	  	  }
	  	
	      def receive = {
	        case Status(available) =>
	          if (available) {
	            println("The Body Scan in line " + lineNum + " is available so Passenger " + id + " steps into it")
	        	sender ! Ticket(id)
	          } else {
	            println("The Body Scan in line " + lineNum + " is unavailable so Passenger " + id + " waits in line " + lineNum)
	        	ticketQueue.enqueue(Ticket(id))
	          }
	      }
	  	}))
	  	
	  case BodyScanAvailable =>
	    println("The next passenger in line " + lineNum + " is sent to the Body Scan")
	    sender ! ticketQueue.dequeue()
	}
  
}