import akka.actor.Actor
import scala.collection.mutable.HashMap



class Security(lineNum: Int) extends Actor {
	val passengerStatus: HashMap[Int, CombinedReport] = new HashMap()
	for (i <- 0 until Driver.NumPassengers) {
	  passengerStatus(i) = new CombinedReport()
	}
	
	def receive = {
	  case BodyReport(id, passed) =>
	    
	    if (passengerStatus(id).bagPass != null) {
	      if (passengerStatus(id).bagPass.passed && passed) {
	        println("Passenger " + id + " in line " + lineNum + " is sent to gate")
	        Driver.gate ! Ticket(id)
	      } else {
	        println("Passenger " + id + " in line " + lineNum + " is sent to jail")
	        Driver.jail ! Ticket(id)
	      }
	    } else {
	      passengerStatus(id).bodyPass = BodyReport(id, passed)
	    }
	    
	  case BagReport(id, passed) =>
	    if (passengerStatus(id).bodyPass != null) {
	      if (passengerStatus(id).bodyPass.passed && passed) {
	        println("Passenger " + id + " in line " + lineNum + " is sent to gate")
	        Driver.gate ! Ticket(id)
	      } else {
	        println("Passenger " + id + " in line " + lineNum + " is sent to jail")
	        Driver.jail ! Ticket(id)
	      }
	    } else {
	      passengerStatus(id).bagPass = BagReport(id, passed)
	    }
	}
	

  
}