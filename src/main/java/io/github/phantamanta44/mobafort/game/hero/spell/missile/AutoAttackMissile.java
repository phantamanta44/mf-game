package io.github.phantamanta44.mobafort.game.hero.spell.missile;

import io.github.phantamanta44.mobafort.game.event.MobaEventAutoAttack;
import io.github.phantamanta44.mobafort.game.hero.HeroKit;
import io.github.phantamanta44.mobafort.game.map.struct.Structure;
import io.github.phantamanta44.mobafort.mfrp.stat.StatTracker;
import io.github.phantamanta44.mobafort.weaponize.stat.Damage;
import io.github.phantamanta44.mobafort.weaponize.stat.Stats;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.List;
import java.util.function.Supplier;

public class AutoAttackMissile extends HomingMissile {

	private final LivingEntity tgt;
	private final HeroKit srcKit;
	private final Supplier<Vector> tgtPosGetter;

	public AutoAttackMissile(Player src, LivingEntity tgt, HeroKit srcKit) {
		super(src.getLocation(), srcKit.getAutoAttackSpeed(), 0D, src.getUniqueId(), CollisionCriteria.NONE);
		this.tgt = tgt;
		this.srcKit = srcKit;
		this.tgtPosGetter = () -> tgt.getLocation().toVector();
	}

	public AutoAttackMissile(Player src, Structure tgt, HeroKit srcKit) {
		super(src.getLocation(), srcKit.getAutoAttackSpeed(), 0D, src.getUniqueId(), CollisionCriteria.NONE);
		this.tgt = tgt.getDamageBuffer();
		this.srcKit = srcKit;
		this.tgtPosGetter = () -> tgt.getBounds().getBasePos();
	}

	@Override
	public void tick(long tick) {
		srcKit.autoAttackEffects(this, tick);
	}

	@Override
	protected Vector getTarget() {
		return tgtPosGetter.get();
	}

	@Override
	public void onHit(CollisionCriteria col, List<Entity> ents) {
		// NO-OP
	}

	@Override
	public void onReachTarget() {
		Player src = Bukkit.getServer().getPlayer(getSource());
		Damage dmg = new Damage(StatTracker.getStat(src, Stats.AD).getValue(), Damage.DamageType.PHYSICAL);
		MobaEventAutoAttack event = MobaEventAutoAttack.fire(src, tgt, dmg);
		if (!event.isCancelled())
			dmg.deal(src, tgt);
	}

}
