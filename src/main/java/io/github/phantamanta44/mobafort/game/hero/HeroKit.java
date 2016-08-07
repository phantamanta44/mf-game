package io.github.phantamanta44.mobafort.game.hero;

import io.github.phantamanta44.mobafort.game.hero.spell.ITieredSpell;

public class HeroKit {

	private ITieredSpell[] spells; // TODO Autoattacks, hero passives
	private boolean melee;
	private double aaRange, aaSpeed;

	public HeroKit(ITieredSpell s1, ITieredSpell s2, ITieredSpell s3, ITieredSpell s4, boolean melee, double aaRange, double aaSpeed) {
		this.spells = new ITieredSpell[] {s1, s2, s3, s4};
		this.melee = melee;
		this.aaRange = aaRange;
		this.aaSpeed = aaSpeed;
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

}
