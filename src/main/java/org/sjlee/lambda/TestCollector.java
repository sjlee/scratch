package org.sjlee.lambda;

import java.util.Collections;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class TestCollector {
  public static void main(String[] args) {
  }

  private static class StringCollector
      implements Collector<String,StringJoiner,String> {
    private final String delim;
    private final String prefix;
    private final String suffix;

    public StringCollector(String delim, String prefix, String suffix) {
      this.delim = delim;
      this.prefix = prefix;
      this.suffix = suffix;
    }

    @Override
    public Supplier<StringJoiner> supplier() {
      return () -> new StringJoiner(delim, prefix, suffix);
    }

    @Override
    public BiConsumer<StringJoiner, String> accumulator() {
      return StringJoiner::add;
    }

    @Override
    public BinaryOperator<StringJoiner> combiner() {
      return StringJoiner::merge;
    }

    @Override
    public Function<StringJoiner, String> finisher() {
      return StringJoiner::toString;
    }

    @Override
    public Set<java.util.stream.Collector.Characteristics> characteristics() {
      return Collections.emptySet();
    }

  }

  private static class StringJoiner {
    private final String delim;
    private final String prefix;
    private final String suffix;
    private final StringBuilder builder = new StringBuilder();

    public StringJoiner(String delim, String prefix, String suffix) {
      this.delim = delim;
      this.prefix = prefix;
      this.suffix = suffix;
    }

    public StringJoiner add(String element) {
      if (areAtStart()) {
        builder.append(prefix);
      } else {
        builder.append(delim);
      }
      builder.append(element);
      return this;
    }

    public StringJoiner merge(StringJoiner other) {
      builder.append(other.builder);
      return this;
    }

    private boolean areAtStart() {
      return builder.length() == 0;
    }

    @Override
    public String toString() {
      builder.append(suffix);
      return builder.toString();
    }
  }
}