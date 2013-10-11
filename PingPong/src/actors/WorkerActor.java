package actors;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;

public class WorkerActor extends UntypedActor {

	@Override
	public void preStart() {
		System.out.println("Worker Actor starting ...");
	}
	
	@Override
	public void postStop() {
		System.out.println("WorkerActor stopped!");
	}
	
	@Override
	public void onReceive(Object msg) throws Exception {
		if (msg instanceof String) {
			System.out.println("To : Worker Actor, Msg : " + msg);
			getSender().tell("I received your message Mr. MasterActor (sent by Worker Actor)", getSelf());
		} else {
			unhandled(msg);
		}
	}
}
