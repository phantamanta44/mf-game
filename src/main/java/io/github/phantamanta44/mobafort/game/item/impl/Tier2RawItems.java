package io.github.phantamanta44.mobafort.game.item.impl;

import io.github.phantamanta44.mobafort.game.item.BasicStatItem;
import io.github.phantamanta44.mobafort.game.util.FreeItems;
import io.github.phantamanta44.mobafort.lib.item.ItemSig;
import io.github.phantamanta44.mobafort.mfrp.stat.ProvidedStat;
import org.bukkit.Material;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static io.github.phantamanta44.mobafort.mfrp.stat.StatTracker.SRC_ITEM;
import static io.github.phantamanta44.mobafort.weaponize.stat.Stats.*;

public class Tier2RawItems {

    public static class Longsword extends BasicStatItem {

        public static final ItemSig SIG = new ItemSig(FreeItems.next());
        public static final String ID = "2longsword", NAME = "\u00a7rLongsword";
        private static final List<String> LORE = Arrays.asList(
                "\u00a7a+15 Attack Damage",
                "\u00a7a+16% Attack Speed"
        );
        private static final ProvidedStat[] STATS = new ProvidedStat[]{
                new ProvidedStat<>(BONUS_AD, 15, SRC_ITEM, ProvidedStat.ReduceType.ADD),
                new ProvidedStat<>(AS, 0.16F, SRC_ITEM, ProvidedStat.ReduceType.PERC)
        };

        public Longsword() {
            super(ID, SIG, NAME, LORE, STATS);
        }

    }

    public static class HeavyMetal extends BasicStatItem {

        public static final ItemSig SIG = new ItemSig(FreeItems.next());
        public static final String ID = "2heavymetal", NAME = "\u00a7rHeavy Metal";
        private static final List<String> LORE = Collections.singletonList("\u00a7a+40 Attack Damage");
        private static final ProvidedStat[] STATS = new ProvidedStat[]{
                new ProvidedStat<>(BONUS_AD, 40, SRC_ITEM, ProvidedStat.ReduceType.ADD)
        };

        public HeavyMetal() {
            super(ID, SIG, NAME, LORE, STATS);
        }

    }

    public static class SadisticSabre extends BasicStatItem {

        public static final ItemSig SIG = new ItemSig(FreeItems.next());
        public static final String ID = "2vampiresword", NAME = "\u00a7rSadistic Sabre";
        private static final List<String> LORE = Arrays.asList(
                "\u00a7a+12 Attack Damage",
                "\u00a7a+8% Life Steal"
        );
        private static final ProvidedStat[] STATS = new ProvidedStat[]{
                new ProvidedStat<>(BONUS_AD, 12, SRC_ITEM, ProvidedStat.ReduceType.ADD),
                new ProvidedStat<>(LIFE_STEAL, 0.08F, SRC_ITEM, ProvidedStat.ReduceType.ADD)
        };

        public SadisticSabre() {
            super(ID, SIG, NAME, LORE, STATS);
        }

    }

    public static class WhirlingSteel extends BasicStatItem {

        public static final ItemSig SIG = new ItemSig(FreeItems.next());
        public static final String ID = "2steelwind", NAME = "\u00a7rWhirling Steel";
        private static final List<String> LORE = Collections.singletonList("\u00a7a+25% Attack Speed");
        private static final ProvidedStat[] STATS = new ProvidedStat[]{
                new ProvidedStat<>(AS, 25, SRC_ITEM, ProvidedStat.ReduceType.PERC)
        };

        public WhirlingSteel() {
            super(ID, SIG, NAME, LORE, STATS);
        }

    }

    public static class WindBite extends BasicStatItem {

        public static final ItemSig SIG = new ItemSig(FreeItems.next());
        public static final String ID = "2windbite", NAME = "\u00a7rWind's Bite";
        private static final List<String> LORE = Arrays.asList(
                "\u00a7a+15% Attack Speed",
                "\u00a7a+20 Ability Power",
                "\u00a7a+8% Movement Speed"
        );
        private static final ProvidedStat[] STATS = new ProvidedStat[]{
                new ProvidedStat<>(AS, 0.15F, SRC_ITEM, ProvidedStat.ReduceType.PERC),
                new ProvidedStat<>(AP, 20, SRC_ITEM, ProvidedStat.ReduceType.ADD),
                new ProvidedStat<>(MOVE_SPEED, 0.08F, SRC_ITEM, ProvidedStat.ReduceType.PERC)
        };

        public WindBite() {
            super(ID, SIG, NAME, LORE, STATS);
        }

    }

    public static class BigClub extends BasicStatItem {

