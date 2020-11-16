package ibemu.logbook.plugin.jsonviewer;

import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;
import javax.json.JsonValue.ValueType;

public class JsonTreeItem extends TreeItem<NamedJsonValue>
{
    public JsonTreeItem(final NamedJsonValue value)
    {
        super(value);
        this.setExpanded(true);
    }

    @Override
    public boolean isLeaf()
    {
        ValueType t = this.getValue().getValue().getValueType();
        return t == ValueType.NUMBER || t == ValueType.STRING ||
                t == ValueType.NULL || t == ValueType.TRUE || t == ValueType.FALSE;
    }

    @Override
    public ObservableList<TreeItem<NamedJsonValue>> getChildren()
    {
        ObservableList<TreeItem<NamedJsonValue>> children = super.getChildren();
        // すでに作ってあるときはそのまま返す
        if(!children.isEmpty()) return children;

        JsonValue value = this.getValue().getValue();

        switch(value.getValueType())
        {
        case NUMBER:
        case STRING:
        case NULL:
        case TRUE:
        case FALSE:
            // 子要素なし
            break;
        case ARRAY:
            // 配列
            JsonArray a = (JsonArray) value;
            for(int i = 0; i < a.size(); ++i)
                children.add(new JsonTreeItem(new NamedJsonValue("[" + i + "]", a.get(i))));
            break;
        case OBJECT:
            // オブジェクト
            JsonObject o = (JsonObject) value;
            o.forEach((k, v) -> children.add(new JsonTreeItem(new NamedJsonValue(k, v))));
            break;
        }
        return children;
    }
}
