package io.github.vcvitaly.depopener.resolver.predicate;

import com.intellij.psi.PsiElement;
import java.util.function.Predicate;

public class DescribedPsiElementPredicate implements Predicate<PsiElement> {

    private final String comparisonString;
    private final Predicate<PsiElement> tester;

    public DescribedPsiElementPredicate(String comparisonString, Predicate<PsiElement> tester) {
        this.comparisonString = comparisonString;
        this.tester = tester;
    }

    @Override
    public boolean test(PsiElement pe) {
        return tester.test(pe);
    }

    @Override
    public String toString() {
        return "DescribedPsiElementPredicate{comparisonString='%s'}".formatted(comparisonString);
    }
}
