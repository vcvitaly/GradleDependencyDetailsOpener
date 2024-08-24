package io.github.vcvitaly.depopener.resolver;

import com.intellij.psi.PsiElement;
import com.intellij.psi.impl.source.tree.CompositeElement;
import com.intellij.psi.impl.source.tree.LeafPsiElement;
import com.intellij.psi.impl.source.tree.TreeElement;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import org.jetbrains.plugins.groovy.lang.psi.impl.statements.blocks.GrClosableBlockImpl;

public class ScopeArgumentResolverImpl implements ScopeArgumentResolver {

    private static final Function<String, Predicate<PsiElement>> PE_PREDICATE_FACTORY =
            s -> pe -> performCompositePeTest(pe, s);

    private static final List<Predicate<PsiElement>> PE_PREDICATES = List.of(
            PE_PREDICATE_FACTORY.apply("LITERAL"),
            PE_PREDICATE_FACTORY.apply("APPLICATION_ARGUMENT_LIST"),
            PE_PREDICATE_FACTORY.apply("APPLICATION_EXPRESSION"),
            pe -> pe instanceof GrClosableBlockImpl
    );

    private static String getElementType(TreeElement te) {
        return te.getElementType().toString();
    }

    private static boolean performCompositePeTest(PsiElement pe, String s) {
        return pe.getNode() instanceof CompositeElement cpe &&
                getElementType(cpe).equals(s);
    }

    @Override
    public boolean resolve(LeafPsiElement lpe) {
        if (lpe.getElementType().toString().endsWith("quoted string")) {
            PsiElement parent = lpe.getParent();
            for (Predicate<PsiElement> pePredicate : PE_PREDICATES) {
                if (pePredicate.test(parent)) {
                    parent = parent.getParent();
                } else {
                    return false;
                }
            }
            if (PE_PREDICATE_FACTORY.apply("METHOD_CALL_EXPRESSION").test(parent)) {
                return parent.getFirstChild() != null &&
                        parent.getFirstChild().getFirstChild() != null &&
                        parent.getFirstChild().getFirstChild().getText().equals("dependencies");
            }
        }
        return false;
    }
}
