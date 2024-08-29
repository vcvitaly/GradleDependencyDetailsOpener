package io.github.vcvitaly.depopener.util;

import com.intellij.openapi.util.TextRange;

public final class DependencyTextRangeUtil {

    private DependencyTextRangeUtil() {
    }

    public static TextRange getDependencyTextRange(TextRange elementTextRange) {
        return TextRange.from(
                getDependencyTextStartOffset(elementTextRange),
                getDependencyTextLength(elementTextRange)
        );
    }

    private static int getDependencyTextStartOffset(TextRange elementTextRange) {
        return elementTextRange.getStartOffset() + Constants.QUOTE_OFFSET_SHIFT;
    }

    private static int getDependencyTextLength(TextRange elementTextRange) {
        return elementTextRange.getLength() - Constants.COUNT_OF_QUOTES;
    }
}
