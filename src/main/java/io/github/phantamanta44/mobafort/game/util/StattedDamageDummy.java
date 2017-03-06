package io.github.phantamanta44.mobafort.game.util;

import io.github.phantamanta44.mobafort.lib.collection.CollectionUtils;
import io.github.phantamanta44.mobafort.lib.collection.OneToManyMap;
import io.github.phantamanta44.mobafort.lib.entity.DummyLiving;
import io.github.phantamanta44.mobafort.lib.math.MathUtils;
import io.github.phantamanta44.mobafort.weaponize.stat.IStatted;
import io.github.phantamanta44.mobafort.mfrp.stat.ProvidedStat;
import io.github.phantamanta44.mobafort.weaponize.stat.Stats;
import org.apache.commons.lang.mutable.MutableDouble;
import org.apache.commons.lang.mutable.MutableFloat;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class StattedDamageDummy extends DummyLiving implements IStatted {

    private OneToManyMap<Stats.StatType, ProvidedStat<?>, List<ProvidedStat<?>>> providers;
    private Map<Stats.StatType, Number> stats;

    public StattedDamageDummy(String name, int hpMax, int hp, ProvidedStat<?>... stats) {
        super(name, hpMax, hp);
        addStats(Arrays.asList(stats));
    }

    public void clearStats(boolean clearProviders) {
        if (clearProviders)
            providers.clear();
        stats.clear();
    }

    public void addStat(ProvidedStat<?> stat) {
        providers.put(stat.stat.enumType, stat);
    }

    public void addStats(Collection<ProvidedStat<?>> stats) {
        stats.forEach(this::addStat);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends Number> T getStat(Stats<T> stat) {
        Number val = stats.get(stat.enumType);
        return (T)(val != null ? val : reduceFromProviders(stat));
    }

    private <T extends Number> Number reduceFromProviders(Stats<T> stat) {
        List<ProvidedStat<?>> provs = providers.get(stat.enumType);
        if (provs == null)
            return MathUtils.box(0, stat.enumType.type);
        OneToManyMap<ProvidedStat.ReduceType, ProvidedStat<?>, List<ProvidedStat<?>>> byType = CollectionUtils.groupByProperty(provs, s -> s.type);
        if (byType.contains(ProvidedStat.ReduceType.ADD)) {
            MutableDouble val = new MutableDouble();
            byType.get(ProvidedStat.ReduceType.ADD).forEach(p -> val.add(p.value));
            if (byType.contains(ProvidedStat.ReduceType.PERC)) {
                MutableFloat perc = new MutableFloat();
                byType.get(ProvidedStat.ReduceType.PERC).forEach(p -> perc.add(p.value));
                val.setValue(val.doubleValue() * (1 + perc.doubleValue()));
            }
            if (byType.contains(ProvidedStat.ReduceType.MULT))
                byType.get(ProvidedStat.ReduceType.MULT).forEach(p -> val.setValue(val.doubleValue() * p.value.doubleValue()));
            Number boxed = MathUtils.box(val.doubleValue(), stat.enumType.type);
            stats.put(stat.enumType, boxed);
            return boxed;
        }
        return MathUtils.box(0, stat.enumType.type);
    }

}
