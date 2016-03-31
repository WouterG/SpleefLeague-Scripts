import com.spleefleague.core.player.Rank;
import com.spleefleague.core.player.SLPlayer;
import com.spleefleague.core.utils.DynamicCommand;
import com.spleefleague.superjump.SuperJump;
import com.spleefleague.superjump.game.Battle;
import com.spleefleague.superjump.player.SJPlayer;
import com.spleefleague.superspleef.SuperSpleef;
import com.spleefleague.superspleef.game.SpleefBattle;
import com.spleefleague.superspleef.player.SpleefPlayer;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collection;

public class CmdListGames implements DynamicCommand {

    @Override
    public String getName() {
        return "listgames";
    }

    @Override
    public Usage getUsage() {
        return new Usage("/listgames <ss/sj>");
    }

    @Override
    public Rank getRequiredRank() {
        return Rank.DEFAULT;
    }

    @Override
    public void run(Player p, SLPlayer sp, String[] args) {
        if (args.length < 1) {
            sendUsage(p);
            return;
        }
        if (args[0].equalsIgnoreCase("ss")) {
            Collection<SpleefBattle> battles = SuperSpleef.getInstance().getBattleManager().getAll();
            if (battles.isEmpty()) {
                error(p, "No Spleef games running");
                return;
            }
            p.sendMessage(ChatColor.DARK_GRAY + "[=== " + ChatColor.BLUE + "Running Spleef games: " + ChatColor.DARK_GRAY + " ===]");
            for (SpleefBattle b : battles) {
                ArrayList<String> names = new ArrayList();
                for (SpleefPlayer spl : b.getPlayers()) {
                    names.add(spl.getName());
                }
                p.sendMessage(ChatColor.RED + b.getArena().getName() + ChatColor.GRAY + " - " + ChatColor.YELLOW + StringUtils.join(names, ", ") + ". ");
            }
        } else if (args[0].equalsIgnoreCase("sj")) {
            Collection<Battle> battles = SuperJump.getInstance().getBattleManager().getAll();
            if (battles.isEmpty()) {
                error(p, "No Superjump games running");
                return;
            }
            p.sendMessage(ChatColor.DARK_GRAY + "[=== " + ChatColor.BLUE + "Running SJ games: " + ChatColor.DARK_GRAY + " ===]");
            for (Battle b : battles) {
                ArrayList<String> names = new ArrayList();
                for (SJPlayer spl : b.getPlayers()) {
                    names.add(spl.getName());
                }
                p.sendMessage(ChatColor.RED + b.getArena().getName() + ChatColor.GRAY + " - " + ChatColor.YELLOW + StringUtils.join(names, ", ") + ". ");
            }
        } else {
            sendUsage(p);
        }
    }

}
