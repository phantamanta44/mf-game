package io.github.phantamanta44.mobafort.game.hero.impl;

import com.comphenix.protocol.wrappers.EnumWrappers;
import io.github.phantamanta44.mobafort.game.hero.HeroClass;
import io.github.phantamanta44.mobafort.game.hero.HeroKit;
import io.github.phantamanta44.mobafort.game.hero.IBaseStats;
import io.github.phantamanta44.mobafort.game.hero.IHero;
import io.github.phantamanta44.mobafort.game.hero.spell.ITieredSpell;
import io.github.phantamanta44.mobafort.game.hero.spell.missile.AutoAttackMissile;
import io.github.phantamanta44.mobafort.game.hero.spell.missile.IMissileDecorator;
import io.github.phantamanta44.mobafort.lib.effect.ParticleUtils;
import io.github.phantamanta44.mobafort.lib.item.ItemSig;
import io.github.phantamanta44.mobafort.mfrp.stat.ProvidedStat;
import io.github.phantamanta44.mobafort.weaponize.stat.Stats;
import io.github.phantamanta44.mobafort.weaponize.weapon.IWeapon;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Arrays;
import java.util.List;

import static io.github.phantamanta44.mobafort.mfrp.stat.ProvidedStat.ReduceType.ADD;
import static io.github.phantamanta44.mobafort.mfrp.stat.ProvidedStat.ReduceType.PERC;
import static io.github.phantamanta44.mobafort.mfrp.stat.StatTracker.SRC_BASE;

public class HeroEzreal implements IHero {

	private static final ItemSig ICON = new ItemSig(Material.STONE);
	private static final IMissileDecorator<AutoAttackMissile> AA_DECOR = (p, t) ->
			ParticleUtils.dispatchEffect(p.getLocation(), EnumWrappers.Particle.ENCHANTMENT_TABLE, 3, 0.05F);

	@Override
	public String getName() {
		return "Ezreal";
	}

	@Override
	public ItemSig getIcon() {
		return ICON;
	}

	@Override
	public HeroClass getHeroClass() {
		return HeroClass.MARKSMAN;
	}

	@Override
	public IBaseStats getStats() {
		return l -> Arrays.asList(
				new ProvidedStat<>(Stats.HP_MAX, 564 + 80 * l, SRC_BASE, ADD),
				new ProvidedStat<>(Stats.HP_REGEN, 6.97 + 0.55 * l, SRC_BASE, ADD),
				new ProvidedStat<>(Stats.MANA_MAX, 403 + 42 * l, SRC_BASE, ADD),
				new ProvidedStat<>(Stats.MANA_REGEN, 8.74 + 0.65 * l, SRC_BASE, ADD),
				new ProvidedStat<>(Stats.MOVE_SPEED, 325, SRC_BASE, ADD),
				new ProvidedStat<>(Stats.AD, 58 + 3 * l, SRC_BASE, ADD),
				new ProvidedStat<>(Stats.AS, 0.625, SRC_BASE, ADD),
				new ProvidedStat<>(Stats.AS, 0.028F, SRC_BASE, PERC),
				new ProvidedStat<>(Stats.ARM, 25 + 4 * l, SRC_BASE, ADD),
				new ProvidedStat<>(Stats.MR, 30, SRC_BASE, ADD)
		);
	}

	@Override
	public HeroKit getKit() {
		return new HeroKit(S1.INSTANCE, S2.INSTANCE, S3.INSTANCE, S4.INSTANCE, false, 4.4D, 0.55D, AA_DECOR);
	}

	private static class S1 implements ITieredSpell {

		private static final S1 INSTANCE = new S1();

		@Override
		public ItemSig getType() {
			return new ItemSig(Material.STONE);
		}

		@Override
		public TieredSpellInstance instantiate(Player player) {
			return new Instance();
		}

		private static class Instance extends TieredSpellInstance {

			@Override
			public String getName() {
				return "Mystic Shot";
			}

			@Override
			public List<String> getLore() {
				return format(
						"\u00a7f{28|31|34|37|40} Mana / {6.5|6|5.5|5|4.5}s Cooldown",
						"\u00a76\u00a7lActive:",
						"\u00a79Fires a bolt of energy in a line, dealing {35|55|75|95|115} \u00a76(+110% AD) \u00a7a(+40% AP)",
						"\u00a79physical damage to the first enemy hit. If Mystic Shot",
						"\u00a79successfully hits an enemy, all of Ezreal's ability",
						"\u00a79cooldowns are refreshed by 1.5 seconds."
				);
			}

