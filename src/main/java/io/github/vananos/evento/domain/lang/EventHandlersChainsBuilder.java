package io.github.vananos.evento.domain.lang;

import io.github.vananos.evento.domain.matchers.EventMatcher;

import java.util.*;

import static java.util.Collections.unmodifiableCollection;
import static java.util.stream.Collectors.toList;

public class EventHandlersChainsBuilder implements ASTVisitor<RuleBuilder> {
    private final Map<RuleBuilder, RuleBuilder> parentsMap = new HashMap<>();
    private final Collection<RuleBuilder> leafs = new ArrayList<>();

    @Override
    public void visit(RuleBuilder node) {
        if (node.getChildren().isEmpty()) {
            leafs.add(node);
            return;
        }
        Collection<RuleBuilder> children = node.getChildren();
        children.forEach(child -> parentsMap.put(child, node));
    }

    public Collection<EventHandlersChain> getEventHandlersChains() {
        return unmodifiableCollection(
                leafs.stream()
                        .map(this::buildChainStartingFromTheLeaf)
                        .collect(toList()));

    }

    private EventHandlersChain buildChainStartingFromTheLeaf(RuleBuilder node) {
        Deque<EventHandler<?>> allHandlers = new LinkedList<>();
        for (RuleBuilder current = node; current != null; current = parentsMap.get(current)) {
            List<EventHandler<?>> eventHandlers = current.getEventHandlers();
            for (int i = eventHandlers.size() - 1; i >= 0; i--) {
                allHandlers.addFirst(eventHandlers.get(i));
            }
        }
        EventMatcher matcher = ((ConcreteRuleBuilderImpl) node).getEventMatcher();
        return new EventHandlersChain(matcher, allHandlers);
    }
}
