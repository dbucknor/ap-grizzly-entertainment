package com.grizzly.application.services;

import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.HashMap;

public class CombinedQuery<T> {
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

    private void generateQuery(Session session) {
        query = session.createQuery(queryString);
        System.out.println(queryString);
        System.out.println(values);
        for (String key :
                values.keySet()) {
            query.setParameter(key, values.get(key));
        }
    }

    public Query<T> get(Session session) {
        generateQuery(session);
        return query;
    }
}
