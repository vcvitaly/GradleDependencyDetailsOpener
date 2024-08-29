package io.github.vcvitaly.depopener.resolver;

import com.intellij.psi.PsiElement;
import com.intellij.psi.impl.source.tree.LeafPsiElement;
import io.github.vcvitaly.depopener.enumeration.PsiElementResolutionType;
import io.github.vcvitaly.depopener.util.PsiResolutionUtil;
import java.util.List;
import java.util.function.Predicate;

public abstract class BaseScopeArgumentResolver implements ScopeArgumentResolver {

    private final List<Predicate<PsiElement>> pePredicates;
    private final Predicate<PsiElement> methodCallPredicate;

    public BaseScopeArgumentResolver(String methodScopeValue,
                                     PsiElementResolutionType argResolutionType,
                                     List<String> predicateArgs) {
        this.pePredicates = PsiResolutionUtil.getPePredicates(predicateArgs, argResolutionType);
        this.methodCallPredicate = PsiResolutionUtil.PE_PREDICATE_FACTORY
                .apply(methodScopeValue, argResolutionType);
    }

    @Override
    public boolean resolve(PsiElement pe) {
        if (pe instanceof LeafPsiElement lpe && lpe.getElementType().toString().endsWith("quoted string")) {
            PsiElement parent = pe.getParent();
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
