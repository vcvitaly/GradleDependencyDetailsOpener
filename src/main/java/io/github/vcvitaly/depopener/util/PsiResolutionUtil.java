package io.github.vcvitaly.depopener.util;

import com.intellij.psi.PsiElement;
import com.intellij.psi.impl.source.tree.CompositeElement;
import com.intellij.psi.impl.source.tree.TreeElement;
import io.github.vcvitaly.depopener.enumeration.PsiElementResolutionType;
import io.github.vcvitaly.depopener.resolver.predicate.DescribedPsiElementPredicate;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import java.util.stream.Stream;
import org.jetbrains.plugins.groovy.lang.psi.impl.statements.blocks.GrClosableBlockImpl;

public final class PsiResolutionUtil {

    public static final BiFunction<String, PsiElementResolutionType, Predicate<PsiElement>> PE_PREDICATE_FACTORY =
            PsiResolutionUtil::getCompositeTestPredicate;

    private static String getElementType(TreeElement te) {
        return te.getElementType().toString();
    }

    public static String getElementType(CompositeElement cpe) {
        return getElementType((TreeElement) cpe);
    }

    public static String getToString(CompositeElement cpe) {
        return cpe.toString();
    }

    private static Predicate<PsiElement> getCompositeTestPredicate(String psiTypeName, PsiElementResolutionType peResolutionType) {
        return new DescribedPsiElementPredicate(
                psiTypeName,
                pe -> PsiResolutionUtil.performCompositePeTest(pe, psiTypeName, peResolutionType)
        );
    }

    public static boolean performCompositePeTest(PsiElement pe, String s, PsiElementResolutionType peResolutionType) {
        return pe.getNode() instanceof CompositeElement cpe &&
                peResolutionType.getTypeResolver().apply(cpe).equals(s);
    }

    public static List<Predicate<PsiElement>> getPePredicates(List<String> psiTypeNames, PsiElementResolutionType peResolutionType) {
        return Stream.concat(
                psiTypeNames.stream().map(s -> PE_PREDICATE_FACTORY.apply(s, peResolutionType)),
                Stream.of(pe -> pe instanceof GrClosableBlockImpl)
        ).toList();
    }

    private PsiResolutionUtil() {
    }
}
