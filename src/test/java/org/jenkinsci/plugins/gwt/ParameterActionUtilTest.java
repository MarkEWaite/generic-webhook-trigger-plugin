package org.jenkinsci.plugins.gwt;

import static org.assertj.core.api.Assertions.assertThat;
import static org.jenkinsci.plugins.gwt.ParameterActionUtil.createParameterAction;

import com.google.common.collect.ImmutableMap;
import hudson.model.BooleanParameterDefinition;
import hudson.model.ParametersAction;
import hudson.model.ParametersDefinitionProperty;
import hudson.model.StringParameterDefinition;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class ParameterActionUtilTest {

    @Test
    void testThatStringKeepsItsDefaultValueWhenNoParameterSupplied() {
        final ParametersDefinitionProperty parametersDefinitionProperty = new ParametersDefinitionProperty(
                new StringParameterDefinition("name", "the default value") //
                );
        final Map<String, String> resolvedVariables = ImmutableMap.<String, String>builder() //
                .build();

        final ParametersAction actual = createParameterAction(parametersDefinitionProperty, resolvedVariables, true);

        assertThat(actual) //
                .hasSize(1);
        assertThat(actual.getAllParameters().get(0).getValue()) //
                .isEqualTo("the default value");
    }

    @Test
    void testThatStringDoesNotKeepItsDefaultValueWhenParameterSupplied() {
        final ParametersDefinitionProperty parametersDefinitionProperty = new ParametersDefinitionProperty(
                new StringParameterDefinition("name", "the default value") //
                );
        final Map<String, String> resolvedVariables = ImmutableMap.of( //
                "name", "this is supplied");

        final ParametersAction actual = createParameterAction(parametersDefinitionProperty, resolvedVariables, true);

        assertThat(actual) //
                .hasSize(1);
        assertThat(actual.getAllParameters().get(0).getValue()) //
                .isEqualTo("this is supplied");
    }

    @Test
    void testThatBooleanKeepsItsDefaultValueWhenNoParameterSupplied() {
        final ParametersDefinitionProperty parametersDefinitionProperty = new ParametersDefinitionProperty(
                new BooleanParameterDefinition("name", true, null) //
                );
        final Map<String, String> resolvedVariables = ImmutableMap.<String, String>builder() //
                .build();

        final ParametersAction actual = createParameterAction(parametersDefinitionProperty, resolvedVariables, true);

        assertThat(actual) //
                .hasSize(1);
        assertThat(actual.getAllParameters().get(0).getValue()) //
                .isEqualTo(true);
    }

    @Test
    void testThatBooleanDoesNotKeepItsDefaultValueWhenParameterSuppliedAndFalseIsFalse() {
        final ParametersDefinitionProperty parametersDefinitionProperty = new ParametersDefinitionProperty(
                new BooleanParameterDefinition("name", true, null) //
                );
        final Map<String, String> resolvedVariables = ImmutableMap.of( //
                "name", "false");

        final ParametersAction actual = createParameterAction(parametersDefinitionProperty, resolvedVariables, true);

        assertThat(actual) //
                .hasSize(1);
        assertThat(actual.getAllParameters().get(0).getValue()) //
                .isEqualTo(false);
    }

    @Test
    void testThatBooleanDoesNotKeepItsDefaultValueWhenParameterSuppliedAndTrueIsTrue() {
        final ParametersDefinitionProperty parametersDefinitionProperty = new ParametersDefinitionProperty(
                new BooleanParameterDefinition("name", true, null) //
                );
        final Map<String, String> resolvedVariables = ImmutableMap.of( //
                "name", "true");

        final ParametersAction actual = createParameterAction(parametersDefinitionProperty, resolvedVariables, true);

        assertThat(actual) //
                .hasSize(1);
        assertThat(actual.getAllParameters().get(0).getValue()) //
                .isEqualTo(true);
    }

    @Test
    void testThatUniqueParameterIsAddedWhenallowSeveralTriggersPerBuildFalse() {
        final ParametersDefinitionProperty parametersDefinitionProperty = new ParametersDefinitionProperty();
        final Map<String, String> resolvedVariables = new HashMap<>();

        final ParametersAction actualWithTrue =
                createParameterAction(parametersDefinitionProperty, resolvedVariables, true);

        assertThat(actualWithTrue) //
                .isEmpty();

        final ParametersAction actualWithFalse =
                createParameterAction(parametersDefinitionProperty, resolvedVariables, false);

        assertThat(actualWithFalse) //
                .hasSize(1);
        assertThat(actualWithFalse.getAllParameters().get(0).getValue()) //
                .isNotNull();
    }
}
