package io.github.phantamanta44.mobafort.game.hero.spell;

public class HeroKit {

	private ITieredSpell[] spells; // TODO Autoattacks, hero passives

	public HeroKit(ITieredSpell s1, ITieredSpell s2, ITieredSpell s3, ITieredSpell s4) {
		this.spells = new ITieredSpell[] {s1, s2, s3, s4};
	}

	public ITieredSpell[] getSpells() {
		return spells;
	}

}
