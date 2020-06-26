package io.github.vananos.evento.domain.lang;

public class DefaultsVisitor implements ASTVisitor<RuleBuilder> {
    private int ruleCounter;
    private int groupCounter;

    @Override
    public void visit(RuleBuilder node) {
        if (node.getName() == null) {
            String defaultName = (node instanceof ConcreteRuleBuilderImpl) ? "Rule #" + (++ruleCounter) : "Group #" + (++groupCounter);
            node.withName(defaultName);
        }
    }
}
