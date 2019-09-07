package de.Luca.Main;

import de.Luca.Connection.Connection;
import de.Luca.GameLogic.LoopHandler;

public class Main {

	public static void main(String[] args) {
		SkyFightClient.handleServerConnection = new Connection(Connection.HANDLE_SERVER_IP, Connection.HANDLE_SERVER_PORT);
		SkyFightEngine.init(new LoopHandler());
	}

}