			@Override
			public int getStackSize() {
				return 1;
			}

			@Override
			public String getHudInfo() {
				return ""; // TODO Implement
			}

			@Override
			public IWeapon getTemplate() {
				return INSTANCE;
			}

			@Override
			public void onInteract(PlayerInteractEvent event) {
				// TODO Implement
			}

		}

	}

	private static class S2 implements ITieredSpell {

		private static final S2 INSTANCE = new S2();

		@Override
		public ItemSig getType() {
			return new ItemSig(Material.STONE);
		}

		@Override
		public TieredSpellInstance instantiate(Player player) {
			return new Instance();
		}

		private static class Instance extends TieredSpellInstance {

			@Override
			public String getName() {
				return "Essense Flux";
			}

			@Override
			public List<String> getLore() {
				return format(
						"\u00a7f{50|60|70|80|90} Mana / 9s Cooldown",
						"\u00a76\u00a7lActive:",
						"\u00a79Fires a wave of energy in a line, dealing {70|115|160|205|250} \u00a7a(+80% AP)",
						"\u00a79magic damage to all enemy heroes struck, and granting",
						"\u00a79all allied heroes {20|25|30|35|40}% bonus attack speed",
						"\u00a79for 5 seconds."
				);
			}

			@Override
			public int getStackSize() {
				return 1;
			}

			@Override
			public String getHudInfo() {
				return ""; // TODO Implement
			}

			@Override
			public IWeapon getTemplate() {
				return INSTANCE;
			}

			@Override
			public void onInteract(PlayerInteractEvent event) {
				// TODO Implement
			}

		}

	}

	private static class S3 implements ITieredSpell {

		private static final S3 INSTANCE = new S3();

		@Override
		public ItemSig getType() {
			return new ItemSig(Material.STONE);
		}

		@Override
		public TieredSpellInstance instantiate(Player player) {
			return new Instance();
		}

		private static class Instance extends TieredSpellInstance {

			@Override
			public String getName() {
				return "Arcane Shift";
			}

			@Override
			public List<String> getLore() {
				return format(
						"\u00a7f90 Mana / {19|17.5|16|14.5|13}s Cooldown",
						"\u00a76\u00a7lActive:",
						"\u00a79Blinks to the target location, firing a homing bolt",
						"\u00a79that deals {75|125|175|225|275} \u00a76(+50% Bonus AD) \u00a7a(+75% AP) \u00a79magic",
						"\u00a79damage to the nearest enemy unit."
				);
			}

			@Override
			public int getStackSize() {
				return 1;
			}

			@Override
			public String getHudInfo() {
				return ""; // TODO Implement
			}

			@Override
			public IWeapon getTemplate() {
				return INSTANCE;
			}

			@Override
			public void onInteract(PlayerInteractEvent event) {
				// TODO Implement
			}

		}

	}

	private static class S4 implements ITieredSpell {

		private static final S4 INSTANCE = new S4();

		@Override
		public ItemSig getType() {
			return new ItemSig(Material.STONE);
		}

		@Override
		public TieredSpellInstance instantiate(Player player) {
			return new Instance();
		}

		private static class Instance extends TieredSpellInstance {

			@Override
			public String getName() {
				return "Trueshot Barrage";
			}

			@Override
			public List<String> getLore() {
				return format(
						"\u00a7f100 Mana / 120s Cooldown",
						"\u00a76\u00a7lActive:",
						"\u00a79After gathering energy for 1 second, Ezreal fires an",
						"\u00a79energy projectile in the target direction, dealing",
						"\u00a79{350/500/650} \u00a76(+100% Bonus AD) \u00a7a(+90% AP) \u00a79magic damage to enemies",
						"\u00a79it passes through. Each enemy hit reduces the projectile's",
						"\u00a79damage by 10%, down to a minimum 30% damage."
				);
			}

			@Override
			public int getStackSize() {
				return 1;
			}

			@Override
			public String getHudInfo() {
				return ""; // TODO Implement
			}

			@Override
			public IWeapon getTemplate() {
				return INSTANCE;
			}

			@Override
			public void onInteract(PlayerInteractEvent event) {
				// TODO Implement
			}

		}

	}

}
