package fanta.gamers;

import java.util.ArrayList;
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

public class Hunter extends StateMachineGamer {

	class Pos {
		int x, y;

		Pos(int ax, int ay) {
			x = ax;
			y = ay;
		}
	}

	List<String> m_moves = new ArrayList<String>();
	int m_currentMove = 0;;

	public Hunter() {
		ArrayList<Pos> p = new ArrayList<Pos>();
		p.add(new Pos(1,1)); //0
		p.add(new Pos(2,3)); //1
		p.add(new Pos(3,1)); //2
		p.add(new Pos(1,2)); //3
		p.add(new Pos(3,3)); //4
		p.add(new Pos(2,1)); //5
		p.add(new Pos(1,3)); //6
		p.add(new Pos(3,2)); //7
		p.add(new Pos(5,1)); //8
		p.add(new Pos(4,3)); //9
		p.add(new Pos(2,2)); //10
		p.add(new Pos(4,1)); //11
		p.add(new Pos(5,3)); //12
		p.add(new Pos(4,1)); //13
		p.add(new Pos(5,3)); //14
		p.add(new Pos(5,2)); //15
		p.add(new Pos(3,1)); //16
		p.add(new Pos(2,3)); //17
		p.add(new Pos(4,2)); //18

		for (int i = 1; i < p.size(); ++i) {
			Pos curr = p.get(i-1);
			Pos next = p.get(i);
			String strTerm = "( move "+curr.x+" "+curr.y+" "+next.x+" "+next.y+" )";

			m_moves.add(strTerm);
		}

		for (int i = 1; i < m_moves.size(); ++i) {
			System.out.println(m_moves.get(i));
		}

	}

	//http://127.0.0.1:9147

	int m_highestPossibleScore = 100;
	int m_lowestPossibleScore = 0;
	int m_myroleIndex = -1;
	int m_oponentRoleIndex = -1;
	long m_timelimit = 0;

	@Override
	public void stateMachineMetaGame(long timeout)
			throws TransitionDefinitionException, MoveDefinitionException,
			GoalDefinitionException
	{
		m_highestPossibleScore = 100;
		m_lowestPossibleScore = 0;
		m_myroleIndex = -1;
		m_currentMove = 0;

		try {
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
						List<GdlTerm> body = head.getBody();
						if (body.get(0).toString() == myRoleStr) {
							System.out.println("rule:" + rule);
							System.out.println("body:" + body);
							int score = Integer.parseInt(body.get(1).toString());
							if (m_highestPossibleScore < score)
								m_highestPossibleScore = score;
							if (m_lowestPossibleScore > score)
								m_lowestPossibleScore = score;
						}
					}
				}
			}
		} catch (Exception ex) {
			m_highestPossibleScore = 100;
			m_lowestPossibleScore = 0;
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
		//System.out.println("bestMove");

		int limit = 6;

		StateMachine stateMachine = getStateMachine();
		List<Move> actions = stateMachine.getLegalMoves(state, role);
		if (actions.size() == 1) {
			//System.out.println("not my turn");
			return actions.get(0);
		}

		//System.out.println("max check "+actions.size()+" moves");
		Move ret = actions.get(0);
		int alpha = m_lowestPossibleScore;
		int beta = m_highestPossibleScore;
		for (int i=actions.size(); --i != 0; ) {
			Move action = actions.get(i);
			int result = minScore(role, state, action, alpha, beta, limit);
			if (alpha < result) {
				alpha = result;
				ret = action;
				if (alpha >= beta) break;
			}
		}
		System.out.println("selecing a:"+alpha+" b:"+beta+" "+ret);
		System.out.println("selecing a:"+alpha/5+" b:"+beta/5+" "+ret);
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
	int maxScore(Role role, MachineState state, int alpha, int beta, int limit)
			throws GoalDefinitionException, MoveDefinitionException, TransitionDefinitionException
	{
		StateMachine stateMachine = getStateMachine();
		@SuppressWarnings("unused")
		boolean isTerminal = false;
		if (0 >= limit || System.currentTimeMillis() >= m_timelimit || (isTerminal = stateMachine.isTerminal(state))) {
			//System.out.println("end score:"+ stateMachine.getGoal(state, role)+ " term:"+ isTerminal + " " + state);
			return stateMachine.getGoal(state, role);
		}

		List<Move> actions = stateMachine.getLegalMoves(state, role);
		//System.out.println("max check "+actions.size()+" moves");

		for (int i = actions.size(); --i >= 0; ) {
			int result = minScore(role, state, actions.get(i), alpha, beta, limit);
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
	int minScore(Role role, MachineState state, Move myMove, int alpha, int beta, int limit) throws GoalDefinitionException, MoveDefinitionException, TransitionDefinitionException {
		StateMachine stateMachine = getStateMachine();

		List<List<Move>> actions = stateMachine.getLegalJointMoves(state, role, myMove);
		for (int i=actions.size(); --i >= 0; ) {
			int result = maxScore(role, stateMachine.getNextState(state, actions.get(i)), alpha, beta, limit-1);
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
		m_timelimit = start + timeout - 1000;


		List<Move> moves = getStateMachine().getLegalMoves(getCurrentState(),getRole());
		Move move = moves.get(0);
		String mstr = m_moves.get(m_currentMove++);
		for (int i = 0; i < moves.size(); i++) {
			move = moves.get(i);
			String term = move.toString();
			System.out.println("check "+mstr+" "+term);
			if (mstr.equals(term)) {
				System.out.println("found it "+term);
				break;
			}
		}

		//Move move = bestMove(getRole(), getCurrentState());

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
