package mx.com.villavicencio.commons.jdbc;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

/**
 *
 * @author Gabriel J
 */
public final class Jdbc {

    public static final SimpleJdbcInsert getSimpleJdbcInsert(JdbcTemplate jdbcTemplate, String table, String generateKey) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        simpleJdbcInsert.setTableName(table);
        simpleJdbcInsert.setGeneratedKeyName(generateKey);
        return simpleJdbcInsert;
    }

    public static final SimpleJdbcInsert getSimpleJdbcInsert(JdbcTemplate jdbcTemplate, String table) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        simpleJdbcInsert.setTableName(table);
        return simpleJdbcInsert;
    }

    public static final SimpleJdbcCall getSimpleJdbcCall(JdbcTemplate jdbcTemplate, String procedureName) {
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate);
        simpleJdbcCall.setProcedureName(procedureName);
        simpleJdbcCall.compile();
        return simpleJdbcCall;
    }

    public static final SimpleJdbcCall getSimpleJdbcCallFunction(JdbcTemplate jdbcTemplate,String functionName){
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate);
        simpleJdbcCall.setFunction(true);
        simpleJdbcCall.setProcedureName(functionName);
        simpleJdbcCall.compile();
        return simpleJdbcCall;
    }
}
