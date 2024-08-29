package io.github.vcvitaly.depopener.resolver;

import com.intellij.psi.PsiElement;

/**
 * A resolver which takes a PSI element as input and checks if it's an argument of some configuration scope,
 * For example, for input <b>implementation 'org.springframework.boot:spring-boot-starter-web'</b> when the caret is placed at
 * the dependency description it wit should be resolved.
 *
 * An example of the expected structure is:
 * <pre><code>
 *     dependencies {
 *         implementation 'group.id:dependency-name:version'
 *     }
 * </code></pre>
 */
public interface ScopeArgumentResolver {
    boolean resolve(PsiElement pe);
}
