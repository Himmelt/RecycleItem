package org.soraworld.recycle;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class RecycleItem extends JavaPlugin implements Listener {

    private static final Pattern PRICE = Pattern.compile("(?<=回收价格: )\\d+");
    private static final Pattern ALL_COLOR_PATTERN = Pattern.compile("(?i)[&|\u00A7][0-9a-fk-or]");

    private VaultEconomy economy = null;

    public void onEnable() {
        try {
            economy = new VaultEconomy();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (economy != null && sender instanceof Player) {
            Player player = (Player) sender;
            PlayerInventory inv = player.getInventory();
            int size = inv.getSize();
            int cost = 0;
            for (int i = 9; i <= size; i++) {
                ItemStack stack = inv.getItem(i);
                if (stack != null && stack.hasItemMeta()) {
                    ItemMeta meta = stack.getItemMeta();
                    if (meta.hasLore()) {
                        for (String line : meta.getLore()) {
                            String text = ALL_COLOR_PATTERN.matcher(line).replaceAll("");
                            Matcher matcher = PRICE.matcher(text);
                            if (matcher.find()) {
                                int price = Integer.parseInt(matcher.group());
                                cost += price;
                                inv.clear(i);
                                economy.addEco(player, price);
                                break;
                            }
                        }
                    }
                }
            }
            player.sendMessage("[物品回收] 已为你回收物品，兑换了 " + cost + " 金币.");
            player.updateInventory();
        } else sender.sendMessage("[物品回收] 此命令只能由玩家在游戏内执行!");
        return true;
    }
}
