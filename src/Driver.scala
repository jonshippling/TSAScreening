import akka.actor.Actor
import akka.actor.Props
import akka.actor.ActorSystem
import akka.actor.ActorRef

/*object constants {
  val NumPassengers = 5
}*/

object Driver extends App {
    val NumPassengers = 5
	val NumQueues = 4
	
	val system = ActorSystem("MySystem")
	val jail = system.actorOf(Props[Jail], name = "jail")
	val gate = system.actorOf(Props[Gate], name = "gate")
	
	val lines: Array[ActorRef] = new Array(NumQueues)
      
  	val documentCheck = system.actorOf(Props(new DocumentCheck(NumQueues)), name = "documentCheck")

  	// make the queues
	for (i <- 0 until NumQueues) {
	  lines(i) = system.actorOf(Props(new Line(i)), name = "line" + i)
	}
	
	for(i <- 0 until NumPassengers) {
	  val curPassenger = system.actorOf(Props(new Passenger(i, documentCheck, lines)), name = "" + i)
	  
	}
		
  
}