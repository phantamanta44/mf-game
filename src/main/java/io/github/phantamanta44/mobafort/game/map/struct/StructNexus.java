package io.github.phantamanta44.mobafort.game.map.struct;

import io.github.phantamanta44.mobafort.game.game.Team;
import io.github.phantamanta44.mobafort.game.util.StattedDamageDummy;
import io.github.phantamanta44.mobafort.lib.math.CylBounds;
import org.bukkit.util.Vector;

public class StructNexus extends Structure {

    private final Team team;
    private final BlockBuild build;
    private StattedDamageDummy hp;
    private long destroyTime = -1L;

    public StructNexus(Vector pos, Team team, BlockBuild build) {
        super(new CylBounds(pos, 2.75F, 1.5F));
        this.team = team;
        this.build = build;
        this.hp = new StattedDamageDummy("Nexus", 5500, 5500) {
            @Override
            public void damage(double dmg) {
                super.damage(dmg);
                if (getHealth() < 1 && destroyTime == -1) {
                    // TODO Destruction fx
                    // TODO Set victory state
                }
            }
        };
    }

    @Override
    public BlockBuild getBlocks() {
        return build;
    }

    @Override
    public void tick(long tick) {
        if (tick % 20L == 0 && hp.getHealth() > 0)
            hp.setHealth(Math.min(hp.getHealth() + 5, hp.getMaxHealth()));
    }

}
