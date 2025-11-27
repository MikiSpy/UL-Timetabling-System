package util;

import java.util.HashSet;
import java.util.Set;

public class WeeksPattern {

    /**
     * Parses a weeks string like "1-12" or "1,2,4-6" into a set of integers.
     */
    public static Set<Integer> parse(String weeks) {
        Set<Integer> result = new HashSet<>();
        if (weeks == null || weeks.isBlank()) return result;

        String[] parts = weeks.split(",");
        for (String part : parts) {
            part = part.trim();
            if (part.contains("-")) {
                String[] range = part.split("-");
                int start = Integer.parseInt(range[0]);
                int end = Integer.parseInt(range[1]);
                for (int i = start; i <= end; i++) {
                    result.add(i);
                }
            } else {
                result.add(Integer.parseInt(part));
            }
        }
        return result;
    }

    /**
     * Checks if two week patterns overlap.
     */
    public static boolean intersects(String weeksA, String weeksB) {
        Set<Integer> setA = parse(weeksA);
        Set<Integer> setB = parse(weeksB);
        for (Integer w : setA) {
            if (setB.contains(w)) {
                return true;
            }
        }
        return false;
    }
}
