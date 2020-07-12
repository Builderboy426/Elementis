package com.builderboy.elementis.item;

import com.builderboy.elementis.Elementis;
import net.minecraft.item.Item;

public class BaseItem extends Item {
    public BaseItem() {
        super(new Item.Properties().group(Elementis.GROUP));
    }

    public BaseItem(Item.Properties properties) {
        super(properties);
    }
}