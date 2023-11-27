package project.grizzly.application.services;

import org.hibernate.Session;
import org.hibernate.query.Query;

import java.io.Serializable;
import java.util.HashMap;

public class CombinedQuery<T> implements Serializable {

    private transient Query<T> query;
    private String queryString;
    private HashMap<String, Object> values;

    public CombinedQuery(String hql) {
        this.queryString = hql;
        this.values = new HashMap<>();
    }

    public CombinedQuery<T> where(String param, String comparison, Serializable value) {
        queryString = queryString + " WHERE " + param + " " + comparison;
        values.put(comparison.split(":")[1], value);
        return this;
    }

    public CombinedQuery<T> like(String param, String comparison, Serializable value) {
        queryString = queryString + " WHERE " + param + " " + comparison.trim() + "LIKE";
        values.put(comparison.split(":")[1] + "LIKE", value);
        return this;
    }

    public CombinedQuery<T> orderBy(String field, String order) {
        queryString = queryString + " ORDER BY " + field + " " + order;
        return this;
    }

    private void generateQuery(Session session) {
        try {
            query = (Query<T>) session.createQuery(queryString);
        } catch (Exception e) {
            e.printStackTrace();
        }


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

    @Override
    public String toString() {
        return "CombinedQuery{" +
                "query=" + query +
                ", queryString='" + queryString + '\'' +
                ", values=" + values +
                '}';
    }
}
