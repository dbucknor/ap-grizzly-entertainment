//package com.grizzly.server;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.grizzly.application.models.Employee;
//import com.grizzly.application.services.SerializeService;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.hibernate.HibernateException;
//import org.hibernate.query.Query;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//
////@RestController
//public class EmployeeREST implements IRestController<Employee, String> {
//    private final CRUDService<Employee, String> crud;
//    private final Logger logger = LogManager.getLogger(EmployeeREST.class);
//
//
//    @PostMapping(value = "/employee")
//    @Override
//    public boolean insertRecord(@RequestBody String record) {
//        try {
//            Employee r = (Employee) SerializeService.deserialize(record, EmployeeREST.class);
//            crud.insert(r);
//            logger.info("Record Inserted!");
//            return true;
//        } catch (HibernateException he) {
//            logger.error(he.getMessage());
//            throw he;
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);//TODO
//        }
//    }
//
//    @GetMapping("/employee/{id}")
//    @Override
//    public String getRecord(@PathVariable String id) {
//        try {
//            Employee record = crud.read(id);
//            return SerializeService.serialize(record);
//        } catch (HibernateException he) {
//            logger.error(he.getMessage());
//            throw he;
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);//TODO
//        }
//    }
//
//    @GetMapping("/employee")
//    @Override
//    public String getAllRecords() {
//        try {
//            List<Employee> users = crud.readAll();
//            ObjectMapper mapper = new ObjectMapper();
//            return mapper.writeValueAsString(users);
//        } catch (HibernateException he) {
//            logger.error(he.getMessage());
//            throw he;
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);//TODO
//        }
//    }
//
//    @GetMapping("/employee/query")
//    @Override
//    public List<Employee> getRecordsWhere(@RequestParam String query) {
//        try {
//            Query<Employee> q = (Query<Employee>) SerializeService.deserialize(query, Employee.class);
//            return crud.readWhere((s) -> q);
//        } catch (HibernateException he) {
//            logger.error(he.getMessage());
//            throw he;
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);//TODO
//        }
//    }
//
//    @PostMapping("/employee/update")
//    @Override
//    public boolean updateRecord(@RequestBody String record) {
//        try {
//            Employee r = (Employee) SerializeService.deserialize(record, Employee.class);
//            crud.update(r);
//            return true;
//        } catch (Exception he) {
//            logger.error(he.getMessage());
//            throw new RuntimeException(he);//TODO
//        }
//    }
//
//    @DeleteMapping("/employee/delete/{id}")
//    @Override
//    public boolean deleteRecord(@PathVariable String id) {
//        try {
//            crud.delete(id);
//            return true;
//        } catch (HibernateException he) {
//            logger.error(he.getMessage());
//            throw he;//TODO
//        }
//    }
//
//    @DeleteMapping("/employee/delete")
//    @Override
//    public boolean deleteRecordsWhere(@RequestParam String query) {
//        try {
//            Query<Employee> q = (Query<Employee>) SerializeService.deserialize(query, Employee.class);
//            List<Employee> toDelete = crud.readWhere((s) -> q);
//            for (Employee v : toDelete) {
//                crud.delete(v.getStaffId());
//            }
//            return true;
//        } catch (HibernateException he) {
//            logger.error(he.getMessage());
//            throw he;
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);//TODO
//        }
//    }
//
//    public EmployeeREST(Class<Employee> clazz) {
//        crud = new CRUDService<>(clazz);
//    }
//}
