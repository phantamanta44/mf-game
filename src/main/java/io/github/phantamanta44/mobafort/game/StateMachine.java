package io.github.phantamanta44.mobafort.game;

import io.github.phantamanta44.mobafort.game.game.Announcer;
import io.github.phantamanta44.mobafort.game.game.ClassSelectSession;
import io.github.phantamanta44.mobafort.game.game.GameEngine;
import io.github.phantamanta44.mobafort.game.map.MapLoader;
import io.github.phantamanta44.mobafort.weaponize.Weaponize;

import java.util.function.LongConsumer;

public class StateMachine implements LongConsumer {

    private GameEngine engine;
    private GameState state;
    private boolean stateChange;
    private ClassSelectSession classSelect = null;

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
                    Announcer.system(String.format("Queueing for \u00a7b%s\u00a77... \u00a7f(%d/%d)",
                            engine.getMap().getProvider().getName(), engine.queueSize(), 10));
                if (engine.queueSize() >= engine.getMap().getProvider().getTeamSize() * 2)
                    setState(GameState.DRAFTING);
                break;
            case DRAFTING:
                classSelect.tick(tick);
                if (classSelect.isAllReady())
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
                engine.setMap(MapLoader.random().getMap());
                break;
            case DRAFTING:
                GamePlugin.getLobbies().teleportToDraft();
                engine.assignTeams();
                classSelect = new ClassSelectSession(Weaponize.INSTANCE.getTick());
                break;
            case PLAYING:
                classSelect.cleanUp();
                classSelect = null;
                engine.beginGame(Weaponize.INSTANCE.getTick());
                break;
            case CONCLUSION:
                engine.cleanUp();
                break;
        }
        stateChange = true;
    }

    public GameState getState() {
        return state;
    }

    public ClassSelectSession getClassSelect() {
        return classSelect;
    }

}