        public static final ItemSig SIG = new ItemSig(FreeItems.next());
        public static final String ID = "2bigclub", NAME = "\u00a7rReally Big Club";
        private static final List<String> LORE = Arrays.asList(
                "\u00a7a+25 Attack Damage",
                "\u00a7a+8% Critical Strike Damage"
        );
        private static final ProvidedStat[] STATS = new ProvidedStat[]{
                new ProvidedStat<>(BONUS_AD, 25, SRC_ITEM, ProvidedStat.ReduceType.ADD),
                new ProvidedStat<>(CRIT_DMG, 0.08F, SRC_ITEM, ProvidedStat.ReduceType.ADD)
        };

        public BigClub() {
            super(ID, SIG, NAME, LORE, STATS);
        }

    }

    public static class Longbow extends BasicStatItem {

        public static final ItemSig SIG = new ItemSig(FreeItems.next());
        public static final String ID = "3longbow", NAME = "\u00a7rLongbow";
        private static final List<String> LORE = Arrays.asList(
                "\u00a7a+30 Attack Damage",
                "\u00a7a+15% Critical Strike Chance",
                "\u00a7a+5% Critical Strike Damage"
        );
        private static final ProvidedStat[] STATS = new ProvidedStat[]{
                new ProvidedStat<>(BONUS_AD, 30, SRC_ITEM, ProvidedStat.ReduceType.ADD),
                new ProvidedStat<>(CRIT_CHANCE, 0.15F, SRC_ITEM, ProvidedStat.ReduceType.ADD),
                new ProvidedStat<>(CRIT_DMG, 0.05F, SRC_ITEM, ProvidedStat.ReduceType.ADD)
        };

        public Longbow() {
            super(ID, SIG, NAME, LORE, STATS);
        }

    }

    public static class CrystalMech extends BasicStatItem {

        public static final ItemSig SIG = new ItemSig(FreeItems.next());
        public static final String ID = "2crystalmech", NAME = "\u00a7rCrystalline Mechanism";
        private static final List<String> LORE = Arrays.asList(
                "\u00a7a+10% Cooldown Reduction",
                "\u00a7a+60% Mana Regeneration"
        );
        private static final ProvidedStat[] STATS = new ProvidedStat[]{
                new ProvidedStat<>(CDR, 0.1F, SRC_ITEM, ProvidedStat.ReduceType.ADD),
                new ProvidedStat<>(MANA_REGEN, 0.6F, SRC_ITEM, ProvidedStat.ReduceType.PERC)
        };

        public CrystalMech() {
            super(ID, SIG, NAME, LORE, STATS);
        }

    }

    public static class ManaMirror extends BasicStatItem {

        public static final ItemSig SIG = new ItemSig(FreeItems.next());
        public static final String ID = "2manamirror", NAME = "\u00a7rMana Mirror";
        private static final List<String> LORE = Arrays.asList(
                "\u00a7a+100% Mana Regeneration",
                "\u00a7a+8% Cooldown Reduction"
        );
        private static final ProvidedStat[] STATS = new ProvidedStat[]{
                new ProvidedStat<>(MANA_REGEN, 1F, SRC_ITEM, ProvidedStat.ReduceType.PERC),
                new ProvidedStat<>(CDR, 0.08F, SRC_ITEM, ProvidedStat.ReduceType.ADD)
        };

        public ManaMirror() {
            super(ID, SIG, NAME, LORE, STATS);
        }

    }

    public static class PrismSphere extends BasicStatItem {

        public static final ItemSig SIG = new ItemSig(FreeItems.next());
        public static final String ID = "2prismsphere", NAME = "\u00a7rPrismatic Sphere";
        private static final List<String> LORE = Arrays.asList(
                "\u00a7a+20 Ability Power",
                "\u00a7a+50% Mana Regeneration",
                "\u00a7a+10% Mana"
        );
        private static final ProvidedStat[] STATS = new ProvidedStat[]{
                new ProvidedStat<>(AP, 20, SRC_ITEM, ProvidedStat.ReduceType.ADD),
                new ProvidedStat<>(MANA_REGEN, 0.5F, SRC_ITEM, ProvidedStat.ReduceType.PERC),
                new ProvidedStat<>(MANA_MAX, 0.1F, SRC_ITEM, ProvidedStat.ReduceType.PERC)
        };

        public PrismSphere() {
            super(ID, SIG, NAME, LORE, STATS);
        }

    }

    public static class DenseCluster extends BasicStatItem {

