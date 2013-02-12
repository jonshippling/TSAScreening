import akka.actor.Actor

class DocumentCheck(numQueues: Int) extends Actor {

	// kick back passengerID mod numQueues for requester
  def receive = {
	  case Document(id) =>
	    // TODO: invalid possibility
	    println("Document Check says Passenger " + id +"'s document is valid")
		sender ! ValidDocument(id % numQueues)
  }
}