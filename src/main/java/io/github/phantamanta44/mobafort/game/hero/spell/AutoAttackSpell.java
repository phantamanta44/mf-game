package io.github.phantamanta44.mobafort.game.hero.spell;

import com.comphenix.protocol.wrappers.EnumWrappers;
import io.github.phantamanta44.mobafort.game.GamePlugin;
import io.github.phantamanta44.mobafort.game.event.MobaEventAutoAttack;
import io.github.phantamanta44.mobafort.game.hero.HeroKit;
import io.github.phantamanta44.mobafort.game.hero.spell.missile.AutoAttackMissile;
import io.github.phantamanta44.mobafort.game.map.struct.Structure;
import io.github.phantamanta44.mobafort.lib.effect.ParticleUtils;
import io.github.phantamanta44.mobafort.lib.item.ItemSig;
import io.github.phantamanta44.mobafort.lib.math.RayTrace;
import io.github.phantamanta44.mobafort.mfrp.stat.StatTracker;
import io.github.phantamanta44.mobafort.mfrp.status.IAutoAttackSpell;
import io.github.phantamanta44.mobafort.weaponize.stat.Damage;
import io.github.phantamanta44.mobafort.weaponize.stat.Stats;
import io.github.phantamanta44.mobafort.weaponize.weapon.IWeapon;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Collections;
import java.util.List;

public class AutoAttackSpell implements IAutoAttackSpell {

	public static final AutoAttackSpell INSTANCE = new AutoAttackSpell();
	public static final ItemSig ITEM_SIG = new ItemSig(Material.STONE);

	@Override
	public ItemSig getType() {
		return ITEM_SIG;
	}

	@Override
	public IWeaponInstance instantiate(Player player) {
		return new Instance(player);
	}

	public static class Instance implements IWeaponInstance {

		private final Player pl;
		private final HeroKit kit;
		private final CooldownEngine cd;

		public Instance(Player player) {
			this.pl = player;
			this.kit = GamePlugin.getEngine().getPlayer(pl).getHero().getKit();
			this.cd = new CooldownEngine(pl, false);
		}

		@Override
		public String getName() {
			return "Basic Attack";
		}

		@Override
		public List<String> getLore() {
			return Collections.emptyList();
		}

		@Override
		public int getStackSize() {
			return 1;
		}

		@Override
		public String getHudInfo() {
			return cd.offCooldown() ? null : cd.getBarRepresentation();
		}

		@Override
		public IWeapon getTemplate() {
			return INSTANCE;
		}

		@Override
		public void onInteract(PlayerInteractEvent event) {
			if (cd.offCooldown()) {
				RayTrace trace = new RayTrace(pl, kit.getAutoAttackRange(), (int)Math.ceil(kit.getAutoAttackRange()));
				Object target = null;
				for (Location loc : trace) {
					// TODO Target selection
				}
				if (target != null) { // TODO sfx
					if (kit.isMelee()) {
						LivingEntity leTgt = null;
						if (target instanceof LivingEntity)
							leTgt = (LivingEntity)target;
						else if (target instanceof Structure)
							leTgt = ((Structure)target).getDamageBuffer();
						else
							throw new IllegalStateException();
						Damage dmg = new Damage(StatTracker.getStat(pl, Stats.AD).getValue(), Damage.DamageType.PHYSICAL);
						MobaEventAutoAttack aaEvent = MobaEventAutoAttack.fire(pl, leTgt, dmg);
						if (!aaEvent.isCancelled()) {
							dmg.deal(pl, leTgt);
							Location loc = pl.getLocation();
							ParticleUtils.dispatchEffect(loc.add(loc.getDirection()), EnumWrappers.Particle.SWEEP_ATTACK, 2);
						}
					} else {
						if (target instanceof LivingEntity)
							new AutoAttackMissile(pl, (LivingEntity)target, kit).dispatch();
						else if (target instanceof Structure)
							new AutoAttackMissile(pl, (Structure)target, kit).dispatch();
					}
					cd.cooldown((long)(Math.pow(Math.min(StatTracker.getStat(pl, Stats.AS).getValue(), 2.5D), -1D) * 20D));
				}
			}
		}

		@Override
		public void tick(long tick) {
			// NO-OP
		}

		@Override
		public void kill() {
			// NO-OP
		}

	}

}
