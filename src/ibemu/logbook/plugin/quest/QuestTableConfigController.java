package ibemu.logbook.plugin.quest;

import ibemu.logbook.plugin.Config;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import logbook.internal.ThreadManager;
import logbook.internal.gui.WindowController;

public class QuestTableConfigController extends WindowController
{
    /** api_no */
    @FXML
    private CheckBox no;

//    /** api_category */
//    @FXML
//    private CheckBox category;
//
//    /** api_type */
//    @FXML
//    private CheckBox type;

    /** api_state */
    @FXML
    private CheckBox state;

    /** api_title */
    @FXML
    private CheckBox title;

    /** api_detail */
    @FXML
    private CheckBox detail;

    /** api_get_material[0] */
    @FXML
    private CheckBox fuel;

    /** api_get_material[1] */
    @FXML
    private CheckBox ammo;

    /** api_get_material[2] */
    @FXML
    private CheckBox metal;

    /** api_get_material[3] */
    @FXML
    private CheckBox bauxite;

//    /** api_bonus_flag */
//    @FXML
//    private CheckBox bonusFlag;
//
//    /** api_progress_flag */
//    @FXML
//    private CheckBox progressFlag;

    /** 期限 */
    @FXML
    private CheckBox due;

    /** 出撃 */
    @FXML
    private CheckBox sortie;

    /** ボス到達 */
    @FXML
    private CheckBox bossArrive;

    /** ボス勝利 */
    @FXML
    private CheckBox bossWin;

    /** 1-4ボスS */
    @FXML
    private CheckBox boss1_4WinS;

    /** 1-5ボスA */
    @FXML
    private CheckBox boss1_5WinA;

    /** 南西ボス */
    @FXML
    private CheckBox boss2Win;

    /** 3-3+ボス */
    @FXML
    private CheckBox boss3_3pWin;

    /** 西方ボス */
    @FXML
    private CheckBox boss4Win;

    /** 4-4ボス */
    @FXML
    private CheckBox boss4_4Win;

    /** 5-2ボスS */
    @FXML
    private CheckBox boss5_2WinS;

    /** 6-1ボスS */
    @FXML
    private CheckBox boss6_1WinS;

    /** 戦闘勝利 */
    @FXML
    private CheckBox battleWin;

    /** 戦闘Sランク */
    @FXML
    private CheckBox battleWinS;

    /** 補給艦撃破 */
    @FXML
    private CheckBox defeatAP;

    /** 空母撃破 */
    @FXML
    private CheckBox defeatCV;

    /** 潜水艦撃破 */
    @FXML
    private CheckBox defeatSS;

    /** 演習 */
    @FXML
    private CheckBox practice;

    /** 演習勝利 */
    @FXML
    private CheckBox practiceWin;

    /** 遠征成功 */
    @FXML
    private CheckBox missionSuccess;

    /** 建造 */
    @FXML
    private CheckBox createShip;

    /** 開発 */
    @FXML
    private CheckBox createItem;

    /** 解体 */
    @FXML
    private CheckBox destroyShip;

    /** 廃棄 */
    @FXML
    private CheckBox destroyItem;

    /** 補給 */
    @FXML
    private CheckBox charge;

    /** 入渠 */
    @FXML
    private CheckBox repair;

    /** 近代化改修 */
    @FXML
    private CheckBox powerUp;

