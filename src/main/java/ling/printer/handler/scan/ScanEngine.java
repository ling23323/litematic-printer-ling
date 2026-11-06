package ling.printer.handler.scan;

import fi.dy.masa.litematica.world.WorldSchematic;
import ling.printer.placement.PrinterBox;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.LevelChunk;

import java.util.function.Predicate;
import java.util.function.Supplier;
import ling.printer.core.runtime.RuntimeEpoch;
import ling.printer.core.diagnostics.RuntimeTiming;
import ling.printer.core.runtime.RuntimeComponent;
import ling.printer.core.runtime.RuntimeEvent;
import ling.printer.runtime.PrinterRuntime;
/**
 * Feature-facing scan middleware.
 *
 * <p>The current implementation is backed by {@link ScanCache}. Keeping this
 * boundary separate lets the scan algorithm evolve without making handlers
 * depend on cache ownership, invalidation storage, or cursor internals.</p>
 */
public final class ScanEngine implements RuntimeComponent {
    private final Supplier<RuntimeEpoch> epoch;
    private final Supplier<ClientLevel> currentLevel;
    private final ScanCache cache;

    public ScanEngine(PrinterRuntime runtime, ScanCache cache) {
        this(runtime::epoch, () -> runtime.client().level, cache);
    }

    public ScanEngine(Supplier<RuntimeEpoch> epoch, Supplier<ClientLevel> currentLevel, ScanCache cache) {
        this.epoch = epoch;
        this.currentLevel = currentLevel;
        this.cache = cache;
    }

    public ScanEngine(PrinterRuntime runtime) {
        this(runtime, new ScanCache(runtime.timing()));
    }

    public RuntimeTiming timing() { return this.cache.timing(); }

    public void clear() {
        this.cache.clear();
    }

    @Override
    public void onEpochChanged(RuntimeEvent.EpochChanged event) {
        this.clear();
    }

    public void beginTick(ClientLevel level, WorldSchematic schematic, long tickTime) {
        this.cache.beginTick(level, schematic, tickTime, this.epoch.get());
    }

    public void invalidate(BlockPos pos) {
        this.cache.invalidate(pos);
    }

    public void invalidateChunk(ClientLevel level, LevelChunk chunk) {
        if (level == null || chunk == null || this.currentLevel.get() != level) {
            return;
        }
        ChunkPos pos = chunk.getPos();
        //#if MC >= 260100
        int chunkX = pos.x();
        int chunkZ = pos.z();
        //#else
        //$$ int chunkX = pos.x;
        //$$ int chunkZ = pos.z;
        //#endif
        int minY = level.getMinY();
        this.cache.invalidateChunk(chunkX, chunkZ, minY, minY + level.getHeight() - 1);
    }

    public long dirtyVersion() {
        return this.cache.dirtyVersion();
    }

    public long printSourceRevision() {
        return this.cache.printSourceRevision();
    }

    public DirtyRegionTracker.DirtySnapshot dirtySnapshotAfter(long lastSeenVersion, PrinterBox bounds) {
        return this.cache.dirtySnapshotAfter(lastSeenVersion, bounds);
    }

    public void resetOwner(String ownerKey) {
        this.cache.resetOwner(ownerKey);
    }

    public ScanCache.ScanMetrics metricsFor(String ownerKey) {
        return this.cache.metricsFor(ownerKey);
    }

    public static long key(BlockPos pos) {
        return ScanCache.key(pos);
    }

    public Iterable<BlockPos> rawIterable(
            String ownerKey,
            PrinterBox sourceBox,
            LocalPlayer player,
            int scanGuardLimit,
            Predicate<BlockPos> preFilter
    ) {
        return this.cache.rawIterable(ownerKey, sourceBox, player, scanGuardLimit, preFilter);
    }

    /** 执行一次扫描请求；查询参数由调用方通过 {@link ScanQuery} 组装。 */
    public Iterable<BlockPos> iterable(ScanQuery query) {
        return this.cache.iterable(query);
    }
}
