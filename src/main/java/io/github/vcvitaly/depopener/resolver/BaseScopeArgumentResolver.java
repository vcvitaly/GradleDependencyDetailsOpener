package io.github.vcvitaly.depopener.resolver;

import com.intellij.psi.PsiElement;
import com.intellij.psi.impl.source.tree.LeafPsiElement;
import io.github.vcvitaly.depopener.enumeration.ScopeArgumentResolutionType;
import io.github.vcvitaly.depopener.util.ScopeResolutionUtil;
import java.util.List;
import java.util.function.Predicate;

public abstract class BaseScopeArgumentResolver implements ScopeArgumentResolver {

    private final List<Predicate<PsiElement>> pePredicates;
    private final Predicate<PsiElement> methodCallPredicate;

    public BaseScopeArgumentResolver(String methodScopeValue,
                                     ScopeArgumentResolutionType argResolutionType,
                                     List<String> predicateArgs) {
        this.pePredicates = ScopeResolutionUtil.getPePredicates(predicateArgs, argResolutionType);
        this.methodCallPredicate = ScopeResolutionUtil.PE_PREDICATE_FACTORY
                .apply(methodScopeValue, ScopeArgumentResolutionType.TO_STRING);
    }

    @Override
    public boolean resolve(LeafPsiElement lpe) {
        if (lpe.getElementType().toString().endsWith("quoted string")) {
            PsiElement parent = lpe.getParent();
            for (Predicate<PsiElement> pePredicate : pePredicates) {
                if (pePredicate.test(parent)) {
                    parent = parent.getParent();
                } else {
                    return false;
                }
            }
            if (methodCallPredicate.test(parent)) {
                return parent.getFirstChild() != null &&
                        parent.getFirstChild().getFirstChild() != null &&
                        parent.getFirstChild().getFirstChild().getText().equals("dependencies");
            }
        }
        return false;
    }
}
