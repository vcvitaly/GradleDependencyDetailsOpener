package io.github.vcvitaly.depopener.annotator;

import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.impl.source.tree.CompositeElement;
import com.intellij.psi.impl.source.tree.LeafPsiElement;
import com.intellij.psi.impl.source.tree.TreeElement;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.plugins.groovy.lang.psi.impl.statements.blocks.GrClosableBlockImpl;

public class GradleDependencyAnnotator implements Annotator {

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
    public void annotate(@NotNull PsiElement element, @NotNull AnnotationHolder holder) {
        if (holder.isBatchMode()) {
            return;
        }

        final PsiFile containingFile = element.getContainingFile();
        if (containingFile.getName().endsWith(".gradle")) {
            try {
                Thread.sleep(0, 100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            /*if (!element.getText().equals("'org.springframework.boot:spring-boot-starter-web'")) {
                return;
            }*/
            if (element instanceof LeafPsiElement lpe && lpe.getElementType().toString().endsWith("quoted string")) {
                PsiElement parent = lpe.getParent();
                for (Predicate<PsiElement> pePredicate : PE_PREDICATES) {
                    if (pePredicate.test(parent)) {
                        parent = parent.getParent();
                    } else {
                        return;
                    }
                }
                if (PE_PREDICATE_FACTORY.apply("METHOD_CALL_EXPRESSION").test(parent)) {
                    if (parent.getFirstChild() != null && parent.getFirstChild().getFirstChild() != null &&
                            parent.getFirstChild().getFirstChild().getText().equals("dependencies")) {
                        System.out.println(element.getText());
                    }
                }
            }
            /*if (element.getText().equals("'org.springframework.boot:spring-boot-starter-web'")) {
                System.out.println("Ok");
            }*/
        }
    }
}
