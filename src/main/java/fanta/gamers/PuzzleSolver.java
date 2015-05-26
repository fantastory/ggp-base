package fanta.gamers;

import java.util.Collections;
import java.util.List;

import org.ggp.base.player.gamer.event.GamerSelectedMoveEvent;
import org.ggp.base.player.gamer.exception.GamePreviewException;
import org.ggp.base.player.gamer.statemachine.StateMachineGamer;
import org.ggp.base.util.game.Game;
import org.ggp.base.util.gdl.grammar.Gdl;
import org.ggp.base.util.gdl.grammar.GdlRule;
import org.ggp.base.util.gdl.grammar.GdlSentence;
import org.ggp.base.util.gdl.grammar.GdlTerm;
import org.ggp.base.util.statemachine.MachineState;
import org.ggp.base.util.statemachine.Move;
import org.ggp.base.util.statemachine.Role;
import org.ggp.base.util.statemachine.StateMachine;
import org.ggp.base.util.statemachine.exceptions.GoalDefinitionException;
import org.ggp.base.util.statemachine.exceptions.MoveDefinitionException;
import org.ggp.base.util.statemachine.exceptions.TransitionDefinitionException;
import org.ggp.base.util.statemachine.implementation.prover.ProverStateMachine;

/* solve single player games
 *
 * first, find a terminal state with highets score (just find maximumscore)
 *
 *
 * tests: http://gamemaster.stanford.edu/gamemanager.php?id=buttonsandlights
 */
public class PuzzleSolver extends StateMachineGamer {
	//http://127.0.0.1:9147

	int m_highestPossibleScore = Integer.MIN_VALUE;

	public PuzzleSolver() {

	}

	@Override
	public void stateMachineMetaGame(long timeout)
			throws TransitionDefinitionException, MoveDefinitionException,
			GoalDefinitionException
	{
		Game g = getMatch().getGame();
		List<Gdl> rules = g.getRules();
		String myRoleStr = getRoleName().toString();
		for (Gdl gdl : rules) {
			GdlRule rule = null;
			if (gdl.getClass() == GdlRule.class ) {
				rule = (GdlRule) gdl;
				//System.out.println(gdl);
				//System.out.println("head:" + rule.getHead());
				//System.out.println("name:" + rule.getHead().getName());
				GdlSentence head = rule.getHead();
				if (head.getName().getValue() == "goal") {
					//System.out.println("body:" + rule.getHead().getBody());
					List<GdlTerm> body = head.getBody();
					if (body.get(0).toString() == myRoleStr) {
						int score = Integer.parseInt(body.get(1).toString());
						if (m_highestPossibleScore < score)
							m_highestPossibleScore = score;
					}
				}
			}
		}
		System.out.println("m_highestPossibleScore:" + m_highestPossibleScore);
	}

	class MoveResult {
		public Move move;
		public int score;

		public MoveResult(Move move, int score) {
			assign(move, score);
		}

		public MoveResult assign(Move move, int score) {
			this.move = move;
			this.score = score;
			return this;
		}
	};

	Move bestMove(Role role, MachineState state) throws GoalDefinitionException, MoveDefinitionException, TransitionDefinitionException {
		MoveResult action = maxScore(role, state);
		return action.move;
	}

	MoveResult maxScore(Role role, MachineState state) throws GoalDefinitionException, MoveDefinitionException, TransitionDefinitionException {
		StateMachine stateMachine = getStateMachine();
		if (stateMachine.isTerminal(state)) {
			return new MoveResult(null, stateMachine.getGoal(state, role));
		}

		List<Move> actions = stateMachine.getLegalMoves(state, role);
		Move act = actions.get(0);
		MoveResult action = maxScore(role, stateMachine.getNextState(state, Collections.singletonList(act)));
		action.move = act;
		for (int i=1; i<actions.size(); i++) {
			act = actions.get(i);
			MoveResult result = maxScore(role, stateMachine.getNextState(state, Collections.singletonList(act)));
			if (result.score > action.score) {
				action.assign(act, result.score);
				if (action.score==m_highestPossibleScore) {
					break;
				}
			}
		}
		return action;
	}

	@Override
	public Move stateMachineSelectMove(long timeout)
			throws TransitionDefinitionException, MoveDefinitionException,
			GoalDefinitionException
	{
		//System.out.println("stateMachineSelectMove");
		long start = System.currentTimeMillis();
		List<Move> moves = getStateMachine().getLegalMoves(getCurrentState(),getRole());

		Move move = bestMove(getRole(), getCurrentState());

		long stop = System.currentTimeMillis();
		notifyObservers(new GamerSelectedMoveEvent(moves, move, stop - start));
		return move;
	}

	@Override
	public StateMachine getInitialStateMachine() {
		return new ProverStateMachine();
	}

	@Override
	public void stateMachineStop() {
	}

	@Override
	public void stateMachineAbort() {
	}

	@Override
	public void preview(Game g, long timeout) throws GamePreviewException {
		System.out.println("preview");
		List<Gdl> rules = g.getRules();

		for (Gdl gdl : rules) {
			System.out.println(gdl);
		}
	}

	@Override
	public String getName() {
		return getClass().getCanonicalName();
	}

}
