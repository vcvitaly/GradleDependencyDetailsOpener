package io.github.vcvitaly.depopener.enumeration;

import com.intellij.psi.impl.source.tree.CompositeElement;
import io.github.vcvitaly.depopener.util.ScopeResolutionUtil;
import java.util.function.Function;

public enum ScopeArgumentResolutionType {
    ELEMENT_TYPE(ScopeResolutionUtil::getElementType),
    TO_STRING(ScopeResolutionUtil::getToString);

    private final Function<CompositeElement, String> typeResolver;

    ScopeArgumentResolutionType(Function<CompositeElement, String> typeResolver) {
        this.typeResolver = typeResolver;
    }

    public Function<CompositeElement, String> getTypeResolver() {
        return typeResolver;
    }
}
