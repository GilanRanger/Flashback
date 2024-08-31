package com.moulberry.flashback.mixin.compat.bobby;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.moulberry.flashback.playback.ReplayServer;
import com.moulberry.mixinconstraints.annotations.IfModLoaded;
import de.johni0702.minecraft.bobby.Bobby;
import net.minecraft.client.Minecraft;
import net.minecraft.client.server.IntegratedServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@IfModLoaded("bobby")
@Mixin(value = Bobby.class, remap = false)
public class MixinBobby {

    @WrapOperation(method = "isEnabled", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;getSingleplayerServer()Lnet/minecraft/client/server/IntegratedServer;"))
    public IntegratedServer isEnabled_getSingleplayerServer(Minecraft instance, Operation<IntegratedServer> original) {
        IntegratedServer server = original.call(instance);
        if (server instanceof ReplayServer) {
            return null; // Turn bobby on when viewing replay, even if disabled in singleplayer
        }
        return server;
    }

}
