package co.uk.orm.demo.d.loader.service;

import java.util.List;
import java.util.Map;

public interface Loader {

    Map<String, Object> persist(List<String> scripts);

    Map<String, Object> load(List<String> scripts);

}