        public static final ItemSig SIG = new ItemSig(FreeItems.next());
        public static final String ID = "2cluster", NAME = "\u00a7rDense Cluster";
        private static final List<String> LORE = Arrays.asList(
                "\u00a7a+20 Ability Power",
                "\u00a7a+8% Cooldown Reduction"
        );
        private static final ProvidedStat[] STATS = new ProvidedStat[]{
                new ProvidedStat<>(AP, 20, SRC_ITEM, ProvidedStat.ReduceType.ADD),
                new ProvidedStat<>(CDR, 0.08F, SRC_ITEM, ProvidedStat.ReduceType.ADD)
        };

        public DenseCluster() {
            super(ID, SIG, NAME, LORE, STATS);
        }

    }

    public static class PiercingGaze extends BasicStatItem {

        public static final ItemSig SIG = new ItemSig(FreeItems.next());
        public static final String ID = "2piercinggaze", NAME = "\u00a7rPiercing Gaze";
        private static final List<String> LORE = Arrays.asList(
                "\u00a7a+15 Ability Power",
                "\u00a7a+200 Health",
                "\u00a7a+50% Mana Regeneration",
                "\u00a7a+7 Magic Penetration"
        );
        private static final ProvidedStat[] STATS = new ProvidedStat[]{
                new ProvidedStat<>(AP, 15, SRC_ITEM, ProvidedStat.ReduceType.ADD),
                new ProvidedStat<>(HP_MAX, 200, SRC_ITEM, ProvidedStat.ReduceType.ADD),
                new ProvidedStat<>(MANA_REGEN, 0.5F, SRC_ITEM, ProvidedStat.ReduceType.PERC),
                new ProvidedStat<>(MAG_PEN, 7, SRC_ITEM, ProvidedStat.ReduceType.ADD)
        };

        public PiercingGaze() {
            super(ID, SIG, NAME, LORE, STATS);
        }

    }

    public static class LostScripture extends BasicStatItem {

        public static final ItemSig SIG = new ItemSig(FreeItems.next());
        public static final String ID = "2lostchapter", NAME = "\u00a7rLost Scripture";
        private static final List<String> LORE = Arrays.asList(
                "\u00a7a+35 Ability Power",
                "\u00a7a+10% Cooldown Reduction",
                "\u00a7a+25% Mana Regeneration"
        );
        private static final ProvidedStat[] STATS = new ProvidedStat[]{
                new ProvidedStat<>(AP, 35, SRC_ITEM, ProvidedStat.ReduceType.ADD),
                new ProvidedStat<>(CDR, 0.1F, SRC_ITEM, ProvidedStat.ReduceType.ADD),
                new ProvidedStat<>(MANA_REGEN, 0.25F, SRC_ITEM, ProvidedStat.ReduceType.PERC)
        };

        public LostScripture() {
            super(ID, SIG, NAME, LORE, STATS);
        }

    }

    public static class MassiveGauntlet extends BasicStatItem {

        public static final ItemSig SIG = new ItemSig(FreeItems.next());
        public static final String ID = "2massivegauntlet", NAME = "\u00a7rMassive Gauntlet";
        private static final List<String> LORE = Arrays.asList(
                "\u00a7a+30 Armour",
                "\u00a7a+10% Armour"
        );
        private static final ProvidedStat[] STATS = new ProvidedStat[]{
                new ProvidedStat<>(ARM, 30, SRC_ITEM, ProvidedStat.ReduceType.ADD),
                new ProvidedStat<>(ARM, 0.1F, SRC_ITEM, ProvidedStat.ReduceType.PERC)
        };

        public MassiveGauntlet() {
            super(ID, SIG, NAME, LORE, STATS);
        }

    }

    public static class EnergyVoid extends BasicStatItem {

        public static final ItemSig SIG = new ItemSig(FreeItems.next());
        public static final String ID = "2energyvoid", NAME = "\u00a7rEnergetic Void";
        private static final List<String> LORE = Arrays.asList(
                "\u00a7a+300 Mana",
                "\u00a7a+15 Magic Resist"
        );
        private static final ProvidedStat[] STATS = new ProvidedStat[]{
                new ProvidedStat<>(MANA_MAX, 300, SRC_ITEM, ProvidedStat.ReduceType.ADD),
                new ProvidedStat<>(MR, 15, SRC_ITEM, ProvidedStat.ReduceType.ADD)
        };

        public EnergyVoid() {
            super(ID, SIG, NAME, LORE, STATS);
        }

    }

    public static class IronBrace extends BasicStatItem {

