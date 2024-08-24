package io.github.vcvitaly.depopener.util;

import com.intellij.psi.PsiElement;
import com.intellij.psi.impl.source.tree.CompositeElement;
import com.intellij.psi.impl.source.tree.TreeElement;
import io.github.vcvitaly.depopener.enumeration.ScopeArgumentResolutionType;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import java.util.stream.Stream;
import org.jetbrains.plugins.groovy.lang.psi.impl.statements.blocks.GrClosableBlockImpl;

public final class ScopeResolutionUtil {

    public static final BiFunction<String, ScopeArgumentResolutionType, Predicate<PsiElement>> PE_PREDICATE_FACTORY =
            ScopeResolutionUtil::getCompositeTestPredicate;

    private static String getElementType(TreeElement te) {
        return te.getElementType().toString();
    }

    public static boolean performCompositePeTest(PsiElement pe, String s, ScopeArgumentResolutionType argResolutionType) {
        return pe.getNode() instanceof CompositeElement cpe &&
                argResolutionType.getTypeResolver().apply(cpe).equals(s);
    }

    public static String getElementType(CompositeElement cpe) {
        return getElementType((TreeElement) cpe);
    }

    public static String getToString(CompositeElement cpe) {
        return cpe.toString();
    }

    public static List<Predicate<PsiElement>> getPePredicates(List<String> psiTypeNames, ScopeArgumentResolutionType argResolutionType) {
        return Stream.concat(
                psiTypeNames.stream().map(s -> PE_PREDICATE_FACTORY.apply(s, argResolutionType)),
                Stream.of(pe -> pe instanceof GrClosableBlockImpl)
        ).toList();
    }

    private static Predicate<PsiElement> getCompositeTestPredicate(String psiTypeName, ScopeArgumentResolutionType argResolutionType) {
        return pe -> ScopeResolutionUtil.performCompositePeTest(pe, psiTypeName, argResolutionType);
    }

    private ScopeResolutionUtil() {
    }
}
