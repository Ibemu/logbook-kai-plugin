package ibemu.logbook.plugin.widget;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import logbook.bean.Chara;
import logbook.bean.Ship;
import logbook.internal.Ships;
import logbook.internal.gui.InternalFXMLLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.stream.Stream;

public class SimpleBattleFleetPane extends GridPane
{
    @FXML
    private VBox name;

    @FXML
    private VBox beforeHp;

    @FXML
    private VBox arrowHp;

    @FXML
    private VBox afterHp;

    @FXML
    private VBox slashHp;

    @FXML
    private VBox maxHp;

    public SimpleBattleFleetPane(Stream<Chara> before)
    {
        try
        {
            FXMLLoader loader = InternalFXMLLoader.load("ibemu/logbook/plugin/widget/SimpleBattleFleetPane.fxml");
            loader.setRoot(this);
            loader.setController(this);
            loader.load();

            ObservableList<Node> nameList = this.name.getChildren();
            ObservableList<Node> beforeHpList = this.beforeHp.getChildren();
            ObservableList<Node> arrowHpList = this.arrowHp.getChildren();
            ObservableList<Node> slashHpList = this.slashHp.getChildren();
            ObservableList<Node> maxHpList = this.maxHp.getChildren();

            before.forEach(chara ->
            {
                if(chara == null) return;
                nameList.add(new Label(Ships.toName(chara)));
                Label l = new Label(chara.getNowhp().toString());
                if(chara instanceof Ship)
                    l.getStyleClass().add(getStyle((Ship) chara));
                else
                    l.getStyleClass().add(getStyle(chara));
                beforeHpList.add(l);
                arrowHpList.add(new Label("→"));
                slashHpList.add(new Label("/"));
                maxHpList.add(new Label(chara.getMaxhp().toString()));
            });
        }
        catch(IOException e)
        {
            LoggerHolder.LOG.error("FXMLのロードに失敗しました", e);
        }
    }

    private String getStyle(Ship chara)
    {
        if(Ships.isEscape(chara))
            return "escape";
        return getStyle((Chara) chara);
    }

    private String getStyle(Chara chara)
    {
        if(Ships.isLessThanSlightDamage(chara))
            return "normal";
        if(Ships.isSlightDamage(chara))
            return "slight-damage";
        if(Ships.isHalfDamage(chara))
            return "half-damage";
        if(Ships.isBadlyDamage(chara))
            return "badly-damage";
        if(Ships.isLost(chara))
            return "lost";
        return "lost";
    }

    public void applyAfterHp(Stream<Chara> after)
    {
        ObservableList<Node> afterHpList = this.afterHp.getChildren();
        after.forEach(chara ->
        {
            if(chara == null) return;
            Label l = new Label(chara.getNowhp().toString());
            if(chara instanceof Ship)
                l.getStyleClass().add(getStyle((Ship) chara));
            else
                l.getStyleClass().add(getStyle(chara));
            afterHpList.add(l);
        });
    }

    private static class LoggerHolder
    {
        /**
         * ロガー
         */
        private static final Logger LOG = LogManager.getLogger(SimpleBattleFleetPane.class);
    }
}
