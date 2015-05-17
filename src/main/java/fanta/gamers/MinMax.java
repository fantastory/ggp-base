package fanta.gamers;

import java.util.List;

import org.ggp.base.player.gamer.event.GamerSelectedMoveEvent;
import org.ggp.base.player.gamer.exception.GamePreviewException;
import org.ggp.base.player.gamer.statemachine.StateMachineGamer;
import org.ggp.base.util.game.Game;
import org.ggp.base.util.statemachine.Move;
import org.ggp.base.util.statemachine.StateMachine;
import org.ggp.base.util.statemachine.exceptions.GoalDefinitionException;
import org.ggp.base.util.statemachine.exceptions.MoveDefinitionException;
import org.ggp.base.util.statemachine.exceptions.TransitionDefinitionException;
import org.ggp.base.util.statemachine.implementation.prover.ProverStateMachine;

public class MinMax extends StateMachineGamer {

	public MinMax() {
	}

	@Override
	public StateMachine getInitialStateMachine() {
		return new ProverStateMachine();
	}

	@Override
	public void stateMachineMetaGame(long timeout)
			throws TransitionDefinitionException, MoveDefinitionException,
			GoalDefinitionException {
	}

	@Override
	public Move stateMachineSelectMove(long timeout)
			throws TransitionDefinitionException, MoveDefinitionException,
			GoalDefinitionException
	{
		long start = System.currentTimeMillis();


		List<Move> moves = getStateMachine().getLegalMoves(getCurrentState(), getRole());
		Move selection = moves.get(0);



		long stop = System.currentTimeMillis();
		notifyObservers(new GamerSelectedMoveEvent(moves, selection, stop - start));
		return selection;
	}

	@Override
	public void stateMachineStop() {
	}

	@Override
	public void stateMachineAbort() {
	}

	@Override
	public void preview(Game g, long timeout) throws GamePreviewException {
	}

	@Override
	public String getName() {
		return getClass().getCanonicalName();
	}

}