        public static final ItemSig SIG = new ItemSig(FreeItems.next());
        public static final String ID = "2ironbrace", NAME = "\u00a7rIron Brace";
        private static final List<String> LORE = Arrays.asList(
                "\u00a7a+18 Armour",
                "\u00a7a+200 Health"
        );
        private static final ProvidedStat[] STATS = new ProvidedStat[]{
                new ProvidedStat<>(ARM, 18, SRC_ITEM, ProvidedStat.ReduceType.ADD),
                new ProvidedStat<>(HP_MAX, 200, SRC_ITEM, ProvidedStat.ReduceType.ADD)
        };

        public IronBrace() {
            super(ID, SIG, NAME, LORE, STATS);
        }

    }

    public static class BloodBuckler extends BasicStatItem {

        public static final ItemSig SIG = new ItemSig(FreeItems.next());
        public static final String ID = "2bloodbuckler", NAME = "\u00a7rBloody Buckler";
        private static final List<String> LORE = Arrays.asList(
                "\u00a7a+350 Health",
                "\u00a7a+50% Health Regeneration",
                "\u00a7a+3% Life Steal"
        );
        private static final ProvidedStat[] STATS = new ProvidedStat[]{
                new ProvidedStat<>(HP_MAX, 350, SRC_ITEM, ProvidedStat.ReduceType.ADD),
                new ProvidedStat<>(HP_REGEN, 0.5F, SRC_ITEM, ProvidedStat.ReduceType.PERC),
                new ProvidedStat<>(LIFE_STEAL, 0.03F, SRC_ITEM, ProvidedStat.ReduceType.ADD)
        };

        public BloodBuckler() {
            super(ID, SIG, NAME, LORE, STATS);
        }

    }

    public static class LifeFlask extends BasicStatItem {

        public static final ItemSig SIG = new ItemSig(FreeItems.next());
        public static final String ID = "2lifeflask", NAME = "\u00a7rVictuum Flask";
        private static final List<String> LORE = Collections.singletonList("\u00a7a+500 Health");
        private static final ProvidedStat[] STATS = new ProvidedStat[]{
                new ProvidedStat<>(HP_MAX, 500, SRC_ITEM, ProvidedStat.ReduceType.ADD)
        };

        public LifeFlask() {
            super(ID, SIG, NAME, LORE, STATS);
        }

    }

    public static class Pathos extends BasicStatItem {

        public static final ItemSig SIG = new ItemSig(FreeItems.next());
        public static final String ID = "2pathos", NAME = "\u00a7rPathos";
        private static final List<String> LORE = Arrays.asList(
                "\u00a7a+20 Armour",
                "\u00a7a+15 Magic Resist"
        );
        private static final ProvidedStat[] STATS = new ProvidedStat[]{
                new ProvidedStat<>(ARM, 20, SRC_ITEM, ProvidedStat.ReduceType.ADD),
                new ProvidedStat<>(MR, 15, SRC_ITEM, ProvidedStat.ReduceType.ADD)
        };

        public Pathos() {
            super(ID, SIG, NAME, LORE, STATS);
        }

    }

    public static class CritCape extends BasicStatItem {

        public static final ItemSig SIG = new ItemSig(FreeItems.next());
        public static final String ID = "2critcape", NAME = "\u00a7rMarauder's Cape";
        private static final List<String> LORE = Arrays.asList(
                "\u00a7a+18 Armour",
                "\u00a7a+12% Critical Strike Chance",
                "\u00a7a+8% Movement Speed"
        );
        private static final ProvidedStat[] STATS = new ProvidedStat[]{
                new ProvidedStat<>(ARM, 18, SRC_ITEM, ProvidedStat.ReduceType.ADD),
                new ProvidedStat<>(CRIT_CHANCE, 0.12F, SRC_ITEM, ProvidedStat.ReduceType.ADD),
                new ProvidedStat<>(MOVE_SPEED, 0.08F, SRC_ITEM, ProvidedStat.ReduceType.PERC)
        };

        public CritCape() {
            super(ID, SIG, NAME, LORE, STATS);
        }

    }

    public static class TalismanChain extends BasicStatItem {

        public static final ItemSig SIG = new ItemSig(FreeItems.next());
        public static final String ID = "2talismanchain", NAME = "\u00a7rTalisman Chain";
        private static final List<String> LORE = Arrays.asList(
                "\u00a7a+36 Ability Power",
                "\u00a7a+12 Magic Resist"
        );
        private static final ProvidedStat[] STATS = new ProvidedStat[]{
                new ProvidedStat<>(AP, 36, SRC_ITEM, ProvidedStat.ReduceType.ADD),
                new ProvidedStat<>(MR, 12, SRC_ITEM, ProvidedStat.ReduceType.ADD)
        };

        public TalismanChain() {
            super(ID, SIG, NAME, LORE, STATS);
        }

    }

}
