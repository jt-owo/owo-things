package dev.jtowo.things.common.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.util.Objects;

public record CodecValue<T>(T value) {
    public static final CodecValue<Boolean> TRUE = new CodecValue<>(true);
    public static final CodecValue<Boolean> FALSE = new CodecValue<>(false);

    @Override
    public int hashCode() {
        return Objects.hash(this.value);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else {
            return obj instanceof CodecValue(Object other)
                    && this.value == other;
        }
    }

    public static final Codec<CodecValue<Boolean>> BOOL_CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.BOOL.fieldOf("value").forGetter(CodecValue::value)
            ).apply(instance, CodecValue::new)
    );

    public static final StreamCodec<ByteBuf, CodecValue<Boolean>> BOOL_STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL, CodecValue::value,
            CodecValue::new
    );
}
