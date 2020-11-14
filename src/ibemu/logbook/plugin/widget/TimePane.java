package ibemu.logbook.plugin.widget;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;
import logbook.internal.Time;
import logbook.internal.gui.InternalFXMLLoader;
import logbook.internal.gui.PopOver;
import logbook.internal.gui.PopOverPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Supplier;

public class TimePane extends GridPane
{
    /**
     * 色変化1段階目
     */
    private final Duration stage1 = Duration.ofMinutes(20);

    /**
     * 色変化2段階目
     */
    private final Duration stage2 = Duration.ofMinutes(10);

    /**
     * 色変化3段階目
     */
    private final Duration stage3 = Duration.ofMinutes(1);

    /**
     * 名前
     */
    private final String title;
    /**
     * 最大時間
     */
    private final Duration max;
    /**
     * 終了予定時刻
     */
    private final long finishTime;
    /**
     * 空きかどうか
     */
    private final boolean isEmpty;

    @FXML
    private ProgressBar progress;

    @FXML
    private Label time;

    private TimePane(String title, Duration max, long finishTime, boolean isEmpty)
    {
        this.title = title;
        this.max = max;
        this.finishTime = finishTime;
        this.isEmpty = isEmpty;
        try
        {
            FXMLLoader loader = InternalFXMLLoader.load("ibemu/logbook/plugin/widget/TimePane.fxml");
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        }
        catch(IOException e)
        {
            LoggerHolder.LOG.error("FXMLのロードに失敗しました", e);
        }
    }

    /**
     * 何もしない
     */
    public TimePane(String title)
    {
        this(title, null, 0, true);
    }

    /**
     * 遠征
     */
    public TimePane(String title, Duration max, long finishTime)
    {
        this(title, max, finishTime, false);
    }

    /**
     * 入渠
     */
    public TimePane(String title, long finishTime)
    {
        this(title, null, finishTime, false);
    }

    @FXML
    void initialize()
    {
        try
        {
            this.update();
            // マウスオーバーでのポップアップ
            PopOver<Object> popover = new PopOver<>((node, a) ->
            {
                String message;
                if(this.isEmpty)
                {
                    message = "空いています";
                }
                else
                {
                    ZonedDateTime dateTime = ZonedDateTime.ofInstant(Instant.ofEpochMilli(this.finishTime), ZoneOffset.systemDefault());
                    if(dateTime.toLocalDate().equals(ZonedDateTime.now().toLocalDate()))
                    {
                        message = "今日 " + DateTimeFormatter.ofPattern("H時m分s秒").format(dateTime)
                                + " 頃に完了します";
                    }
                    else
                    {
                        message = DateTimeFormatter.ofPattern("M月d日 H時m分s秒").format(dateTime)
                                + " 頃に完了します";
                    }
                }
                return new PopOverPane(this.title, message);
            });
            popover.install(this, null);
        }
        catch(Exception e)
        {
            LoggerHolder.LOG.error("FXMLの初期化に失敗しました", e);
        }
    }

    /**
     * 画面を更新します
     */
    public void update()
    {
        ObservableList<String> styleClass = this.getStyleClass();
        styleClass.removeAll("stage1", "stage2", "stage3", "empty");

        if(this.isEmpty)
        {
            // 何もしていない
            this.progress.setProgress(0);
            this.progress.setVisible(false);
            this.time.setText("<空き>");
            styleClass.add("empty");
        }
        else
        {
            // 残り時間を計算
            Duration now = Duration.ofMillis(this.finishTime - System.currentTimeMillis());

            if(max != null && !max.isZero())
            {
                double p = (double) (max.toMillis() - now.toMillis()) / (double) max.toMillis();
                this.progress.setProgress(p);
            }
            else
            {
                this.progress.setProgress(1);
            }
            // 残り時間を更新
            this.time.setText(Time.toString(now, "完了"));

            // スタイルを更新
            if(now.compareTo(this.stage3) < 0)
            {
                styleClass.add("stage3");
            }
            else if(now.compareTo(this.stage2) < 0)
            {
                styleClass.add("stage2");
            }
            else if(now.compareTo(this.stage1) < 0)
            {
                styleClass.add("stage1");
            }
        }
    }

    private static class LoggerHolder
    {
        /**
         * ロガー
         */
        private static final Logger LOG = LogManager.getLogger(TimePane.class);
    }
}
