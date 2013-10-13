package actors;

import akka.actor.PoisonPill;
import akka.actor.UntypedActor;

public class JuniorWorkerActor extends UntypedActor {

	@Override
	public void onReceive(Object msg) throws Exception {
		if (msg instanceof PoisonPill) {
			System.out.println("Someone fed me poison !!! yikes ...");
		} else if (msg instanceof String) { 
			System.out.println(msg);
			//Thread.sleep(5000);
			getSender().tell("I got your message MasterActor. What can I do for you?", getSelf());
		}
	}

	@Override
	public void postStop() {
		System.out.println("Stopping JuniorWorkerActor");
	}
}
