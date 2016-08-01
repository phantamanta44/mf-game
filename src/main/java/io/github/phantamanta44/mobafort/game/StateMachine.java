package io.github.phantamanta44.mobafort.game;

import io.github.phantamanta44.mobafort.game.game.GameEngine;
import io.github.phantamanta44.mobafort.weaponize.Weaponize;

import java.util.function.LongConsumer;

public class StateMachine implements LongConsumer {

	private GameEngine engine;
	private GameState state;

	public StateMachine(GameEngine engine) {
		this.engine = engine;
		this.state = GameState.QUEUEING;
		Weaponize.INSTANCE.registerTickHandler(this);
	}

	@Override
	public void accept(long tick) {
		switch (state) {
			case QUEUEING:
				break;
			case DRAFTING:
				break;
			case PLAYING:
				engine.tick(tick);
				break;
			case CONCLUSION:
				break;
		}
	}

	public void advance() {
		state = GameState.values()[(state.ordinal() + 1) % GameState.values().length];
		switch (state) {
			case DRAFTING:
				break;
			case PLAYING:
				engine.beginGame(Weaponize.INSTANCE.getTick());
				break;
			case CONCLUSION:
				engine.endGame();
				break;
			case QUEUEING:
				break;
		}
	}

}
