package io.github.vananos.evento.domain.lang;

import io.github.vananos.evento.domain.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static io.github.vananos.evento.domain.Constants.EVENT_MATCHER;
import static io.github.vananos.evento.domain.lang.ConcreteRuleBuilderImpl.when;
import static io.github.vananos.evento.domain.lang.GroupRuleBuilderImpl.group;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ValidatorVisitorTest {

    private static RuleBuilder<Event> RULE = when(EVENT_MATCHER).withName("sameName");
    private ValidatorVisitor validatorVisitor;

    @BeforeEach
    public void setUp() {
        validatorVisitor = new ValidatorVisitor();
        validatorVisitor.visit(RULE);
    }

    @ParameterizedTest
    @MethodSource("validatorTestCaseProviders")
    public void visit_shouldThrowExceptionIfValidationFailed(RuleBuilder<Event> ruleBuilder) {
        // then
        assertThrows(IllegalStateException.class, () -> {
            // when
            validatorVisitor.visit(ruleBuilder);
        });
    }

    private static Stream<Arguments> validatorTestCaseProviders() {
        return Stream.of(
                // duplicated names
                Arguments.of(RULE),
                // empty groups
                Arguments.of(group()));
    }

}