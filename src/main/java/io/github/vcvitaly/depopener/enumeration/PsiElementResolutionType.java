package io.github.vcvitaly.depopener.enumeration;

import com.intellij.psi.impl.source.tree.CompositeElement;
import io.github.vcvitaly.depopener.util.PsiResolutionUtil;
import java.util.function.Function;

public enum PsiElementResolutionType {
    ELEMENT_TYPE(PsiResolutionUtil::getElementType),
    TO_STRING(PsiResolutionUtil::getToString);
//    WRAPPER_TO_STRING();

    private final Function<CompositeElement, String> typeResolver;

    PsiElementResolutionType(Function<CompositeElement, String> typeResolver) {
        this.typeResolver = typeResolver;
    }

    public Function<CompositeElement, String> getTypeResolver() {
        return typeResolver;
    }
}
