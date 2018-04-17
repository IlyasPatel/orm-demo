package co.uk.orm.demo.d.loader.service;

import groovy.lang.Binding;

import java.util.HashMap;
import java.util.Map;

public class NamespacedBinding extends Binding {

    private Map<String, Object> namespacedVariables = new HashMap<String, Object>();

    @Override
    public void setVariable(String name, Object value) {
        namespacedVariables.put(name, value);

        super.setVariable(name, value);
    }

    public Map<String, Object> getNamespacedVariables() {
        return namespacedVariables;
    }
}
