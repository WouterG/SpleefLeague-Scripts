import com.spleefleague.core.player.Rank;
import com.spleefleague.core.player.SLPlayer;
import com.spleefleague.core.utils.DynamicCommand;
import com.spleefleague.superspleef.game.Arena;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class CmdTpArena implements DynamicCommand {

    @Override
    public String getName() {
        return "tparena";
    }

    @Override
    public Usage getUsage() {
        return new Usage(
            "/tparena <ss/sj> <name>"
        );
    }

    @Override
    public Rank getRequiredRank() {
        return Rank.MODERATOR;
    }

    @Override
    public void run(Player p, SLPlayer sp, String[] args) {
        if (args.length <= 1) {
            Collection<? extends Arena> ss_a = Arena.getAll();
            Collection<com.spleefleague.superjump.game.Arena> sj_a = com.spleefleague.superjump.game.Arena.getAll();
            Set<String> ss_names = new HashSet<>();
            Set<String> sj_names = new HashSet<>();
            ss_a.forEach((a) -> ss_names.add(a.getName()));
            sj_a.forEach((a) -> sj_names.add(a.getName()));
            p.sendMessage(ChatColor.RED + "SuperSpleef Arena's:");
            p.sendMessage(ChatColor.GOLD + StringUtils.join(ss_names, ChatColor.YELLOW + ", " + ChatColor.GOLD) + ChatColor.YELLOW + ".");
            p.sendMessage(ChatColor.YELLOW + "/tparena ss <name>");
            p.sendMessage(ChatColor.RED + "SuperJump Arena's:");
            p.sendMessage(ChatColor.GOLD + StringUtils.join(sj_names, ChatColor.YELLOW + ", " + ChatColor.GOLD) + ChatColor.YELLOW + ".");
            p.sendMessage(ChatColor.YELLOW + "/tparena sj <name>");
            return;
        }
        if (args[0].equalsIgnoreCase("ss")) {
            Arena a = Arena.byName(args[1]);
            if (a == null) {
                p.sendMessage(ChatColor.RED + "Arena " + ChatColor.GOLD + args[1] + ChatColor.RED + " not found!");
                return;
            }
            if (a.getSpectatorSpawn() == null) {
                p.sendMessage(ChatColor.RED + "No spectator spawn is set on " + ChatColor.YELLOW + a.getName());
                return;
            }
            p.sendMessage(ChatColor.GREEN + "Whoosh!");
            p.teleport(a.getSpectatorSpawn());
        } else if (args[0].equalsIgnoreCase("sj")) {
            com.spleefleague.superjump.game.Arena a = com.spleefleague.superjump.game.Arena.byName(args[1]);
            if (a == null) {
                p.sendMessage(ChatColor.RED + "Arena " + ChatColor.GOLD + args[1] + ChatColor.RED + " not found!");
                return;
            }
            if (a.getSpectatorSpawn() == null) {
                p.sendMessage(ChatColor.GOLD + "No spectator spawn is set on " + ChatColor.YELLOW + a.getName());
                p.sendMessage(ChatColor.GOLD + "Teleporting to spawn of player #1");
                p.teleport(a.getSpawns()[0]);
                return;
            }
            p.sendMessage(ChatColor.GREEN + "Whoosh!");
            p.teleport(a.getSpectatorSpawn());
        } else {
            p.sendMessage(ChatColor.RED + "Invalid game type!");
        }
    }
}
