import statuscodes.StatusCodes;
import actors.MasterActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.PoisonPill;
import akka.actor.Props;

public class MainClass {

	/**
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {
		ActorSystem actorSystem = ActorSystem.create("MyActorSystem");
		ActorRef masterActorRef = actorSystem.actorOf(new Props(
				MasterActor.class), "MasterActor");
		masterActorRef.tell("I am initializing you Mr. MasterActor");
		Thread.sleep(2000);
		// Kill WorkerActor ...
		masterActorRef.tell(StatusCodes.STOP_WORKER);
		Thread.sleep(2000);
		// Stop master Actor and all its existing child actors ...
		masterActorRef.tell(PoisonPill.getInstance());
		System.out.println("Poison pill sent, will work async !!!");
		Thread.sleep(2000);
		actorSystem.shutdown();
	}
}
