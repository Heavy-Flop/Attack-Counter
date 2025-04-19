package com.AttackCounter;

import net.runelite.client.config.*;


@ConfigGroup("attackcounter")
public interface CounterConfig extends Config
{
	@ConfigItem(
			keyName = "enableAutoReset",
			name = "Enable Auto Reset",
			description = "Automatically reset stats after reaching thresholds"
	)
	default boolean enableAutoReset() { return false; }

	@ConfigItem(
			keyName = "successThreshold",
			name = "Success Threshold",
			description = "Reset after this many successful hits (0 to disable)"
	)
	@Range(min = 0)
	default int successThreshold() { return 0; }

	@ConfigItem(
			keyName = "damageThreshold",
			name = "Damage Threshold",
			description = "Reset after this much damage dealt (0 to disable)"
	)
	@Range(min = 0)
	default int damageThreshold() { return 0; }

	@ConfigItem(
			keyName = "attackThreshold",
			name = "Attack Threshold",
			description = "Reset after this many attempted hits (0 to disable)"
	)
	@Range(min = 0)
	default int attackThreshold() { return 0; }

	@ConfigItem(
			keyName = "showThresholds",
			name = "Show Thresholds in Overlay",
			description = "Display current thresholds on the overlay"
	)
	default boolean showThresholds() { return false; }
}
