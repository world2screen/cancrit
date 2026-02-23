package com.destruct0r;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.HitResult;

import java.util.Objects;


public class CanCritClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		HudElementRegistry.attachElementBefore(VanillaHudElements.CHAT, Identifier.of(CanCrit.MOD_ID, "before_chat"), CanCritClient::render);
	}

	private static void render(DrawContext context, RenderTickCounter tickCounter) {
		final MinecraftClient mc = MinecraftClient.getInstance();
		final PlayerEntity plyr = mc.player;
		assert plyr != null;

		int color = 0xFFFF0000; // Red
		int targetColor = 0xFF00FF00; // Green

        float h = plyr.getAttackCooldownProgress(0.5F);
		boolean bl = h > 0.9F;
		boolean bl3 = plyr.fallDistance > (double)0.0F &&
				!plyr.isOnGround() &&
				!plyr.isClimbing() &&
				!plyr.isTouchingWater() &&
				!plyr.hasStatusEffect(StatusEffects.BLINDNESS) &&
				!plyr.hasVehicle() &&
				!plyr.isSprinting();

		if (bl3) {
			if (bl) {
				if (Objects.requireNonNull(mc.crosshairTarget).getType() == HitResult.Type.ENTITY) {
					context.drawItem(Items.NETHERITE_SWORD.asItem().getDefaultStack(), (context.getScaledWindowWidth() - 15) / 2, (context.getScaledWindowHeight() + 18) / 2, 15);
				} else {
					context.drawItem(Items.DIAMOND_SWORD.asItem().getDefaultStack(), (context.getScaledWindowWidth() - 15) / 2, (context.getScaledWindowHeight() + 18) / 2, 15);
				}
			} else {
				if (Objects.requireNonNull(mc.crosshairTarget).getType() == HitResult.Type.ENTITY) {
					context.drawItem(Items.STRUCTURE_VOID.asItem().getDefaultStack(), (context.getScaledWindowWidth() - 15) / 2, (context.getScaledWindowHeight() + 18) / 2, 15);
				} else {
					context.drawItem(Items.BARRIER.asItem().getDefaultStack(), (context.getScaledWindowWidth() - 15) / 2, (context.getScaledWindowHeight() + 18) / 2, 15);
				}
			}
		}
	}
}
