package com.alfakynz.ltxec.mixin;

import java.util.List;

import com.anthonyhilyard.equipmentcompare.config.EquipmentCompareConfig;
import com.anthonyhilyard.equipmentcompare.gui.ComparisonTooltips;
import com.anthonyhilyard.iceberg.services.Services;
import com.anthonyhilyard.iceberg.util.GuiHelper;
import com.anthonyhilyard.iceberg.util.Tooltips;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipPositioner;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.ItemStack;

import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import static com.anthonyhilyard.equipmentcompare.gui.ComparisonTooltips.getEquippedBadge;

@Mixin(ComparisonTooltips.class)
public class ComparisonTooltipsMixin {
    /**
     * @author Alfakynz
     * @reason This mod fixes tooltip rendering issues when Legendary Tooltips and Equipment Compare are installed by ensuring that the stack count is always hidden behind the tooltips.
     */
    @Overwrite
    private static void drawTooltip(GuiGraphics graphics, ClientTooltipPositioner positioner, ItemStack itemStack, Rect2i rect, List<ClientTooltipComponent> tooltipLines, Font font, Screen screen, int maxWidth, boolean showBadge, int index) {
        boolean legendaryTooltipsPresent = Services.getPlatformHelper().isModLoaded("legendarytooltips");
        if (showBadge && legendaryTooltipsPresent) {
            rect.setHeight(rect.getHeight() + 11);
        }

        PoseStack poseStack = graphics.pose();
        poseStack.pushPose();
        poseStack.translate(0.0F, 0.0F, 2000.0F);

        Tooltips.renderItemTooltip(itemStack, font, tooltipLines, rect, graphics, positioner, showBadge, index);
        if (showBadge && !legendaryTooltipsPresent) {
            int bgColor = EquipmentCompareConfig.getInstance().badgeBackgroundColor.get().intValue();
            int borderStartColor = EquipmentCompareConfig.getInstance().badgeBorderStartColor.get().intValue();
            int borderEndColor = EquipmentCompareConfig.getInstance().badgeBorderEndColor.get().intValue();
            Style textColor = Style.EMPTY.withColor(TextColor.fromRgb(EquipmentCompareConfig.getInstance().badgeTextColor.get().intValue()));
            MutableComponent equippedBadge = getEquippedBadge().withStyle(textColor);
            if (rect.getY() + rect.getHeight() + 4 > screen.height) {
                rect = new Rect2i(rect.getX(), screen.height - rect.getHeight() - 4, rect.getWidth(), rect.getHeight());
            }

            poseStack.pushPose();
            poseStack.translate((float)(rect.getX() - 2), (float)(rect.getY() - 4), 0.0F);
            Matrix4f matrix = poseStack.last().pose();
            GuiHelper.drawGradientRect(matrix, -1, 1, -17, rect.getWidth() + 7, -16, bgColor, bgColor);
            GuiHelper.drawGradientRect(matrix, -1, 0, -16, 1, -4, bgColor, bgColor);
            GuiHelper.drawGradientRect(matrix, -1, rect.getWidth() + 7, -16, rect.getWidth() + 8, -4, bgColor, bgColor);
            GuiHelper.drawGradientRect(matrix, -1, 1, -4, rect.getWidth() + 7, -3, bgColor, bgColor);
            GuiHelper.drawGradientRect(matrix, -1, 1, -16, rect.getWidth() + 7, -4, bgColor, bgColor);
            GuiHelper.drawGradientRect(matrix, -1, 1, -15, 2, -5, borderStartColor, borderEndColor);
            GuiHelper.drawGradientRect(matrix, -1, rect.getWidth() + 6, -15, rect.getWidth() + 7, -5, borderStartColor, borderEndColor);
            GuiHelper.drawGradientRect(matrix, -1, 1, -16, rect.getWidth() + 7, -15, borderStartColor, borderStartColor);
            GuiHelper.drawGradientRect(matrix, -1, 1, -5, rect.getWidth() + 7, -4, borderEndColor, borderEndColor);
            graphics.drawCenteredString(font, equippedBadge, rect.getWidth() / 2 + 4, -14, -1);
            poseStack.popPose();
        }
        poseStack.popPose();
    }
}
