package actors;

import java.util.concurrent.TimeoutException;

import statuscodes.StatusCodes;

import akka.actor.ActorKilledException;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.actor.UntypedActorFactory;
import akka.dispatch.Await;
import akka.dispatch.Future;
import akka.pattern.Patterns;
import akka.util.Duration;
import akka.util.Timeout;

public class MasterActor extends UntypedActor {

	private ActorRef juniorWorkerActorRef = getContext().actorOf(
			new Props(JuniorWorkerActor.class), "JuniorWorkerActor");

	private ActorRef workerActorRef = getContext().actorOf(
			new Props(new UntypedActorFactory() {

				@Override
				public UntypedActor create() {
					return new WorkerActor(juniorWorkerActorRef);
				}
			}), "WorkerActor");

	@Override
	public void onReceive(Object msg) throws Exception {

		/*
		 * if (getSender().equals(workerActorRef)) { // No pingpong here ...
		 * return; }
		 */

		if (msg instanceof Enum) {
			if (msg == StatusCodes.STOP_WORKER) {
				getContext().stop(workerActorRef);
			} else {
				unhandled(msg);
			}
		} else if (msg instanceof String) {
			System.out.println(msg);
			Timeout timeout = new Timeout(Duration.create(2, "seconds"));
			Future<Object> future = Patterns.ask(workerActorRef,
					"Time to communicate with worker ...", timeout);

			try {
				// Blocks the MasterActor ...
				Object result = Await.result(future, timeout.duration());
				System.out.println("Result msg from worker : " + result);
			} catch (TimeoutException e) {
				System.out.println(e.getMessage());
			}
		} else {
			unhandled(msg);
		}
	}

	@Override
	public void postStop() {
		System.out.println("Stopping MasterActor");
	}
}
