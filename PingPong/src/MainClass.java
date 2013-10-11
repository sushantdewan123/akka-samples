import actors.MasterActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class MainClass {

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		ActorSystem actorSystem = ActorSystem.create("MyActorSystem");
		ActorRef masterActorRef = actorSystem.actorOf(new Props(MasterActor.class), "MasterActor");
		masterActorRef.tell("I am initializing you - Mr. MasterActor (sent by ActorSystem)");
		//Thread.sleep(5000);	
		actorSystem.shutdown();
	}
}
