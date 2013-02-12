import akka.actor.Actor
import akka.actor.ActorRef

case object Begin
case class Document(id: Int)
case class Ticket(id: Int)
case class ValidDocument(lineNum: Int)
case object InvalidDocument

class Passenger(id: Int, documentCheck: ActorRef, queues: Array[ActorRef]) extends Actor {
	
  override def preStart() = {
	  println("Passenger " + id + " has arrived")
	  println("Passenger " + id + " sends document to Document Check")
	  documentCheck ! Document(id)
  }
	def receive = {
	  case ValidDocument(lineNum) =>
	    println("Passenger " + id + " goes to line " + lineNum)
		queues(lineNum) ! Ticket(id)
	}
	


}