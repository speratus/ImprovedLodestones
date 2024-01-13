package blind.fold.improved_lodestones;

import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;

import static blind.fold.improved_lodestones.ClientPlayPacketListenerExt.onLodestoneUpdate;

public record LodestoneUpdateS2CPacket(
        RegistryKey<World> dimension,
        BlockPos pos,
        LodestoneState state
) implements Packet<ClientPlayPacketListener> {
  
  public LodestoneUpdateS2CPacket(PacketByteBuf buf) {
    this(buf.readRegistryKey(Registry.WORLD_KEY), buf.readBlockPos(), LodestoneState.SERIALIZER.read(buf));
  }
  
  @Override
  public void write(PacketByteBuf buf) {
    buf.writeRegistryKey(dimension);
    buf.writeBlockPos(pos);
    LodestoneState.SERIALIZER.write(buf, state);
  }
  
  @Override
  public void apply(ClientPlayPacketListener listener) {
    onLodestoneUpdate(listener, this);
  }
  
}