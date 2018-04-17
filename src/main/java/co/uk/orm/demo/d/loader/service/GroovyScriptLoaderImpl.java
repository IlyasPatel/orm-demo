package co.uk.orm.demo.d.loader.service;

import groovy.lang.Binding;
import groovy.lang.Closure;
import groovy.lang.GroovyShell;
import org.apache.commons.io.IOUtils;

import javax.persistence.EntityManager;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class GroovyScriptLoaderImpl implements Loader {

    private String scriptDirectory;
    private Stack<ScriptContext> scriptContextStack = new Stack<>();
    private EntityManager entityManager;

    private boolean doPersist = true;

    public GroovyScriptLoaderImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Map<String, Object> load(List<String> scripts) {
        this.doPersist = false;

        beginTransaction();

        Map<String, Object> data = loadScripts(scripts);

        commitTransaction();

        return data;
    }

    @Override
    public Map<String, Object> persist(List<String> scripts) {
        this.doPersist = true;
        beginTransaction();

        Map<String, Object> data = loadScripts(scripts);

        commitTransaction();

        return data;
    }

    private void commitTransaction() {
        entityManager.getTransaction().commit();
        //entityManager.close();
    }

    private void beginTransaction() {
        entityManager.getTransaction().begin();
    }

    private Map<String, Object> loadScripts(List<String> scripts) {
        Map<String, Object> variables = new HashMap<>();

        for (String script : scripts) {
            NamespacedBinding binding = create();
            // Bind inputs.
            for (String key : variables.keySet()) {
                binding.setVariable(key, variables.get(key));
            }

            try (BufferedReader stream = new BufferedReader(new InputStreamReader(new FileInputStream(script), "UTF8"))) {
                List<String> lines = IOUtils.readLines(stream);
                StringBuilder builder = new StringBuilder();
                for (String line : lines) {
                    builder.append(line).append("\n");
                }
                execute(builder.toString(), binding);
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage(), e);
            } finally{
                System.out.println("");
            }

            for (String key : binding.getNamespacedVariables().keySet()) {
                if (!BindingKeys.isExists(key)) {
                    variables.put(key, binding.getNamespacedVariables().get(key));
                }
            }
        }

        return variables;
    }

    private NamespacedBinding create() {
        Closure<List<Object>> table = new Closure<List<Object>>(this) {
            @SuppressWarnings("unchecked")
            @Override
            public List<Object> call(Object... args) {
                if (args == null || args.length != 3) {
                    throw new RuntimeException("The table method expects JPA entity class reference, "
                            + "list of bean properties and a closure");
                }
                return invokeWithClosure((Class<?>) args[0], (List<String>) args[1], (Closure<Void>) args[2]);
            }
        };

        Closure<Object> row = new Closure<Object>(this) {
            @Override
            public Object call(Object... args) {
                return invokeRowClosure(args);
            }
        };

        Closure<Object> defaultRow = new Closure<Object>(this) {

            @SuppressWarnings("unchecked")
            @Override
            public Object call(Object... args) {
                invokeDefaultRowClosure((Closure<Object>) args[0]);
                return null;
            }
        };

        Closure<Object> find = new Closure<Object>(this) {

            @SuppressWarnings("unchecked")
            @Override
            public Object call(Object... args) {
                return invokeFindClosure((Class<? extends Serializable>) args[0], (Serializable) args[1]);
            };
        };

        Closure<Object> flush = new Closure<Object>(this) {

            @Override
            public Object call(Object... args) {
                if (doPersist) {
                    entityManager.flush();
                }
                return null;
            };
        };

        NamespacedBinding binding = new NamespacedBinding();
        binding.setVariable("table", table);
        binding.setVariable("row", row);
        binding.setVariable("defaultRow", defaultRow);
        binding.setVariable("find", find);
        binding.setVariable("flush", flush);

        return binding;
    }

    private <V> List<Object> invokeWithClosure(Class<?> clz, List<String> attributes, Closure<V> callable) {
        ScriptContext context = new ScriptContext();
        context.setEntityClass(clz);
        context.setAttributes(attributes);

        scriptContextStack.push(context);

        callable.call();

        scriptContextStack.pop();
        return context.getCreatedEntities();
    }

    private Object invokeRowClosure(Object... attributeValues) {
        Serializable instance = instantiate();
        DelegatingGroovyObjectSupport<Serializable> delegate = new DelegatingGroovyObjectSupport<>(instance);

        for (int i = 0; i < scriptContextStack.peek().getAttributes().size(); i++) {
            delegate.setProperty(scriptContextStack.peek().getAttributes().get(i), attributeValues[i]);
        }

        scriptContextStack.peek().getDefaultRowClosure().call(instance);

        if (doPersist) {
            entityManager.persist(instance);
            entityManager.flush();
            entityManager.clear();
        }

        scriptContextStack.peek().getCreatedEntities().add(instance);

        return instance;
    }

    private void invokeDefaultRowClosure(Closure<Object> closure) {
        scriptContextStack.peek().setDefaultRowClosure(closure);
    }

    private Object invokeFindClosure(Class<? extends Serializable> clz, Serializable id) {
        return entityManager.find(clz, id);
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> execute(String script, Binding binding) {
        GroovyShell shell = new GroovyShell(getClass().getClassLoader(), binding);
        shell.evaluate(script);
        return binding.getVariables();
    }

    private Serializable instantiate() {
        try {
            return (Serializable) scriptContextStack.peek().getEntityClass().newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
