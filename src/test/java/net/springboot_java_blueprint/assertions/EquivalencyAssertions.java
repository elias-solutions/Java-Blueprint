package net.springboot_java_blueprint.assertions;

import java.time.OffsetDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public final class EquivalencyAssertions {
    private static final String[] GENERATED_FIELDS = {"id", "version", "addresses.id", "addresses.version"};

    private EquivalencyAssertions() {
    }

    /**
     * Member-wise comparison that ignores database-generated identity (ids, versions)
     * and compares timestamps by instant rather than by offset.
     */
    public static void assertEquivalentIgnoringIdentity(Object actual, Object expected) {
        assertThat(actual)
                .usingRecursiveComparison()
                .ignoringFields(GENERATED_FIELDS)
                .withEqualsForType(OffsetDateTime::isEqual, OffsetDateTime.class)
                .isEqualTo(expected);
    }
}