    @FXML
    void initialize() {
        QuestTableConfig conf = QuestTableConfig.get();

        this.no.setSelected(conf.isNo());
//        this.category.setSelected(conf.isCategory());
//        this.type.setSelected(conf.isType());
        this.state.setSelected(conf.isState());
        this.title.setSelected(conf.isTitle());
        this.detail.setSelected(conf.isDetail());
        this.fuel.setSelected(conf.isFuel());
        this.ammo.setSelected(conf.isAmmo());
        this.metal.setSelected(conf.isMetal());
        this.bauxite.setSelected(conf.isBauxite());
//        this.bonusFlag.setSelected(conf.isBonusFlag());
//        this.progressFlag.setSelected(conf.isProgressFlag());
        this.due.setSelected(conf.isDue());
        this.sortie.setSelected(conf.isSortie());
        this.bossArrive.setSelected(conf.isBossArrive());
        this.bossWin.setSelected(conf.isBossWin());
        this.boss1_4WinS.setSelected(conf.isBoss1_4WinS());
        this.boss1_5WinA.setSelected(conf.isBoss1_5WinA());
        this.boss2Win.setSelected(conf.isBoss2Win());
        this.boss3_3pWin.setSelected(conf.isBoss3_3pWin());
        this.boss4Win.setSelected(conf.isBoss4Win());
        this.boss4_4Win.setSelected(conf.isBoss4_4Win());
        this.boss5_2WinS.setSelected(conf.isBoss5_2WinS());
        this.boss6_1WinS.setSelected(conf.isBoss6_1WinS());
        this.battleWin.setSelected(conf.isBattleWin());
        this.battleWinS.setSelected(conf.isBattleWinS());
        this.defeatAP.setSelected(conf.isDefeatAP());
        this.defeatCV.setSelected(conf.isDefeatCV());
        this.defeatSS.setSelected(conf.isDefeatSS());
        this.practice.setSelected(conf.isPractice());
        this.practiceWin.setSelected(conf.isPracticeWin());
        this.missionSuccess.setSelected(conf.isMissionSuccess());
        this.createShip.setSelected(conf.isCreateShip());
        this.createItem.setSelected(conf.isCreateItem());
        this.destroyShip.setSelected(conf.isDestroyShip());
        this.destroyItem.setSelected(conf.isDestroyItem());
        this.charge.setSelected(conf.isCharge());
        this.repair.setSelected(conf.isRepair());
        this.powerUp.setSelected(conf.isPowerUp());
    }

    /**
     * キャンセル
     *
     * @param event ActionEvent
     */
    @FXML
    void cancel(ActionEvent event) {
        this.getWindow().close();
    }

    /**
     * 設定の反映
     *
     * @param event ActionEvent
     */
    @FXML
    void ok(ActionEvent event) {
        QuestTableConfig conf = QuestTableConfig.get();

        conf.setNo(this.no.isSelected());
//        conf.setCategory(this.category.isSelected());
//        conf.setType(this.type.isSelected());
        conf.setState(this.state.isSelected());
        conf.setTitle(this.title.isSelected());
        conf.setDetail(this.detail.isSelected());
        conf.setFuel(this.fuel.isSelected());
        conf.setAmmo(this.ammo.isSelected());
        conf.setMetal(this.metal.isSelected());
        conf.setBauxite(this.bauxite.isSelected());
//        conf.setBonusFlag(this.bonusFlag.isSelected());
//        conf.setProgressFlag(this.progressFlag.isSelected());
        conf.setDue(this.due.isSelected());
        conf.setSortie(this.sortie.isSelected());
        conf.setBossArrive(this.bossArrive.isSelected());
        conf.setBossWin(this.bossWin.isSelected());
        conf.setBoss1_4WinS(this.boss1_4WinS.isSelected());
        conf.setBoss1_5WinA(this.boss1_5WinA.isSelected());
        conf.setBoss2Win(this.boss2Win.isSelected());
        conf.setBoss3_3pWin(this.boss3_3pWin.isSelected());
        conf.setBoss4Win(this.boss4Win.isSelected());
        conf.setBoss4_4Win(this.boss4_4Win.isSelected());
        conf.setBoss5_2WinS(this.boss5_2WinS.isSelected());
        conf.setBoss6_1WinS(this.boss6_1WinS.isSelected());
        conf.setBattleWin(this.battleWin.isSelected());
        conf.setBattleWinS(this.battleWinS.isSelected());
        conf.setDefeatAP(this.defeatAP.isSelected());
        conf.setDefeatCV(this.defeatCV.isSelected());
        conf.setDefeatSS(this.defeatSS.isSelected());
        conf.setPractice(this.practice.isSelected());
        conf.setPracticeWin(this.practiceWin.isSelected());
        conf.setMissionSuccess(this.missionSuccess.isSelected());
        conf.setCreateShip(this.createShip.isSelected());
        conf.setCreateItem(this.createItem.isSelected());
        conf.setDestroyShip(this.destroyShip.isSelected());
        conf.setDestroyItem(this.destroyItem.isSelected());
        conf.setCharge(this.charge.isSelected());
        conf.setRepair(this.repair.isSelected());
        conf.setPowerUp(this.powerUp.isSelected());

        ThreadManager.getExecutorService().execute(Config.getDefault()::store);
        this.getWindow().close();
    }
}
