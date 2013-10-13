package actors;

import akka.actor.ActorRef;
import akka.actor.Kill;
import akka.actor.UntypedActor;

public class WorkerActor extends UntypedActor {

	private ActorRef juniorWorkerActorRef;

	public WorkerActor(ActorRef juniorWorkerActorRef) {
		this.juniorWorkerActorRef = juniorWorkerActorRef;
	}

	@Override
	public void onReceive(Object msg) throws Exception {
		if (msg instanceof String) {
			juniorWorkerActorRef.forward(msg, getContext());
		}
	}

	@Override
	public void postStop() {
		System.out.println("Stopping WorkerActor");
		System.out.println("Lets see whether I can kill JrWorker!!!");
		// Kill Actor synchronously ...
		juniorWorkerActorRef.tell(Kill.getInstance());
	}
}
