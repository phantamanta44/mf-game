package io.github.phantamanta44.mobafort.game.hero;

import io.github.phantamanta44.mobafort.game.hero.spell.ITieredSpell;
import io.github.phantamanta44.mobafort.game.hero.spell.missile.AutoAttackMissile;
import io.github.phantamanta44.mobafort.game.hero.spell.missile.IMissileDecorator;

public class HeroKit {

	private ITieredSpell[] spells; // TODO hero passives
	private boolean melee;
	private double aaRange, aaSpeed;
	private IMissileDecorator<AutoAttackMissile> aaDecor;

	public HeroKit(ITieredSpell s1, ITieredSpell s2, ITieredSpell s3, ITieredSpell s4,
	               boolean melee, double aaRange, double aaSpeed, IMissileDecorator<AutoAttackMissile> aaDecor) {
		this.spells = new ITieredSpell[] {s1, s2, s3, s4};
		this.melee = melee;
		this.aaRange = aaRange;
		this.aaSpeed = aaSpeed;
		this.aaDecor = aaDecor;
	}

	public ITieredSpell[] getSpells() {
		return spells;
	}

	public boolean isMelee() {
		return melee;
	}

	public double getAutoAttackRange() {
		return aaRange;
	}

	public double getAutoAttackSpeed() {
		return aaSpeed;
	}

	public void autoAttackEffects(AutoAttackMissile missile, long tick) {
		aaDecor.apply(missile, tick);
	}

}
