package ibemu.logbook.plugin.jsonviewer;

import javax.json.JsonValue;
import javax.json.JsonValue.ValueType;

import javafx.util.Pair;

public class NamedJsonValue extends Pair<String, JsonValue>
{
    /**
     *
     */
    private static final long serialVersionUID = -5935903972540868284L;

    public NamedJsonValue(String key, JsonValue value)
    {
        super(key, value);
    }

    @Override
    public String toString()
    {
        ValueType t = this.getValue().getValueType();
        if(t == ValueType.NUMBER || t == ValueType.STRING ||
                t == ValueType.NULL || t == ValueType.TRUE || t == ValueType.FALSE)
            return this.getKey() + " : " + this.getValue().toString();
        return this.getKey();
    }
}
