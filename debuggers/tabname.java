import com.spleefleague.core.SpleefLeague;
import com.spleefleague.core.utils.Debugger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

public class tabname implements Debugger, Runnable, Debugger.Stoppable {

    /*
     * Configuration
     */

    private String name = "Morgan_Ladimore";
    private ChatColor original = ChatColor.RED;
    private ChatColor highlight = ChatColor.YELLOW;

    /*
     * End of configuration
     */

    @Override
    public void debug(CommandSender cs, SpleefLeague plugin) {
        p = (CraftPlayer) Bukkit.getPlayer(name);
        task = Bukkit.getScheduler().runTaskTimer(plugin, this, 10L, 3L);
    }

    private CraftPlayer p;
    private BukkitTask task;
    private int animTimer = 0;

    @Override
    public void run() {
        try {
            animTimer++;
            if (animTimer > name.length() + 7) {
                animTimer = 0;
            }
            if (p == null || !p.isOnline()) {
                if (animTimer == 0) {
                    Player m = Bukkit.getPlayer(name);
                    if (m != null && m.isOnline()) {
                        p = (CraftPlayer) m;
                    }
                }
                if (p == null || !p.isOnline()) {
                    return;
                }
            }
            if (animTimer > name.length() - 1) {
                if (animTimer % 2 == 0) {
                    p.setPlayerListName(highlight + name);
                } else {
                    p.setPlayerListName(original + name);
                }
            } else {
                String p3 = name.substring(animTimer + (animTimer >= name.length() ? 0 : 1));
                String p2 = name.substring(animTimer, animTimer + 1);
                String p1 = name.substring(0, animTimer);
                p.setPlayerListName(original + p1 + highlight + p2 + original + p3);
            }
        } catch (Exception e) {
            Player admin = Bukkit.getPlayer("Wouto1997");
            if (admin != null) {
                admin.sendMessage("an error occured");
            }
            e.printStackTrace();
            stop();
        }
    }

    @Override
    public void stop() {
        if (task != null) {
            task.cancel();
        }
        if (p != null) {
            p.setPlayerListName(original + p.getName());
        }
    }
}
