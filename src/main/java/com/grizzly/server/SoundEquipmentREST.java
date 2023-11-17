//package com.grizzly.server;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.grizzly.application.models.equipment.Sound;
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
//public class SoundEquipmentREST implements IRestController<Sound, String> {
//    private final CRUDService<Sound, String> crud;
//    private final Logger logger = LogManager.getLogger(SoundEquipmentREST.class);
//
//
//    @PostMapping(value = "/sound-equipment")
//    @Override
//    public boolean insertRecord(@RequestBody String record) {
//        try {
//            Sound r = (Sound) SerializeService.deserialize(record, Sound.class);
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
//    @GetMapping("/sound-equipment/{id}")
//    @Override
//    public String getRecord(@PathVariable String id) {
//        try {
//            Sound record = crud.read(id);
//            return SerializeService.serialize(record);
//        } catch (HibernateException he) {
//            logger.error(he.getMessage());
//            throw he;
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);//TODO
//        }
//    }
//
//    @GetMapping("/sound-equipments")
//    @Override
//    public String getAllRecords() {
//        try {
//            List<Sound> users = crud.readAll();
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
//    @GetMapping("/sound-equipments/query")
//    @Override
//    public List<Sound> getRecordsWhere(@RequestParam String query) {
//        try {
//            Query<Sound> q = (Query<Sound>) SerializeService.deserialize(query, Query.class);
//            return crud.readWhere((s) -> q);
//        } catch (HibernateException he) {
//            logger.error(he.getMessage());
//            throw he;
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);//TODO
//        }
//    }
//
//    @PostMapping("/sound-equipment/update")
//    @Override
//    public boolean updateRecord(@RequestBody String record) {
//        try {
//            Sound r = (Sound) SerializeService.deserialize(record, Sound.class);
//            crud.update(r);
//            return true;
//        } catch (Exception he) {
//            logger.error(he.getMessage());
//            throw new RuntimeException(he);//TODO
//        }
//    }
//
//    @DeleteMapping("/sound-equipment/delete/{id}")
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
//    @DeleteMapping("/sound-equipment/delete")
//    @Override
//    public boolean deleteRecordsWhere(@RequestParam String query) {
//        try {
//            Query<Sound> q = (Query<Sound>) SerializeService.deserialize(query, Query.class);
//            List<Sound> toDelete = crud.readWhere((s) -> q);
//            for (Sound v : toDelete) {
//                crud.delete(v.getEquipmentId());
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
//    public SoundEquipmentREST(Class<Sound> clazz) {
//        crud = new CRUDService<>(clazz);
//    }
//}
