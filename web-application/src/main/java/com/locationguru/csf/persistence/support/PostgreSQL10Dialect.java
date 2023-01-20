package com.locationguru.csf.persistence.support;

import org.hibernate.dialect.PostgreSQLDialect;
import org.hibernate.engine.jdbc.dialect.spi.DialectResolutionInfo;

/**
 * Workaround for using sequence with PostgreSQL 10 identity columns.
 */
public class PostgreSQL10Dialect
        extends PostgreSQLDialect {
    private static final String SEQUENCES_QUERY_STRING =
            "SELECT current_database() sequence_catalog, n.nspname AS sequence_schema, " +
                    "c.relname AS sequence_name, s.seqtypid::REGTYPE AS data_type, " +
                    "(information_schema._pg_numeric_precision(s.seqtypid, '-1'::INTEGER))::information_schema.CARDINAL_NUMBER AS numeric_precision, " +
                    "(2)::information_schema.CARDINAL_NUMBER AS numeric_precision_radix, " +
                    "(0)::information_schema.CARDINAL_NUMBER AS numeric_scale, " +
                    "(s.seqstart)::information_schema.CHARACTER_DATA AS start_value, " +
                    "(s.seqmin)::information_schema.CHARACTER_DATA AS minimum_value, " +
                    "(s.seqmax)::information_schema.CHARACTER_DATA AS maximum_value, " +
                    "(s.seqincrement)::information_schema.CHARACTER_DATA AS increment, " +
                    "(CASE WHEN s.seqcycle THEN 'YES'::TEXT ELSE 'NO'::TEXT END)::information_schema.YES_OR_NO AS cycle_option " +
                    "FROM pg_catalog.pg_sequence s JOIN pg_catalog.pg_class c ON s.seqrelid = c.oid " +
                    "LEFT JOIN pg_catalog.pg_namespace n ON n.oid = c.relnamespace " +
                    "WHERE (NOT pg_is_other_temp_schema(n.oid)) AND c.relkind = 'S';";

    /**
     * Required for correct initialization.
     */
    public PostgreSQL10Dialect(final DialectResolutionInfo info) {
        super(info);
    }

    @Override
    public String getQuerySequencesString() {
        return SEQUENCES_QUERY_STRING;
    }
}
