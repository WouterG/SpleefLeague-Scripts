import com.spleefleague.core.player.Rank;
import com.spleefleague.core.player.SLPlayer;
import com.spleefleague.core.utils.DynamicCommand;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class CmdMemoryUsage implements DynamicCommand {

    @Override
    public String getName() {
        return "memoryusage";
    }

    @Override
    public Usage getUsage() {
        return new Usage(
                "/memoryusage - shows memory used by the server"
        );
    }

    @Override
    public Rank getRequiredRank() {
        return Rank.DEVELOPER;
    }

    @Override
    public void run(Player p, SLPlayer sp, String[] args) {
        Runtime r = Runtime.getRuntime();
        r.totalMemory();

        long total = r.totalMemory();
        long used = total - r.freeMemory();

        String usedNice = getFormattedMemory(used);
        String totalNice = getFormattedMemory(total);

        p.sendMessage(ChatColor.DARK_GREEN + "Usage: " + ChatColor.WHITE + used + ChatColor.GREEN + " / " + ChatColor.WHITE + total);
        p.sendMessage(ChatColor.DARK_GREEN + "Usage (nice): " + ChatColor.WHITE + usedNice + ChatColor.GREEN + " / " + ChatColor.WHITE  + totalNice);
    }

    public final String[] MEM_SUFFIX = new String[] {
            "b",
            "Kb",
            "Mb",
            "Gb",
            "Tb"
    };

    private static final int ROUND_BY = 3;

    public String getFormattedMemory(long bytes) {
        double v = (double) bytes;
        int index = 0;
        while (v > 1024) {
            v /= 1024;
            index++;
        }
        String vS = Double.toString(v);
        if (vS.contains(".")) {
            String l = vS.split("\\.")[0];
            String r = vS.split("\\.")[1];
            if (r.length() > ROUND_BY) {
                r = r.substring(0, ROUND_BY);
            }
            vS = l + "." + r;
        }
        return vS + " " +  MEM_SUFFIX[index];
    }
}
