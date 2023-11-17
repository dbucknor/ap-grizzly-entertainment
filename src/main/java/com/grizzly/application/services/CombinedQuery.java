package com.grizzly.application.services;

import org.hibernate.Session;
import org.hibernate.query.Query;

import java.io.Serializable;
import java.util.HashMap;

public class CombinedQuery<T> implements Serializable {
    private Query<T> query;
    private String queryString;
    HashMap<String, Object> values;

    public CombinedQuery(String clause) {
        this.queryString = clause;
        this.values = new HashMap<>();
    }

    public CombinedQuery<T> where(String param, String comparison, Object value) {
        queryString = queryString + " WHERE " + param + " " + comparison;
        values.put(comparison.split(":")[1], value);
        return this;
    }

    public CombinedQuery<T> like(String param, String comparison, Object value) {
        queryString = queryString + " WHERE " + param + " " + comparison.trim() + "LIKE";
        values.put(comparison.split(":")[1] + "LIKE", value);
        return this;
    }

    public CombinedQuery<T> orderBy(String field, String order) {
        queryString = queryString + " ORDER BY " + field + " " + order;
        return this;
    }

    private void generateQuery(Session session) {
        query = session.createQuery(queryString);

        for (String key :
                values.keySet()) {
            if (key.contains("LIKE")) {
                query.setParameter(key, "%" + values.get(key) + "%");
            } else {
                query.setParameter(key, values.get(key));
            }
        }
    }

    public Query<T> getQuery(Session session) {
        generateQuery(session);
        return query;
    }
}
