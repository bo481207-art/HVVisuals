package com.yourname.educheat.features;

import com.yourname.educheat.EduCheat;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.Comparator;
import java.util.List;

public class AimBot {
    private LivingEntity currentTarget = null;
    
    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.START) return;
        if (!EduCheat.getInstance().aimbotEnabled) return;
        
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.world == null) return;
        if (mc.isGamePaused()) return;
        
        LivingEntity target = getBestTarget(mc);
        if (target == null) {
            currentTarget = null;
            return;
        }
        
        currentTarget = target;
        smoothAim(mc, target);
    }
    
    private LivingEntity getBestTarget(Minecraft mc) {
        EduCheat config = EduCheat.getInstance();
        double range = config.aimbotRange;
        double fov = config.aimbotFov;
        
        List<LivingEntity> entities = mc.world.getEntitiesWithinAABB(
            LivingEntity.class,
            new AxisAlignedBB(
                mc.player.getPosX() - range,
                mc.player.getPosY() - range,
                mc.player.getPosZ() - range,
                mc.player.getPosX() + range,
                mc.player.getPosY() + range,
                mc.player.getPosZ() + range
            )
        );
        
        entities.removeIf(entity -> {
            if (entity == mc.player) return true;
            if (!entity.isAlive()) return true;
            if (config.aimbotOnlyMonsters && !(entity instanceof MonsterEntity)) return true;
            if (entity instanceof PlayerEntity && ((PlayerEntity) entity).isCreative()) return true;
            return false;
        });
        
        if (entities.isEmpty()) return null;
        
        return entities.stream()
            .filter(e -> getAngleToEntity(mc, e) <= fov)
            .min(Comparator.comparingDouble(e -> getAngleToEntity(mc, e)))
            .orElse(null);
    }
    
    private double getAngleToEntity(Minecraft mc, LivingEntity entity) {
        Vector3d playerPos = mc.player.getEyePosition(1.0f);
        Vector3d entityPos = entity.getBoundingBox().getCenter();
        Vector3d toEntity = entityPos.subtract(playerPos).normalize();
        Vector3d lookVec = mc.player.getLook(1.0f);
        
        double dot = lookVec.dotProduct(toEntity);
        if (dot > 1.0) dot = 1.0;
        if (dot < -1.0) dot = -1.0;
        
        return Math.toDegrees(Math.acos(dot));
    }
    
    private void smoothAim(Minecraft mc, LivingEntity target) {
        EduCheat config = EduCheat.getInstance();
        float smoothness = config.aimbotSmoothness;
        
        Vector3d targetPos = target.getBoundingBox().getCenter();
        targetPos = targetPos.add(0, target.getHeight() * 0.8, 0);
        
        Vector3d playerPos = mc.player.getEyePosition(1.0f);
        Vector3d diff = targetPos.subtract(playerPos);
        
        float targetYaw = (float) (Math.toDegrees(Math.atan2(diff.z, diff.x)) - 90.0);
        float targetPitch = (float) (-Math.toDegrees(Math.atan2(diff.y, Math.sqrt(diff.x * diff.x + diff.z * diff.z))));
        targetPitch = Math.max(-90, Math.min(90, targetPitch));
        
        float currentYaw = mc.player.rotationYaw;
        float currentPitch = mc.player.rotationPitch;
        
        float yawDiff = targetYaw - currentYaw;
        while (yawDiff > 180) yawDiff -= 360;
        while (yawDiff < -180) yawDiff += 360;
        
        float pitchDiff = targetPitch - currentPitch;
        
        mc.player.rotationYaw = currentYaw + yawDiff * smoothness;
        mc.player.rotationPitch = currentPitch + pitchDiff * smoothness;
        mc.player.rotationYawHead = mc.player.rotationYaw;
    }
    
    public LivingEntity getCurrentTarget() { return currentTarget; }
}
