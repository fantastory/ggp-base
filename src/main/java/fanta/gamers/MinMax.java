package fanta.gamers;

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

public class MinMax extends StateMachineGamer {

	public MinMax() {
	}

	//http://127.0.0.1:9147

	int m_highestPossibleScore = Integer.MIN_VALUE;
	int m_lowestPossibleScore = Integer.MAX_VALUE;
	int m_myroleIndex = -1;
	int m_oponentRoleIndex = -1;

	@Override
	public void stateMachineMetaGame(long timeout)
			throws TransitionDefinitionException, MoveDefinitionException,
			GoalDefinitionException
	{
		m_highestPossibleScore = Integer.MIN_VALUE;
		m_lowestPossibleScore = Integer.MAX_VALUE;
		m_myroleIndex = -1;

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
						if (m_lowestPossibleScore > score)
							m_lowestPossibleScore = score;
					}
				}
			}
		}
		System.out.println("m_highestPossibleScore:" + m_highestPossibleScore);
		System.out.println("m_lowestPossibleScore:" + m_lowestPossibleScore);

		m_myroleIndex = getStateMachine().getRoleIndices().get(getRole());
		if (m_myroleIndex == 0)
			m_oponentRoleIndex = 1;
		else
			m_oponentRoleIndex = 0;
		System.out.println("myindex:"+m_myroleIndex+" opponent:"+m_oponentRoleIndex);
	}

	class MoveResult {
		public List<Move> action;
		public int score;

		public MoveResult(List<Move> action, int score) {
			assign(action, score);
		}

		public MoveResult assign(List<Move> action, int score) {
			this.action = action;
			this.score = score;
			return this;
		}
	};

	Move bestMove(Role role, MachineState state) throws GoalDefinitionException, MoveDefinitionException, TransitionDefinitionException {
		System.out.println("bestMove");

		StateMachine stateMachine = getStateMachine();
		List<Move> moves = stateMachine.getLegalMoves(state, getRole());
		if (moves.size() == 1) {
			System.out.println("not my turn");
			return moves.get(0);
		}

		List<Move> actions = stateMachine.getLegalMoves(state, getRole());
		System.out.println("max check "+actions.size()+" moves");
		Move ret = actions.get(0);
		int alpha = m_lowestPossibleScore;
		int beta = m_highestPossibleScore;
		for (int i = 0; i < actions.size(); ++i ) {
			Move action = actions.get(i);
			int result = minScore(role, state, action, alpha, beta);
			if (alpha < result) {
				alpha = result;
				ret = action;
				if (alpha >= beta) break;
			}
		}
		System.out.println("selecing a:"+alpha+" b:"+beta+" "+ret);
		return ret;
	}


/*
function maxscore (role,state,alpha,beta)
 {if (findterminalp(state,game)) {return findreward(role,state,game)};
  var actions = findlegals(role,state,game);
  for (var i=0; i<actions.length; i++)
      {var result = minscore(role,actions[i],state,alpha,beta);
       alpha = max(alpha,result);
       if (alpha>=beta) then {return beta}};
  return alpha}
  */

	int maxScore(Role role, MachineState state, int alpha, int beta)
			throws GoalDefinitionException, MoveDefinitionException, TransitionDefinitionException
	{
		StateMachine stateMachine = getStateMachine();
		if (stateMachine.isTerminal(state)) {
			System.out.println("max end state "+ stateMachine.getGoal(state, role)+ " "+ state);
			return stateMachine.getGoal(state, role);
		}

		List<Move> actions = stateMachine.getLegalMoves(state, getRole());
		//System.out.println("max check "+actions.size()+" moves");
		for (int i = 0; i < actions.size(); ++i) {
			Move action = actions.get(i);
			int result = minScore(role, state, action, alpha, beta);
			if (alpha < result)
				alpha = result;
			if (alpha >= beta) return beta;
		}
		return alpha;
	}

	/*
function minscore (role,action,state,alpha,beta)
 {var opponent = findopponent(role,game);
  var actions = findlegals(opponent,state,game);
  for (var i=0; i<actions.length; i++) {
  		var move;
       if (role==roles[0]) {move = [action,actions[i]]}
       else {move = [actions[i],action]}
       var newstate = findnext(move,state,game);
       var result = maxscore(role,newstate,alpha,beta);
       beta = min(beta,result);
       if (beta<=alpha) then {return alpha}};
   return beta}
	 */
	int minScore(Role role, MachineState state, Move myMove, int alpha, int beta) throws GoalDefinitionException, MoveDefinitionException, TransitionDefinitionException {
		StateMachine stateMachine = getStateMachine();
		if (stateMachine.isTerminal(state)) {
			System.out.println("min end state "+ stateMachine.getGoal(state, role)+ " "+ state);
			return stateMachine.getGoal(state, role);
		}

		List<List<Move>> actions = stateMachine.getLegalJointMoves(state, role, myMove);
		for (int i=0; i<actions.size(); i++) {
			int result = maxScore(role, stateMachine.getNextState(state, actions.get(i)), alpha, beta);
			if (beta > result) {
				beta = result;
				if (beta <= alpha) return alpha;
			}
		}
		return beta;
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
