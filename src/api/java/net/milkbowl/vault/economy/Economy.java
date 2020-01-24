package net.milkbowl.vault.economy;

import org.bukkit.OfflinePlayer;

/**
 * @author Himmelt
 */
public interface Economy {
    String getName();

    double getBalance(OfflinePlayer player);

    boolean has(OfflinePlayer player, double value);

    EconomyResponse withdrawPlayer(OfflinePlayer player, double value);

    EconomyResponse depositPlayer(OfflinePlayer player, double value);
}
