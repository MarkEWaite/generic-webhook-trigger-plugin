package org.jenkinsci.plugins.gwt;

import static org.assertj.core.api.Assertions.assertThat;
import static org.jenkinsci.plugins.gwt.GenericTrigger.HEADER_DRY_RUN;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.junit.jupiter.api.Test;

class GenericTriggerTest {

    @Test
    void testDryRun() {
        assertThat(GenericTrigger.isDryRun(null)).isFalse();

        final Map<String, List<String>> headers = new TreeMap<>();
        assertThat(GenericTrigger.isDryRun(headers)).isFalse();

        headers.put("whatever", null);
        assertThat(GenericTrigger.isDryRun(headers)).isFalse();

        final List<String> headerValues = List.of("true");
        headers.put("whatever", headerValues);
        assertThat(GenericTrigger.isDryRun(headers)).isFalse();

        headers.put(HEADER_DRY_RUN, null);
        assertThat(GenericTrigger.isDryRun(headers)).isFalse();

        List<String> dryRunHeaderValues = List.of("false");
        headers.put(HEADER_DRY_RUN, dryRunHeaderValues);
        assertThat(GenericTrigger.isDryRun(headers)).isFalse();

        dryRunHeaderValues = List.of("x");
        headers.put(HEADER_DRY_RUN, dryRunHeaderValues);
        assertThat(GenericTrigger.isDryRun(headers)).isFalse();

        dryRunHeaderValues = List.of("true");
        headers.put(HEADER_DRY_RUN, dryRunHeaderValues);
        assertThat(GenericTrigger.isDryRun(headers)).isTrue();
    }
}
