package ch.claude_martin.filterByType;

import static ch.claude_martin.filterByType.FilterByType.byType;
import static ch.claude_martin.filterByType.FilterByType.filterByType;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Gatherer;

public class Demo {
	public static void main(String[] args) {
		List<Number> list = Arrays.asList(1, 2, 3, 4L, BigDecimal.TEN, null);

		// using filter and map:
		var ints = list.stream().filter(Integer.class::isInstance).map(Integer.class::cast).toList();
		assertEquals(ints, List.of(1, 2, 3));
		
		// using multiMap (Java 16+):
		var longs = list.stream().mapMulti(filterByType(Long.class)).toList();
		assertEquals(longs, List.of(4L));

		var bigDecimals = list.stream().mapMulti(filterByType(BigDecimal.class)).toList();
		assertEquals(bigDecimals, List.of(BigDecimal.TEN));

		var objects = list.stream().mapMulti(filterByType(Object.class)).toList();
		assertEquals(objects, list.stream().filter(Objects::nonNull).toList());

		// Using gather requires Java 22 with previews enabled:
		var gathered = list.stream().gather(byType(Integer.class)).toList();
		assertEquals(gathered, ints);
	}

	private static void assertEquals(Object a, Object b) {
		System.out.print("Comparing " + a + " and " + b);
		if (!a.equals(b)) {
			throw new AssertionError("Not equal");
		}

		System.out.println(": equal");
	}
}
