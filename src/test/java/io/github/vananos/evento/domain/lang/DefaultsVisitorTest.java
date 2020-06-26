package io.github.vananos.evento.domain.lang;

import io.github.vananos.evento.domain.Event;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static io.github.vananos.evento.domain.Constants.EVENT_MATCHER;
import static io.github.vananos.evento.domain.lang.ConcreteRuleBuilderImpl.when;
import static io.github.vananos.evento.domain.lang.GroupRuleBuilderImpl.groupOf;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

class DefaultsVisitorTest {

    @Test
    public void visit_shouldFillDefaults() {
        // given
        Collection<RuleBuilder<Event>> ruleBuilders = asList(
                when(EVENT_MATCHER).withName(null),
                groupOf(Event.class).withName(null),
                when(EVENT_MATCHER).withName("custom name should not disappear"),
                when(EVENT_MATCHER).withName(null),
                groupOf(Event.class).withName(null));
        // and
        Collection<String> expectedNames = asList(
                "Rule #1",
                "Group #1",
                "custom name should not disappear",
                "Rule #2",
                "Group #2");
        // and
        DefaultsVisitor visitor = new DefaultsVisitor();
        // when
        ruleBuilders.forEach(visitor::visit);
        // and
        Collection<String> actualNames = ruleBuilders.stream()
                .map(RuleBuilder::getName)
                .collect(toList());
        // then
        assertThat(actualNames).isEqualTo(expectedNames);

    }
}