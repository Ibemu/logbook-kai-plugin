package ibemu.logbook.plugin.quest;

import java.io.Serializable;

import logbook.internal.Config;

public class QuestTableConfig implements Serializable
{

    /**
     *
     */
    private static final long serialVersionUID = -4435908048934406890L;

    /** api_no */
    private boolean no = true;

//    /** api_category */
//    private boolean category = true;
//
//    /** api_type */
//    private boolean type = true;

    /** api_state */
    private boolean state = true;

    /** api_title */
    private boolean title = true;

    /** api_detail */
    private boolean detail = true;

    /** api_get_material[0] */
    private boolean fuel = true;

    /** api_get_material[1] */
    private boolean ammo = true;

    /** api_get_material[2] */
    private boolean metal = true;

    /** api_get_material[3] */
    private boolean bauxite = true;

//    /** api_bonus_flag */
//    private boolean bonusFlag = true;
//
//    /** api_progress_flag */
//    private boolean progressFlag = true;

    /** 期限 */
    private boolean due = true;

    /** 出撃 */
    private boolean sortie = true;

    /** ボス到達 */
    private boolean bossArrive = true;

    /** ボス勝利 */
    private boolean bossWin = true;

    /** 1-4ボスS */
    private boolean boss1_4WinS = true;

    /** 1-5ボスA */
    private boolean boss1_5WinA = true;

    /** 南西ボス */
    private boolean boss2Win = true;

    /** 3-3+ボス */
    private boolean boss3_3pWin = true;

    /** 西方ボス */
    private boolean boss4Win = true;

    /** 4-4ボス */
    private boolean boss4_4Win = true;

    /** 5-2ボスS */
    private boolean boss5_2WinS = true;

    /** 6-1ボスS */
    private boolean boss6_1WinS = true;

    /** 戦闘勝利 */
    private boolean battleWin = true;

    /** 戦闘Sランク */
    private boolean battleWinS = true;

    /** 補給艦撃破 */
    private boolean defeatAP = true;

    /** 空母撃破 */
    private boolean defeatCV = true;

    /** 潜水艦撃破 */
    private boolean defeatSS = true;

    /** 演習 */
    private boolean practice = true;

    /** 演習勝利 */
    private boolean practiceWin = true;

    /** 遠征成功 */
    private boolean missionSuccess = true;

    /** 建造 */
    private boolean createShip = true;

    /** 開発 */
    private boolean createItem = true;

    /** 解体 */
    private boolean destroyShip = true;

    /** 廃棄 */
    private boolean destroyItem = true;

    /** 補給 */
    private boolean charge = true;

    /** 入渠 */
    private boolean repair = true;

    /** 近代化改修 */
    private boolean powerUp = true;

    /**
     * @return no
     */
    public boolean isNo()
    {
        return no;
    }

    /**
     * @param no セットする no
     */
    public void setNo(boolean no)
    {
        this.no = no;
    }

    /**
     * @return category
     */
//    public boolean isCategory()
//    {
//        return category;
//    }
//
//    /**
//     * @param category セットする category
//     */
//    public void setCategory(boolean category)
//    {
//        this.category = category;
//    }
//
//    /**
//     * @return type
//     */
//    public boolean isType()
//    {
//        return type;
//    }
//
//    /**
//     * @param type セットする type
//     */
//    public void setType(boolean type)
//    {
//        this.type = type;
//    }

    /**
     * @return state
     */
    public boolean isState()
    {
        return state;
    }

    /**
     * @param state セットする state
     */
    public void setState(boolean state)
    {
        this.state = state;
    }

    /**
     * @return title
     */
    public boolean isTitle()
    {
        return title;
    }

    /**
     * @param title セットする title
     */
    public void setTitle(boolean title)
    {
        this.title = title;
    }

    /**
     * @return detail
     */
    public boolean isDetail()
    {
        return detail;
    }

    /**
     * @param detail セットする detail
     */
    public void setDetail(boolean detail)
    {
        this.detail = detail;
    }

    /**
     * @return fuel
     */
    public boolean isFuel()
    {
        return fuel;
    }

    /**
     * @param fuel セットする fuel
     */
    public void setFuel(boolean fuel)
    {
        this.fuel = fuel;
    }

    /**
     * @return ammo
     */
    public boolean isAmmo()
    {
        return ammo;
    }

    /**
     * @param ammo セットする ammo
     */
    public void setAmmo(boolean ammo)
    {
        this.ammo = ammo;
    }

    /**
     * @return metal
     */
    public boolean isMetal()
    {
        return metal;
    }

