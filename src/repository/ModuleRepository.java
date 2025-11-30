package repository;

import model.Module;

public interface ModuleRepository {
    boolean save(Module module);
    boolean delete(String code);
    Module findByCode(String code);
}