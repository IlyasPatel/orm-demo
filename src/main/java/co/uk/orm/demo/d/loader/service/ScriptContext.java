package co.uk.orm.demo.d.loader.service;

import groovy.lang.Closure;

import java.util.ArrayList;
import java.util.List;

public class ScriptContext {
    private Class<?> entityClass;
    private List<String> attributes;
    private List<Object> createdEntities = new ArrayList<>();

    private Closure<Object> defaultRowClosure = new Closure<Object>(this) {
        @Override
        public Object call(Object argument) {
            return argument;
        }
    };

    public Class<?> getEntityClass() {
        return entityClass;
    }

    public void setEntityClass(Class<?> entityClass) {
        this.entityClass = entityClass;
    }

    public List<String> getAttributes() {
        return attributes;
    }


    public void setAttributes(List<String> attributes) {
        this.attributes = attributes;
    }

    public List<Object> getCreatedEntities() {
        return createdEntities;
    }

    public Closure<Object> getDefaultRowClosure() {
        return defaultRowClosure;
    }

    public void setDefaultRowClosure(Closure<Object> defaultRowClosure) {
        this.defaultRowClosure = defaultRowClosure;
    }
}