    /**
     * @param metal セットする metal
     */
    public void setMetal(boolean metal)
    {
        this.metal = metal;
    }

    /**
     * @return bauxite
     */
    public boolean isBauxite()
    {
        return bauxite;
    }

    /**
     * @param bauxite セットする bauxite
     */
    public void setBauxite(boolean bauxite)
    {
        this.bauxite = bauxite;
    }

//    /**
//     * @return bonusFlag
//     */
//    public boolean isBonusFlag()
//    {
//        return bonusFlag;
//    }
//
//    /**
//     * @param bonusFlag セットする bonusFlag
//     */
//    public void setBonusFlag(boolean bonusFlag)
//    {
//        this.bonusFlag = bonusFlag;
//    }
//
//    /**
//     * @return progressFlag
//     */
//    public boolean isProgressFlag()
//    {
//        return progressFlag;
//    }
//
//    /**
//     * @param progressFlag セットする progressFlag
//     */
//    public void setProgressFlag(boolean progressFlag)
//    {
//        this.progressFlag = progressFlag;
//    }

    /**
     * @return due
     */
    public boolean isDue()
    {
        return due;
    }

    /**
     * @param due セットする due
     */
    public void setDue(boolean due)
    {
        this.due = due;
    }

    /**
     * @return sortie
     */
    public boolean isSortie()
    {
        return sortie;
    }

    /**
     * @param sortie セットする sortie
     */
    public void setSortie(boolean sortie)
    {
        this.sortie = sortie;
    }

    /**
     * @return bossArrive
     */
    public boolean isBossArrive()
    {
        return bossArrive;
    }

    /**
     * @param bossArrive セットする bossArrive
     */
    public void setBossArrive(boolean bossArrive)
    {
        this.bossArrive = bossArrive;
    }

    /**
     * @return bossWin
     */
    public boolean isBossWin()
    {
        return bossWin;
    }

    /**
     * @param bossWin セットする bossWin
     */
    public void setBossWin(boolean bossWin)
    {
        this.bossWin = bossWin;
    }

    /**
     * @return boss1_4WinS
     */
    public boolean isBoss1_4WinS()
    {
        return boss1_4WinS;
    }

    /**
     * @param boss1_4WinS セットする boss1_4WinS
     */
    public void setBoss1_4WinS(boolean boss1_4WinS)
    {
        this.boss1_4WinS = boss1_4WinS;
    }

    /**
     * @return boss1_5WinA
     */
    public boolean isBoss1_5WinA()
    {
        return boss1_5WinA;
    }

    /**
     * @param boss1_5WinA セットする boss1_5WinA
     */
    public void setBoss1_5WinA(boolean boss1_5WinA)
    {
        this.boss1_5WinA = boss1_5WinA;
    }

    /**
     * @return boss2Win
     */
    public boolean isBoss2Win()
    {
        return boss2Win;
    }

    /**
     * @param boss2Win セットする boss2Win
     */
    public void setBoss2Win(boolean boss2Win)
    {
        this.boss2Win = boss2Win;
    }

    /**
     * @return boss3_3pWin
     */
    public boolean isBoss3_3pWin()
    {
        return boss3_3pWin;
    }

    /**
     * @param boss3_3pWin セットする boss3_3pWin
     */
    public void setBoss3_3pWin(boolean boss3_3pWin)
    {
        this.boss3_3pWin = boss3_3pWin;
    }

    /**
     * @return boss4Win
     */
    public boolean isBoss4Win()
    {
        return boss4Win;
    }

    /**
     * @param boss4Win セットする boss4Win
     */
    public void setBoss4Win(boolean boss4Win)
    {
        this.boss4Win = boss4Win;
    }

    /**
     * @return boss4_4Win
     */
    public boolean isBoss4_4Win()
    {
        return boss4_4Win;
    }

    /**
     * @param boss4_4Win セットする boss4_4Win
     */
    public void setBoss4_4Win(boolean boss4_4Win)
    {
        this.boss4_4Win = boss4_4Win;
    }

    /**
     * @return boss5_2WinS
     */
    public boolean isBoss5_2WinS()
    {
        return boss5_2WinS;
    }

    /**
     * @param boss5_2WinS セットする boss5_2WinS
     */
    public void setBoss5_2WinS(boolean boss5_2WinS)
    {
        this.boss5_2WinS = boss5_2WinS;
    }

    /**
     * @return boss6_1WinS
     */
    public boolean isBoss6_1WinS()
    {
        return boss6_1WinS;
    }

