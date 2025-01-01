package dev.imb11.shields.datagen;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.List;

public class ShieldsAtlas {
    public static class Source {
        public static final Codec<Source> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Codec.STRING.fieldOf("type").forGetter(Source::getType),
                Codec.STRING.fieldOf("resource").forGetter(Source::getResource)
        ).apply(instance, Source::new));

        private final String type;
        private final String resource;

        public Source(String type, String resource) {
            this.type = type;
            this.resource = resource;
        }

        public String getType() {
            return type;
        }

        public String getResource() {
            return resource;
        }
    }

    public static final Codec<ShieldsAtlas> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.list(Source.CODEC).fieldOf("sources").forGetter(ShieldsAtlas::getSources)
    ).apply(instance, ShieldsAtlas::new));

    private final List<Source> sources;

    public ShieldsAtlas(List<Source> sources) {
        this.sources = sources;
    }

    public List<Source> getSources() {
        return sources;
    }

    public void add(Source source) {
        sources.add(source);
    }
}