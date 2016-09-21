package ibemu.logbook.plugin.newship.api;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;

import org.controlsfx.control.Notifications;

import javafx.application.Platform;
import javafx.geometry.Pos;
import logbook.api.API;
import logbook.api.APIListenerSpi;
import logbook.bean.Ship;
import logbook.bean.ShipMst;
import logbook.bean.ShipMstCollection;
import logbook.proxy.RequestMetaData;
import logbook.proxy.ResponseMetaData;

@API({"/kcsapi/api_port/port"})
public class ApiPortPort implements APIListenerSpi
{
    private int lastMaxId = -1;

    @Override
    public void accept(JsonObject json, RequestMetaData req, ResponseMetaData res)
    {
        JsonArray apiship = json.getJsonObject("api_data").getJsonArray("api_ship");
        int max = -1;
        if(lastMaxId < 0) {
            for (JsonValue value : apiship) {
                if (value instanceof JsonObject) {
                    JsonObject ship = (JsonObject) value;
                    Ship s = Ship.toShip(ship);
                    if(s.getId() > max) max = s.getId();
                }
            }
        }
        else {
            Set<ShipMst> oldShips = new HashSet<ShipMst>();    //前からいたやつ
            Set<ShipMst> newShips = new HashSet<ShipMst>();    //今できたやつ
            for (JsonValue value : apiship) {
                if (value instanceof JsonObject) {
                    JsonObject ship = (JsonObject) value;
                    Ship s = Ship.toShip(ship);
                    if(s.getId() > max) max = s.getId();
                    ShipMst mst = ShipMstCollection.get().getShipMap().get(s.getShipId());
                    if(s.getId() > lastMaxId) newShips.add(mst);
                    else oldShips.add(mst);
                }
            }
            newShips.removeIf(new NewShipSearcher(oldShips));
            if(!newShips.isEmpty())
                showNotify("新規艦娘", "母港に新しい艦娘が着任しました。\n"
                        + String.join(", ", newShips.stream().map(x -> x.getName()).toArray(String[]::new)));
        }
        lastMaxId = max;
    }

    private void showNotify(String title, String message) {
        Platform.runLater(() ->
            Notifications.create()
                    .title(title)
                    .text(message)
                    .position(Pos.BOTTOM_RIGHT)
                    .showInformation());
    }

    private class NewShipSearcher implements Predicate<ShipMst>
    {
        private final Set<ShipMst> oldShips;

        public NewShipSearcher(Set<ShipMst> oldShips)
        {
            this.oldShips = oldShips;
        }

        @Override
        public boolean test(ShipMst e)
        {
            if(oldShips.contains(e.getId())) return true;
            for(ShipMst o : oldShips)
                if(compare(e, o)) return true;
            return false;
        }

        private boolean compare(ShipMst a, ShipMst b)
        {
            if (a.getId() == b.getId())
                return true;
            Set<Integer> afters = new HashSet<>();
            afters.add(b.getId());
            int after = b.getAftershipid();
            while ((after != 0) && !afters.contains(after)) {
                if (a.getId() == after)
                    return true;
                afters.add(after);
                after = ShipMstCollection.get().getShipMap().get(after).getAftershipid();
            }
            afters.clear();
            after = a.getAftershipid();
            while ((after != 0) && !afters.contains(after)) {
                if (b.getId() == after)
                    return true;
                afters.add(after);
                after = ShipMstCollection.get().getShipMap().get(after).getAftershipid();
            }
            return false;
        }
    }
}
