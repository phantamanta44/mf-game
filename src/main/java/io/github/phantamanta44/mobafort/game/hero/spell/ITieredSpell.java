package io.github.phantamanta44.mobafort.game.hero.spell;

import io.github.phantamanta44.mobafort.lib.format.StringUtils;
import io.github.phantamanta44.mobafort.weaponize.weapon.IWeapon;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public interface ITieredSpell extends IWeapon {

    TieredSpellInstance instantiate(Player player);

    abstract class TieredSpellInstance implements IWeaponInstance {

        private static final Pattern STR_TKN_PTN = Pattern.compile("\\{(.*?)}");

        protected int level = 0;

        public void setLevel(int l) {
            this.level = l;
        }

        public int getLevel() {
            return level;
        }

        @Override
        public void tick(long tick) {
            // NO-OP
        }

        @Override
        public void kill() {
            // NO-OP
        }

        protected List<String> format(String... parts) {
            return Arrays.stream(parts)
                    .map(this::format)
                    .collect(Collectors.toList());
        }

        protected String format(String str) {
            Matcher m = STR_TKN_PTN.matcher(str);
            StringBuffer sb = new StringBuffer();
            while (m.find())
                m.appendReplacement(sb, expandFmt(m.group(1)));
            m.appendTail(sb);
            return sb.toString();
        }

        protected String expandFmt(String fmt) {
            if (fmt.contains("|")) {
                int end = StringUtils.nthOccurence(fmt, '|', level + 1);
                return fmt.substring(StringUtils.nthOccurence(fmt, '|', level) + 1, end != -1 ? end : fmt.length());
            }
            else if (fmt.isEmpty())
                return Integer.toString(level + 1);
            return fmt;
        }

    }

}
