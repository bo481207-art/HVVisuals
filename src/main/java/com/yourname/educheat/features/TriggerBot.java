package com.yourname.educheat.features;

import com.yourname.educheat.EduCheat;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class TriggerBot {
    private long lastAttackTime = 0;
    
    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        if (!EduCheat.getInstance().triggerbotEnabled) return;
        
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.world == null) return;
        if (mc.isGamePaused()) return;
        
        // Задержка между атаками
        long currentTime = System.currentTimeMillis();
        int delay = EduCheat.getInstance().triggerDelay;
        if (currentTime - lastAttackTime < delay) return;
        
        // Проверяем, смотрит ли игрок на моба
        Entity target = getEntityLookingAt(mc);
        if (!(target instanceof LivingEntity)) return;
        
        LivingEntity livingTarget = (LivingEntity) target;
        
        // Проверяем, только ли мобов атакуем
        if (EduCheat.getInstance().aimbotOnlyMonsters && !(livingTarget instanceof MonsterEntity)) return;
        
        // Проверяем, жив ли
        if (livingTarget.isAlive() && livingTarget.getHealth() > 0) {
            mc.playerController.attackEntity(mc.player, livingTarget);
            mc.player.swingArm(Hand.MAIN_HAND);
            lastAttackTime = currentTime;
        }
    }
    
    private Entity getEntityLookingAt(Minecraft mc) {
        double range = EduCheat.getInstance().aimbotRange;
        Vector3d start = mc.player.getEyePosition(1.0f);
        Vector3d lookVec = mc.player.getLook(1.0f);
        Vector3d end = start.add(lookVec.scale(range));
        
        // Проверка блоков (стен)
        RayTraceContext context = new RayTraceContext(
            start,
            end,
            RayTraceContext.BlockMode.OUTLINE,
            RayTraceContext.FluidMode.NONE,
            mc.player
        );
        RayTraceResult blockHit = mc.world.rayTraceBlocks(context);
        double blockDistance = blockHit.getType() != RayTraceResult.Type.MISS ? 
            start.distanceTo(blockHit.getHitVec()) : range;
        
        Entity target = null;
        double minDistance = Math.min(range, blockDistance);
        
        // Получаем все сущности в радиусе
        AxisAlignedBB searchBox = mc.player.getBoundingBox().grow(range);
        for (Entity entity : mc.world.getEntitiesInAABBexcluding(mc.player, searchBox, e -> e instanceof LivingEntity && e.isAlive())) {
            AxisAlignedBB aabb = entity.getBoundingBox().grow(0.2);
            Vector3d hitPos = aabb.rayTrace(start, end, start).orElse(null);
            
            if (hitPos != null) {
                double distance = start.distanceTo(hitPos);
                if (distance < minDistance) {
                    minDistance = distance;
                    target = entity;
                }
            }
        }
        
        return target;
    }
}