    /**
     * @param boss6_1WinS セットする boss6_1WinS
     */
    public void setBoss6_1WinS(boolean boss6_1WinS)
    {
        this.boss6_1WinS = boss6_1WinS;
    }

    /**
     * @return battleWin
     */
    public boolean isBattleWin()
    {
        return battleWin;
    }

    /**
     * @param battleWin セットする battleWin
     */
    public void setBattleWin(boolean battleWin)
    {
        this.battleWin = battleWin;
    }

    /**
     * @return battleWinS
     */
    public boolean isBattleWinS()
    {
        return battleWinS;
    }

    /**
     * @param battleWinS セットする battleWinS
     */
    public void setBattleWinS(boolean battleWinS)
    {
        this.battleWinS = battleWinS;
    }

    /**
     * @return defeatAP
     */
    public boolean isDefeatAP()
    {
        return defeatAP;
    }

    /**
     * @param defeatAP セットする defeatAP
     */
    public void setDefeatAP(boolean defeatAP)
    {
        this.defeatAP = defeatAP;
    }

    /**
     * @return defeatCV
     */
    public boolean isDefeatCV()
    {
        return defeatCV;
    }

    /**
     * @param defeatCV セットする defeatCV
     */
    public void setDefeatCV(boolean defeatCV)
    {
        this.defeatCV = defeatCV;
    }

    /**
     * @return defeatSS
     */
    public boolean isDefeatSS()
    {
        return defeatSS;
    }

    /**
     * @param defeatSS セットする defeatSS
     */
    public void setDefeatSS(boolean defeatSS)
    {
        this.defeatSS = defeatSS;
    }

    /**
     * @return practice
     */
    public boolean isPractice()
    {
        return practice;
    }

    /**
     * @param practice セットする practice
     */
    public void setPractice(boolean practice)
    {
        this.practice = practice;
    }

    /**
     * @return practiceWin
     */
    public boolean isPracticeWin()
    {
        return practiceWin;
    }

    /**
     * @param practiceWin セットする practiceWin
     */
    public void setPracticeWin(boolean practiceWin)
    {
        this.practiceWin = practiceWin;
    }

    /**
     * @return missionSuccess
     */
    public boolean isMissionSuccess()
    {
        return missionSuccess;
    }

    /**
     * @param missionSuccess セットする missionSuccess
     */
    public void setMissionSuccess(boolean missionSuccess)
    {
        this.missionSuccess = missionSuccess;
    }

    /**
     * @return createShip
     */
    public boolean isCreateShip()
    {
        return createShip;
    }

    /**
     * @param createShip セットする createShip
     */
    public void setCreateShip(boolean createShip)
    {
        this.createShip = createShip;
    }

    /**
     * @return createItem
     */
    public boolean isCreateItem()
    {
        return createItem;
    }

    /**
     * @param createItem セットする createItem
     */
    public void setCreateItem(boolean createItem)
    {
        this.createItem = createItem;
    }

    /**
     * @return destroyShip
     */
    public boolean isDestroyShip()
    {
        return destroyShip;
    }

    /**
     * @param destroyShip セットする destroyShip
     */
    public void setDestroyShip(boolean destroyShip)
    {
        this.destroyShip = destroyShip;
    }

    /**
     * @return destroyItem
     */
    public boolean isDestroyItem()
    {
        return destroyItem;
    }

    /**
     * @param destroyItem セットする destroyItem
     */
    public void setDestroyItem(boolean destroyItem)
    {
        this.destroyItem = destroyItem;
    }

    /**
     * @return charge
     */
    public boolean isCharge()
    {
        return charge;
    }

    /**
     * @param charge セットする charge
     */
    public void setCharge(boolean charge)
    {
        this.charge = charge;
    }

    /**
     * @return repair
     */
    public boolean isRepair()
    {
        return repair;
    }

    /**
     * @param repair セットする repair
     */
    public void setRepair(boolean repair)
    {
        this.repair = repair;
    }

    /**
     * @return powerUp
     */
    public boolean isPowerUp()
    {
        return powerUp;
    }

    /**
     * @param powerUp セットする powerUp
     */
    public void setPowerUp(boolean powerUp)
    {
        this.powerUp = powerUp;
    }

    /**
     * アプリケーションのデフォルト設定ディレクトリからアプリケーション設定を取得します、
     * これは次の記述と同等です
     * <blockquote>
     *     <code>Config.getDefault().get(QuestTableConfig.class, QuestTableConfig::new)</code>
     * </blockquote>
     *
     * @return アプリケーションの設定
     */
    public static QuestTableConfig get() {
        return Config.getDefault().get(QuestTableConfig.class, QuestTableConfig::new);
    }
}
