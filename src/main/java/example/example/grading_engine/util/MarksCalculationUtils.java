
package example.example.grading_engine.util;

import example.example.grading_engine.enums.marksvalidation.MarkComponentType;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class MarksCalculationUtils {

    private MarksCalculationUtils() {}

    public static EnumMap<MarkComponentType, BigDecimal> buildCompleteMarks(
            Map<MarkComponentType, BigDecimal> rawMarks,
            List<MarkComponentType> types
    ) {
        EnumMap<MarkComponentType, BigDecimal> complete = new EnumMap<>(MarkComponentType.class);
        for (MarkComponentType t : types) {
            BigDecimal val = rawMarks.get(t);
            complete.put(t, val != null ? val : BigDecimal.ZERO);
        }
        return complete;
    }

    public static BigDecimal calculateTotal(EnumMap<MarkComponentType, BigDecimal> marks) {
        BigDecimal total = BigDecimal.ZERO;
        for (BigDecimal val : marks.values()) {
            if (val != null) {
                total = total.add(val);
            }
        }
        return total;
    }

    public static BigDecimal calculateAverage(BigDecimal total, int count) {
        if (count == 0) return BigDecimal.ZERO;
        return total.divide(BigDecimal.valueOf(count), 2, RoundingMode.HALF_UP);
    }

    public static BigDecimal calculateMean(List<BigDecimal> totals) {
        if (totals.isEmpty()) return BigDecimal.ZERO;
        BigDecimal sum = BigDecimal.ZERO;
        for (BigDecimal t : totals) sum = sum.add(t);
        return sum.divide(BigDecimal.valueOf(totals.size()), 6, RoundingMode.HALF_UP)
                  .setScale(4, RoundingMode.HALF_UP);
    }

    public static BigDecimal calculateStdDev(List<BigDecimal> totals, BigDecimal mean) {
        if (totals.isEmpty()) return BigDecimal.ZERO;
        BigDecimal varianceSum = BigDecimal.ZERO;
        for (BigDecimal t : totals) {
            BigDecimal diff = t.subtract(mean);
            varianceSum = varianceSum.add(diff.multiply(diff));
        }
        BigDecimal variance = varianceSum.divide(
                BigDecimal.valueOf(totals.size()),
                6,
                RoundingMode.HALF_UP
        );
        return sqrt(variance, 6).setScale(4, RoundingMode.HALF_UP);
    }

    private static BigDecimal sqrt(BigDecimal value, int scale) {
        if (value.compareTo(BigDecimal.ZERO) <= 0) {
            return BigDecimal.ZERO.setScale(scale, RoundingMode.HALF_UP);
        }
        BigDecimal x = new BigDecimal(Math.sqrt(value.doubleValue()));
        BigDecimal two = BigDecimal.valueOf(2);
        for (int i = 0; i < 20; i++) {
            x = x.add(value.divide(x, scale + 8, RoundingMode.HALF_UP))
                 .divide(two, scale + 8, RoundingMode.HALF_UP);
        }
        return x.setScale(scale, RoundingMode.HALF_UP);
    }

    public static BigDecimal calculateMin(EnumMap<MarkComponentType, BigDecimal> complete) {
        if (complete == null || complete.isEmpty()) {
            return BigDecimal.ZERO;
        }
        BigDecimal min = null;
        for (BigDecimal val : complete.values()) {
            if (val == null) continue;
            if (min == null || val.compareTo(min) < 0) {
                min = val;
            }
        }
        return min != null ? min : BigDecimal.ZERO;
    }

    public static BigDecimal calculateMax(EnumMap<MarkComponentType, BigDecimal> complete) {
        if (complete == null || complete.isEmpty()) {
            return BigDecimal.ZERO;
        }
        BigDecimal max = null;
        for (BigDecimal val : complete.values()) {
            if (val == null) continue;
            if (max == null || val.compareTo(max) > 0) {
                max = val;
            }
        }
        return max != null ? max : BigDecimal.ZERO;
    }
}
