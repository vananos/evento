package io.github.vananos.evento.domain.lang;

import java.util.HashSet;
import java.util.Set;

class ValidatorVisitor implements ASTVisitor<RuleBuilder> {
    private final Set<String> usedNames = new HashSet<>();

    @Override
    public void visit(RuleBuilder node) {
        String name = node.getName();
        if (usedNames.contains(name)) {
            throw new IllegalStateException("Duplicated rule name found:" + name);
        }
        if ((node instanceof GroupRuleBuilderImpl) && node.getChildren().isEmpty()) {
            throw new IllegalStateException("Empty groups are not allowed, group: " + name);
        }
        usedNames.add(name);
    }
}
