package com.grizzly.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.grizzly.application.models.User;
import com.grizzly.application.services.CRUDService;
import com.grizzly.application.services.SerializeService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.query.Query;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@org.springframework.web.bind.annotation.RestController
public class UserREST implements IRestController<User, String> {
    private final CRUDService<User, String> crud;
    private final Logger logger = LogManager.getLogger(UserREST.class);


    @PostMapping(value = "/user")
    @Override
    public boolean insertRecord(@RequestBody String record) {
        try {
            User user = (User) SerializeService.deserialize(record, User.class);
            crud.insert(user);
            logger.info("Record Inserted!");
            return true;
        } catch (HibernateException he) {
            logger.error(he.getMessage());
            throw he;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/user/{id}")
    @Override
    public String getRecord(@PathVariable String id) {
        try {
            User u = new User(crud.read(id));
            return SerializeService.serialize(u);
        } catch (HibernateException he) {
            logger.error(he.getMessage());
            throw he;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/users")
    @Override
    public String getAllRecords() {
        try {
            List<User> users = crud.readALL();
            return SerializeService.serialize(users);
        } catch (HibernateException he) {
            logger.error(he.getMessage());
            throw he;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/users/query")
    @Override
    public List<User> getRecordsWhere(@RequestParam String query) {
        try {
            Query<User> q = (Query<User>) SerializeService.deserialize(query, Query.class);
            return crud.readWhere((s) -> q);
        } catch (HibernateException he) {
            logger.error(he.getMessage());
            throw he;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/user/update")
    @Override
    public boolean updateRecord(@RequestBody String record) {
        try {
            User user = (User) SerializeService.deserialize(record, User.class);
            crud.update(user);
            return true;
        } catch (Exception he) {
            logger.error(he.getMessage());
            throw new RuntimeException(he);
        }
    }

    @GetMapping("/user/delete/{id}")
    @Override
    public boolean deleteRecord(@PathVariable String id) {
        try {
            crud.delete(id);
            return true;
        } catch (HibernateException he) {
            logger.error(he.getMessage());
            throw he;
        }
    }

    @GetMapping("/user/delete")
    @Override
    public boolean deleteRecordsWhere(@RequestParam String query) {
        try {
            Query<User> q = (Query<User>) SerializeService.deserialize(query, Query.class);
            List<User> toDelete = crud.readWhere((s) -> q);
            for (User v : toDelete) {
                crud.delete(v.getUserId());
            }
            return true;
        } catch (HibernateException | JsonProcessingException he) {
            logger.error(he.getMessage());
            throw new RuntimeException(he);
        }
    }

    public UserREST(Class<User> clazz) {
        crud = new CRUDService<>(clazz);
    }
}
