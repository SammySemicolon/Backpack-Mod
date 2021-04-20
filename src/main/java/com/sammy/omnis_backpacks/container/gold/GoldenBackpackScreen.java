package com.sammy.omnis_backpacks.container.gold;

import com.sammy.omnis_backpacks.container.AbstractBackpackContainer;
import com.sammy.omnis_backpacks.container.AbstractBackpackScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

public class GoldenBackpackScreen extends AbstractBackpackScreen
{
    public GoldenBackpackScreen(AbstractBackpackContainer container, PlayerInventory player, ITextComponent title)
    {
        super(container, player, title, "textures/gui/backpack_gold");
    }
}
