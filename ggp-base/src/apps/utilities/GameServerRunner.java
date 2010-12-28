package apps.utilities;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import server.GameServer;
import util.game.Game;
import util.game.GameRepository;
import util.gdl.factory.exceptions.GdlFormatException;
import util.match.Match;
import util.statemachine.exceptions.GoalDefinitionException;
import util.symbol.factory.exceptions.SymbolFormatException;

/**
 * GameServerRunner is a utility program that lets you start up a match
 * directly from the command line.
 * 
 * @author Evan Cox
 */
public final class GameServerRunner
{
	public static void runMatch(Match match, List<String> hostnames, List<Integer> portNumbers, List<String> playerNames)
	{
		GameServer server = new GameServer(match, hostnames, portNumbers, playerNames);
		server.run();
		try {
			server.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * TODO: Evan, is this comment still accurate? -Sam
	 * 
	 * Main method for running 
	 * args[0] = name of the game (e.g. tic tac toe)
	 * args[1] = startclock
	 * args[2] = playclock
	 * args[3] = the number of players for the given game
	 * args[4] = file generated by play script
	 * @param args
	 * @throws GdlFormatException 
	 * @throws SymbolFormatException 
	 * @throws IOException 
	 * @throws InterruptedException 
	 * @throws GoalDefinitionException 
	 */
	public static void main(String[] args) throws IOException, SymbolFormatException, GdlFormatException, InterruptedException, GoalDefinitionException
	{
		String tourneyName = args[0];
		String gamename = args[1];
		Game game = GameRepository.getDefaultRepository().getGame(gamename);
		int startClock = Integer.valueOf(args[2]); 
		int playClock = Integer.valueOf(args[3]);
		if ((args.length - 4) % 3 != 0)
		{
			System.err.println("Incorrect player/port/host config");
			System.exit(1);
		}
		List<String> hostNames = new ArrayList<String>();
		List<String> playerNames = new ArrayList<String>();
		List<Integer> portNumbers = new ArrayList<Integer>();
		String matchname = tourneyName + gamename;
		for (int i = 4; i < args.length; i+= 3)
		{
			String hostname = args[i];
			Integer portnumber = Integer.valueOf(args[i + 1]);
			String playerName = args[i + 2];
			hostNames.add(hostname);
			portNumbers.add(portnumber);
			playerNames.add(playerName);
			matchname += playerName;
		}
		Match match = new Match(matchname, startClock, playClock, System.currentTimeMillis(), game);
		GameServer server = new GameServer(match, hostNames, portNumbers, playerNames);
		server.run();
		server.join();
		File f = new File(tourneyName);
		if (!f.exists())
		{
			f.mkdir();
			f = new File(tourneyName + "/scores");
			f.createNewFile();
		}
		f = new File(tourneyName + "/" + matchname);
		if (f.exists()) f.delete();
		BufferedWriter bw = new BufferedWriter(new FileWriter(f));
		bw.write(server.getGameXML());
		bw.flush();
		bw.close();
		bw = new BufferedWriter(new FileWriter(tourneyName + "/scores"));
		List<Integer> goals = server.getGoals();
		String goalstr = "";
		String playerstr = "";
		for (int i = 0; i < goals.size(); i++)
		{
			Integer goal = server.getGoals().get(i);
			goalstr += Integer.toString(goal);
			playerstr += playerNames.get(i);
			if (i != goals.size() - 1)
			{
				playerstr += ",";
				goalstr += ",";
			}
		}
		bw.write(playerstr + "=" + goalstr);
		bw.flush();
		bw.close();
	}
}