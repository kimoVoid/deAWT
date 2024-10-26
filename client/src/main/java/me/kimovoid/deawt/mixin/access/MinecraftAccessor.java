package me.kimovoid.deawt.mixin.access;

import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Minecraft.class)
public interface MinecraftAccessor {

    @Accessor("fullscreen")
    boolean isFullscreen();

    @Accessor("fullscreen")
    void setFullscreen(boolean b);
}