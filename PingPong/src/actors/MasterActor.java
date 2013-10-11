package actors;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;

public class MasterActor extends UntypedActor {

	private ActorRef workerActor = getContext().actorOf(new Props(WorkerActor.class), "WorkerActor");
	
	@Override
	public void onReceive(Object msg) throws Exception {
		
		// Uncomment this to prevent ping-pong of messages !!!
		/*
		if (getSender().equals(workerActor)) {
			System.out.println("I wont allow ping pong of messages in my system!");
			return;
		}
		*/
		
		if (msg instanceof String) {
			System.out.println("To : MasterActor, Msg : " + msg);
			workerActor.tell("Hello Worker! (sent by MasterActor)", getSelf());
		} else {
			unhandled(msg);
		}
	}
}
