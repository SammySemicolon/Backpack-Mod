package com.sammy.backpack_mod.container.netherite;

import com.sammy.backpack_mod.BackpackModHelper;
import com.sammy.backpack_mod.container.AbstractBackpackContainer;
import com.sammy.backpack_mod.container.AbstractBackpackScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

public class NetheriteBackpackScreen extends AbstractBackpackScreen
{
    public NetheriteBackpackScreen(AbstractBackpackContainer container, PlayerInventory player, ITextComponent title)
    {
        super(container, player, title, BackpackModHelper.prefix("textures/gui/backpack_netherite.png"));
        ySize = 222;
    }
}
