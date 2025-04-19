package com.AttackCounter;

import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;

import net.runelite.api.Client;
import net.runelite.api.Hitsplat;
import net.runelite.api.events.HitsplatApplied;

import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

@Slf4j
@PluginDescriptor(
		name = "Attack Counter"
)
public class PlayerAttackCounterPlugin extends Plugin {
	@Inject
	private Client client;
	@Inject
	private ConfigManager configManager;
	@Inject
	private CounterConfig config;
	@Inject
	private OverlayManager overlayManager;
	@Inject
	private PluginOverlay Overlay;

	@Inject
	private PluginOverlay overlay;

	private int successfulHits = 0;
	private int totaldamage = 0;
	private int totalattacks = 0;

	public int getSuccessfulHits() {
		return successfulHits;
	}

	public int getTotaldamage() {
		return totaldamage;
	}

	public int getTotalattacks() {
		return totalattacks;
	}

	public int getSuccessThreshold() {
		return config.successThreshold();
	}

	public int getDamageThreshold() {
		return config.damageThreshold();
	}

	public int getAttackThreshold() {
		return config.attackThreshold();
	}

	public boolean showThresholds() {
		return config.showThresholds();
	}

	public void reset() {
		successfulHits = 0;
		totaldamage = 0;
		totalattacks = 0;
	}

	@Override
	protected void startUp() throws Exception {
		log.info("Counter started!");
		overlayManager.add(overlay);
		reset();
	}

	@Override
	protected void shutDown() throws Exception {
		log.info("Counter stopped!");
		overlayManager.remove(overlay);
		reset();
	}

	@Provides
	CounterConfig provideConfig(ConfigManager configManager) {
		return configManager.getConfig(CounterConfig.class);
	}

	@Subscribe
	public void onHitsplatApplied(HitsplatApplied event) {
		Hitsplat hitsplat = event.getHitsplat();
		if (hitsplat.isMine()) {
			int damage = hitsplat.getAmount();
			totalattacks++;
			totaldamage += damage;
			if (damage > 0) {
				successfulHits++;
			}

			if (config.enableAutoReset()) {
				boolean hitsattempted = config.attackThreshold() > 0 && totalattacks >= config.attackThreshold();
				boolean hitsreached = config.successThreshold() > 0 && successfulHits >= config.successThreshold();
				boolean damagereached = config.damageThreshold() > 0 && totaldamage >= config.damageThreshold();

				if (hitsattempted || hitsreached || damagereached) {
					log.info("Auto-Reset: Hits = {} out of {} attempted, Damage = {}", successfulHits, totalattacks, totaldamage);
					reset();
				}
			}
		}
	}
}