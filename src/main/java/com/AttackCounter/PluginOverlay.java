package com.AttackCounter;

import net.runelite.api.Client;
import net.runelite.client.ui.overlay.*;
import net.runelite.client.ui.overlay.components.ComponentOrientation;
import net.runelite.client.ui.overlay.components.LineComponent;
import net.runelite.client.ui.overlay.components.PanelComponent;

import javax.inject.Inject;
import java.awt.*;

public class PluginOverlay extends Overlay
{
	private final Client client;
	private final PlayerAttackCounterPlugin plugin;
	private final PanelComponent panelComponent = new PanelComponent();

	@Inject
	public PluginOverlay(Client client, PlayerAttackCounterPlugin plugin)
	{
		this.client = client;
		this.plugin = plugin;

		setPosition(OverlayPosition.BOTTOM_RIGHT);
		setPriority(OverlayPriority.LOW);
		panelComponent.setOrientation(ComponentOrientation.VERTICAL);
	}

	@Override
	public Dimension render(Graphics2D graphics)
	{
		panelComponent.getChildren().clear();

		panelComponent.getChildren().add(LineComponent.builder()
				.left("Attempted Hits: ")
				.right(String.valueOf(plugin.getTotalattacks()))
				.build());
		panelComponent.getChildren().add(LineComponent.builder()
				.left("Successful Hits: ")
				.right(String.valueOf(plugin.getSuccessfulHits()))
				.build());
		panelComponent.getChildren().add(LineComponent.builder()
				.left("Total Damage: ")
				.right(String.valueOf(plugin.getTotaldamage()))
				.build());

		if (plugin.showThresholds()) {
			panelComponent.getChildren().add(LineComponent.builder()
					.left("Attempted Threshold: ")
					.right(String.valueOf(plugin.getAttackThreshold()))
					.build());

			panelComponent.getChildren().add(LineComponent.builder()
					.left("Successful Threshold: ")
					.right(String.valueOf(plugin.getSuccessThreshold()))
					.build());

			panelComponent.getChildren().add(LineComponent.builder()
					.left("Damage Threshold: ")
					.right(String.valueOf(plugin.getDamageThreshold()))
					.build());

		}
        return panelComponent.render(graphics);
    }
}