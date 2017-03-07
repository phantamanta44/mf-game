package io.github.phantamanta44.mobafort.game.map.struct;

import io.github.phantamanta44.mobafort.game.game.Announcer;
import io.github.phantamanta44.mobafort.game.game.Team;
import io.github.phantamanta44.mobafort.game.util.StattedDamageDummy;
import io.github.phantamanta44.mobafort.lib.math.CylBounds;
import io.github.phantamanta44.mobafort.weaponize.Weaponize;
import org.bukkit.util.Vector;

public class StructInhib extends Structure {

    private final Team team;
    private final BlockBuild intact, broken;
    private StattedDamageDummy hp;
    private long destroyTime = -1L;

    public StructInhib(Vector pos, Team team, BlockBuild intact, BlockBuild broken) {
        super(new CylBounds(pos, 2.75F, 1.5F));
        this.team = team;
        this.intact = intact;
        this.broken = broken;
        this.hp = new StattedDamageDummy("Inhibitor", 4000, 4000) {
            @Override
            public void damage(double dmg) {
                super.damage(dmg * 0.85D);
                if (getHealth() < 1 && destroyTime == -1) {
                    Announcer.game(team.tag + " inhibitor has been destroyed!"); // TODO Destruction fx
                    destroyTime = Weaponize.INSTANCE.getTick();
                }
            }
        };
    }

    @Override
    public BlockBuild getBlocks() {
        return hp.getHealth() > 0 ? intact : broken;
    }

    @Override
    public void tick(long tick) {
        if (destroyTime != -1L) {
            if (tick - destroyTime == 6000L) {
                hp.setHealth(4000);
                Announcer.game(team.tag + " inhibitor has respawned!");
                destroyTime = -1L;
            } else if (tick - destroyTime == 5700) {
                Announcer.game(team.tag + " inhibitor respawning soon!");
            }
        }
    }

}
