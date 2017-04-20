package com.moseeker.common.util;

import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

/**
 * @author ltf
 * 自定义 strem collectors
 * 2017年3月24日
 */
public final class MyCollectors {

	/**
	 * 实现toMap方法，解决原collectors.toMap() 不能指定value为null的问题
	 * @param f1
	 * @param f2
	 * @return
	 */
	public static <T, K, V> Collector<T, ?, Map<K, V>> toMap(Function<T, K> f1, Function<T, V> f2) {
		
		return new ForceToMapCollector<T, K, V>(f1, f2);
	}
	
    public static class ForceToMapCollector<T, K, V> implements Collector<T, Map<K, V>, Map<K, V>> {
		 
		private Function<? super T, ? extends K> keyMapper;
	 
		private Function<? super T, ? extends V> valueMapper;
	 
		public ForceToMapCollector(Function<? super T, ? extends K> keyMapper,
				Function<? super T, ? extends V> valueMapper) {
			super();
			this.keyMapper = keyMapper;
			this.valueMapper = valueMapper;
		}
	 
		@Override
		public BiConsumer<Map<K, V>, T> accumulator() {
			return (map, element) -> map.put(keyMapper.apply(element), valueMapper.apply(element));
		}
	 
		@Override
		public Supplier<Map<K, V>> supplier() {
			return HashMap::new;
		}
	 
		@Override
		public BinaryOperator<Map<K, V>> combiner() {
			return null;
		}
	 
		@Override
		public Function<Map<K, V>, Map<K, V>> finisher() {
			return null;
		}
	 
		@Override
		public Set<Characteristics> characteristics() {
			return Collections.unmodifiableSet(EnumSet.of(Collector.Characteristics.IDENTITY_FINISH));
		}
	 
	}
}
