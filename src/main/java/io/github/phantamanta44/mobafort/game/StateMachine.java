package io.github.phantamanta44.mobafort.game;

import io.github.phantamanta44.mobafort.game.game.Announcer;
import io.github.phantamanta44.mobafort.game.game.GameEngine;
import io.github.phantamanta44.mobafort.game.map.MapLoader;
import io.github.phantamanta44.mobafort.weaponize.Weaponize;

import java.util.function.LongConsumer;

public class StateMachine implements LongConsumer {

	private GameEngine engine;
	private GameState state;
	private boolean stateChange;

	public StateMachine(GameEngine engine) {
		this.engine = engine;
		this.state = GameState.QUEUEING;
		Weaponize.INSTANCE.registerTickHandler(this);
	}

	@Override
	public void accept(long tick) {
		switch (state) {
			case QUEUEING:
				if (!stateChange && tick % 600 == 0)
					Announcer.global(String.format("Now queueing... (%d/%d)", engine.queueSize(), 10));
				if (engine.queueSize() >= 10) // TODO Variable queue sizes
					setState(GameState.DRAFTING);
				break;
			case DRAFTING:
				// TODO Class/hero selection
				// TODO Some kind of draft pick mechanism
				setState(GameState.PLAYING);
				break;
			case PLAYING:
				engine.tick(tick);
				if (!engine.isInGame())
					setState(GameState.CONCLUSION);
				break;
			case CONCLUSION:
				// TODO Display stats somehow
				setState(GameState.QUEUEING);
				break;
		}
		stateChange = false;
	}

	private void setState(GameState newState) {
		state = newState;
		switch (state) {
			case QUEUEING:
				engine.setMap(MapLoader.random());
				break;
			case DRAFTING:
				engine.assignTeams();
				break;
			case PLAYING:
				engine.beginGame(Weaponize.INSTANCE.getTick());
				break;
			case CONCLUSION:
				engine.endGame();
				break;
		}
		stateChange = true;
	}

	public GameState getState() {
		return state;
	}

}
