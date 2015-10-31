package net.gnomeffinway.depenizen.support.bungee;

import net.aufdemrand.denizencore.utilities.DefinitionProvider;

import java.util.Map;

public class DefinitionsWrapper implements DefinitionProvider {

    private final Map<String, String> definitions;

    public DefinitionsWrapper(Map<String, String> definitions) {
        this.definitions = definitions;
    }

    @Override
    public void addDefinition(String definition, String value) {
        this.definitions.put(definition, value);
    }

    @Override
    public Map<String, String> getAllDefinitions() {
        return this.definitions;
    }

    @Override
    public String getDefinition(String definition) {
        if (definition == null) {
            return null;
        }
        return this.definitions.get(definition);
    }

    @Override
    public boolean hasDefinition(String definition) {
        return this.definitions.containsKey(definition);
    }

    @Override
    public void removeDefinition(String definition) {
        this.definitions.remove(definition);
    }
}
