package com.alfakynz.ltxec.mixin;

import com.anthonyhilyard.equipmentcompare.gui.ComparisonTooltips;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipPositioner;
import net.minecraft.world.item.ItemStack;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = ComparisonTooltips.class, remap = false)
public class ComparisonTooltipsMixin {

    /**
     * @author Alfakynz
     * @reason This mod fixes tooltip rendering issues when Legendary Tooltips and Equipment Compare are installed by ensuring that the stack count is always hidden behind the tooltips.
     */
    @Inject(
            method = "render",
            at = @At("HEAD"),
            remap = false
    )
    private static void onRenderPre(GuiGraphics graphics, ClientTooltipPositioner positioner, int x, int y, ItemStack itemStack, Minecraft minecraft, Font font, Screen screen, CallbackInfoReturnable<Boolean> cir) {
        graphics.pose().pushPose();
        graphics.pose().translate(0.0F, 0.0F, 2000.0F);
    }

    @Inject(
            method = "render",
            at = @At("RETURN"),
            remap = false
    )
    private static void onRenderPost(GuiGraphics graphics, ClientTooltipPositioner positioner, int x, int y, ItemStack itemStack, Minecraft minecraft, Font font, Screen screen, CallbackInfoReturnable<Boolean> cir) {
        graphics.pose().popPose();
    }
}
