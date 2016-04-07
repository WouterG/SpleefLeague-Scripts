import com.spleefleague.core.player.Rank;
import com.spleefleague.core.player.SLPlayer;
import com.spleefleague.core.utils.DynamicCommand;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class CmdColorArmor implements DynamicCommand {

    private Material[] armors = new Material[] {
            Material.LEATHER_BOOTS, Material.LEATHER_CHESTPLATE, Material.LEATHER_HELMET, Material.LEATHER_LEGGINGS
    };

    @Override
    public String getName() {
        return "colorarmor";
    }

    @Override
    public Rank getRequiredRank() {
        return Rank.SENIOR_MODERATOR;
    }

    @Override
    public Usage getUsage() {
        return new Usage(
                "/colorarmor <red> <green> <blue> - color the item you're holding"
        );
    }

    @Override
    public void run(Player p, SLPlayer sp, String[] args) {
        if (args.length < 3) {
            sendUsage(p);
            return;
        }
        ItemStack is = p.getItemInHand();
        if (is == null || !isLeatherArmor(is)) {
            error(p, "You must be holding a leather armor piece");
            return;
        }
        int r = 0;
        int g = 0;
        int b = 0;
        try {
            r = Integer.parseInt(args[0]);
            g = Integer.parseInt(args[1]);
            b = Integer.parseInt(args[2]);
            r = Math.min(Math.max(0, r), 255);
            g = Math.min(Math.max(0, g), 255);
            b = Math.min(Math.max(0, b), 255);
        } catch (Exception e) {
            error(p, "Invalid color, should be number between 0-255");
            return;
        }
        LeatherArmorMeta meta = (LeatherArmorMeta) is.getItemMeta();
        meta.setColor(Color.fromRGB(r, g, b));
        is.setItemMeta(meta);
        success(p, "Color applied");
    }

    public boolean isLeatherArmor(ItemStack is) {
        for (int i = 0; i < armors.length; i++) {
            if (is.getType() == armors[i]) {
                return true;
            }
        }
        return false;
    }

}