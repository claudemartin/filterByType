package ch.claude_martin.filterByType;

import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Gatherer;

public interface FilterByType {
	/**
	 * Filter elements by given type. To be used with
	 * {@link java.util.stream.Stream#mapMulti(BiConsumer)}.
	 */
	@SuppressWarnings("unchecked")
	public static <T, R extends T> BiConsumer<T, Consumer<R>> filterByType(Class<R> type) {
		Objects.requireNonNull(type, "type");
		return (element, consumer) -> {
			if (element != null && type.isAssignableFrom(element.getClass())) {
				consumer.accept((R) element);
			}
		};
	}

	/**
	 * Filter elements by given type. To be used with
	 * {@link java.util.stream.Stream#gather(java.util.stream.Gatherer)}.
	 */
	@SuppressWarnings("unchecked")
	public static <T, R extends T> Gatherer<T, ?, R> byType(Class<R> type) {
		Objects.requireNonNull(type, "type");
		return () -> (state, element, downstream) -> type.isInstance(element) ? downstream.push((R) element) : true;
	}

}
