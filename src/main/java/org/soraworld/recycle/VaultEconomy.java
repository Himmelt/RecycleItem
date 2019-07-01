package org.soraworld.recycle;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.RegisteredServiceProvider;

public class VaultEconomy {

    private final Economy vaultEco;
    private final String providerName;

    public VaultEconomy() throws Exception {
        Economy economy = null;
        RegisteredServiceProvider<Economy> provider = Bukkit.getServicesManager().getRegistration(Economy.class);
        if (provider != null) economy = provider.getProvider();
        if (economy == null) {
            throw new Exception("[物品回收] 没有 Vault 经济实现!");
        }
        providerName = provider.getPlugin().getName();
        vaultEco = economy;
    }

    public boolean setEco(OfflinePlayer player, double amount) {
        if (takeEco(player, getEco(player))) {
            return addEco(player, amount);
        }
        return false;
    }

    public String getCoinName() {
        return vaultEco.getName();
    }

    public String getProviderName() {
        return providerName;
    }

    public boolean addEco(OfflinePlayer player, double amount) {
        return vaultEco.depositPlayer(player, amount).type == EconomyResponse.ResponseType.SUCCESS;
    }

    public double getEco(OfflinePlayer player) {
        return vaultEco.getBalance(player);
    }

    public boolean hasEnough(OfflinePlayer player, double amount) {
        return vaultEco.has(player, amount);
    }

    public boolean takeEco(OfflinePlayer player, double amount) {
        return vaultEco.withdrawPlayer(player, amount).type == EconomyResponse.ResponseType.SUCCESS;
    }
}
