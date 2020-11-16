package ibemu.logbook.plugin.quest;

import logbook.internal.JsonHelper;

import javax.json.JsonObject;
import java.io.Serializable;
import java.util.*;

public class Quest implements Serializable
{
    public static final int COUNT_MAX = 50;
    /**
     *
     */
    private static final long serialVersionUID = -7739198911286351293L;

    /**
     * api_no
     */
    private int no;

    /**
     * api_category
     */
    private int category;

    /**
     * api_type
     */
    private int type;

    /**
     * api_state
     */
    private int state;

    /**
     * api_title
     */
    private String title;

    /**
     * api_detail
     */
    private String detail;

    /**
     * api_get_material
     */
    private List<Integer> material;

    /**
     * api_bonus_flag
     */
    private int bonusFlag;

    /**
     * api_progress_flag
     */
    private int progressFlag;

    /**
     * 期限
     */
    private Date due;

    /**
     * 出撃
     */
    private Set<Date> sortie = Collections.synchronizedSet(new HashSet<>());

    /**
     * ボス到達
     */
    private Set<Date> bossArrive = Collections.synchronizedSet(new HashSet<>());

    /**
     * ボス勝利
     */
    private Set<Date> bossWin = Collections.synchronizedSet(new HashSet<>());

    /**
     * 1-4ボスS
     */
    private Set<Date> boss1_4WinS = Collections.synchronizedSet(new HashSet<>());

    /**
     * 1-5ボスA
     */
    private Set<Date> boss1_5WinA = Collections.synchronizedSet(new HashSet<>());

    /**
     * 南西ボス
     */
    private Set<Date> boss2Win = Collections.synchronizedSet(new HashSet<>());

    /**
     * 3-3+ボス
     */
    private Set<Date> boss3_3pWin = Collections.synchronizedSet(new HashSet<>());

    /**
     * 西方ボス
     */
    private Set<Date> boss4Win = Collections.synchronizedSet(new HashSet<>());

    /**
     * 4-4ボス
     */
    private Set<Date> boss4_4Win = Collections.synchronizedSet(new HashSet<>());

    /**
     * 5-2ボスS
     */
    private Set<Date> boss5_2WinS = Collections.synchronizedSet(new HashSet<>());

    /**
     * 6-1ボスS
     */
    private Set<Date> boss6_1WinS = Collections.synchronizedSet(new HashSet<>());

    /**
     * 戦闘勝利
     */
    private Set<Date> battleWin = Collections.synchronizedSet(new HashSet<>());

    /**
     * 戦闘Sランク
     */
    private Set<Date> battleWinS = Collections.synchronizedSet(new HashSet<>());

    /**
     * 補給艦撃破
     */
    private Set<Date> defeatAP = Collections.synchronizedSet(new HashSet<>());

    /**
     * 空母撃破
     */
    private Set<Date> defeatCV = Collections.synchronizedSet(new HashSet<>());

    /**
     * 潜水艦撃破
     */
    private Set<Date> defeatSS = Collections.synchronizedSet(new HashSet<>());

    /**
     * 演習
     */
    private Set<Date> practice = Collections.synchronizedSet(new HashSet<>());

    /**
     * 演習勝利
     */
    private Set<Date> practiceWin = Collections.synchronizedSet(new HashSet<>());

    /**
     * 遠征成功
     */
    private Set<Date> missionSuccess = Collections.synchronizedSet(new HashSet<>());

    /**
     * 建造
     */
    private Set<Date> createShip = Collections.synchronizedSet(new HashSet<>());

    /**
     * 開発
     */
    private Set<Date> createItem = Collections.synchronizedSet(new HashSet<>());

    /**
     * 解体
     */
    private Set<Date> destroyShip = Collections.synchronizedSet(new HashSet<>());

    /**
     * 廃棄
     */
    private Set<Date> destroyItem = Collections.synchronizedSet(new HashSet<>());

    /**
     * 補給
     */
    private Set<Date> charge = Collections.synchronizedSet(new HashSet<>());

    /**
     * 入渠
     */
    private Set<Date> repair = Collections.synchronizedSet(new HashSet<>());

    /**
     * 近代化改修
     */
    private Set<Date> powerUp = Collections.synchronizedSet(new HashSet<>());

    /**
     * JsonObjectから{@link Quest}を構築します
     *
     * @param json JsonObject
     * @return {@link Quest}
     */
    public static Quest setQuestlist(Quest bean, JsonObject json)
    {
        if(bean == null || (bean.getDue() != null && bean.getDue().before(new Date()))) bean = new Quest();
        JsonHelper.bind(json)
                .setInteger("api_no", bean::setNo)
                .setInteger("api_category", bean::setCategory)
                .setInteger("api_type", bean::setType)
                .setInteger("api_state", bean::setState)
                .setString("api_title", bean::setTitle)
                .setString("api_detail", bean::setDetail)
                .set("api_get_material", bean::setMaterial, JsonHelper::toIntegerList)
                .setInteger("api_bonus_flag", bean::setBonusFlag)
                .setInteger("api_progress_flag", bean::setProgressFlag);
        return bean;
    }

    /**
     * api_noを取得します。
     *
     * @return api_no
     */
    public int getNo()
    {
        return this.no;
    }

    /**
     * api_noを設定します。
     *
     * @param no api_no
     */
    public void setNo(int no)
    {
        this.no = no;
    }

    /**
     * api_categoryを取得します。
     *
     * @return api_category
     */
    public int getCategory()
    {
        return this.category;
    }

    /**
     * api_categoryを設定します。
     *
     * @param category api_category
     */
    public void setCategory(int category)
    {
        this.category = category;
    }

    /**
     * api_typeを取得します。
     *
     * @return api_type
     */
    public int getType()
    {
        return this.type;
    }

    /**
     * api_typeを設定します。
     *
     * @param type api_type
     */
    public void setType(int type)
    {
        this.type = type;
    }

    /**
     * api_stateを取得します。
     *
     * @return api_state
     */
    public int getState()
    {
        return this.state;
    }

    /**
     * api_stateを設定します。
     *
     * @param state api_state
     */
    public void setState(int state)
    {
        this.state = state;
    }

    /**
     * api_titleを取得します。
     *
     * @return api_title
     */
    public String getTitle()
    {
        return this.title;
    }

    /**
     * api_titleを設定します。
     *
     * @param title api_title
     */
    public void setTitle(String title)
    {
        this.title = title;
    }

    /**
     * api_detailを取得します。
     *
     * @return api_detail
     */
    public String getDetail()
    {
        return this.detail;
    }

    /**
     * api_detailを設定します。
     *
     * @param detail api_detail
     */
    public void setDetail(String detail)
    {
        this.detail = detail;
    }

    /**
     * api_get_materialを取得します。
     *
     * @return api_get_material
     */
    public List<Integer> getMaterial()
    {
        return this.material;
    }

    /**
     * api_get_materialを設定します。
     *
     * @param material api_get_material
     */
    public void setMaterial(List<Integer> material)
    {
        this.material = material;
    }

    /**
     * api_bonus_flagを取得します。
     *
     * @return api_bonus_flag
     */
    public int getBonusFlag()
    {
        return this.bonusFlag;
    }

    /**
     * api_bonus_flagを設定します。
     *
     * @param bonusFlag api_bonus_flag
     */
    public void setBonusFlag(int bonusFlag)
    {
        this.bonusFlag = bonusFlag;
    }

    /**
     * api_progress_flagを取得します。
     *
     * @return api_progress_flag
     */
    public int getProgressFlag()
    {
        return this.progressFlag;
    }

    /**
     * api_progress_flagを設定します。
     *
     * @param progressFlag api_progress_flag
     */
    public void setProgressFlag(int progressFlag)
    {
        this.progressFlag = progressFlag;
    }

    /**
     * 期限を取得します
     *
     * @return 期限
     */
    public Date getDue()
    {
        return this.due;
    }

    /**
     * 期限を設定します
     *
     * @param due 期限
     */
    public void setDue(Date due)
    {
        this.due = due;
    }

    /**
     * 出撃を取得します
     *
     * @return 出撃
     */
    public Set<Date> getSortie()
    {
        return this.sortie;
    }

    /**
     * 出撃を設定します
     *
     * @param sortie 出撃
     */
    public void setSortie(Set<Date> sortie)
    {
        this.sortie = sortie;
    }

    /**
     * 出撃をカウントします
     */
    public void countSortie(Date d)
    {
        if(this.sortie.size() < COUNT_MAX)
            this.sortie.add(d);
    }

    /**
     * ボス到達を取得します
     *
     * @return ボス到達
     */
    public Set<Date> getBossArrive()
    {
        return this.bossArrive;
    }

    /**
     * ボス到達を設定します
     *
     * @param bossArrive ボス到達
     */
    public void setBossArrive(Set<Date> bossArrive)
    {
        this.bossArrive = bossArrive;
    }

    /**
     * ボス到達をカウントします
     */
    public void countBossArrive(Date d)
    {
        if(this.bossArrive.size() < COUNT_MAX)
            this.bossArrive.add(d);
    }

    /**
     * ボス勝利を取得します
     *
     * @return ボス勝利
     */
    public Set<Date> getBossWin()
    {
        return this.bossWin;
    }

    /**
     * ボス勝利を設定します
     *
     * @param bossWin ボス勝利
     */
    public void setBossWin(Set<Date> bossWin)
    {
        this.bossWin = bossWin;
    }

    /**
     * ボス勝利をカウントします
     */
    public void countBossWin(Date d)
    {
        if(this.bossWin.size() < COUNT_MAX)
            this.bossWin.add(d);
    }

    /**
     * 1-4ボスSを取得します
     *
     * @return 1-4ボスS
     */
    public Set<Date> getBoss1_4WinS()
    {
        return this.boss1_4WinS;
    }

    /**
     * 1-4ボスSを設定します
     *
     * @param boss1_4WinS 1-4ボスS
     */
    public void setBoss1_4WinS(Set<Date> boss1_4WinS)
    {
        this.boss1_4WinS = boss1_4WinS;
    }

    /**
     * 1-4ボスSをカウントします
     */
    public void countBoss1_4WinS(Date d)
    {
        if(this.boss1_4WinS.size() < COUNT_MAX)
            this.boss1_4WinS.add(d);
    }

    /**
     * 1-5ボスAを取得します
     *
     * @return 1-5ボスA
     */
    public Set<Date> getBoss1_5WinA()
    {
        return this.boss1_5WinA;
    }

    /**
     * 1-5ボスAを設定します
     *
     * @param boss1_5WinA 1-5ボスA
     */
    public void setBoss1_5WinA(Set<Date> boss1_5WinA)
    {
        this.boss1_5WinA = boss1_5WinA;
    }

    /**
     * 1-5ボスAをカウントします
     */
    public void countBoss1_5WinA(Date d)
    {
        if(this.boss1_5WinA.size() < COUNT_MAX)
            this.boss1_5WinA.add(d);
    }

    /**
     * 南西ボスを取得します
     *
     * @return 南西ボス
     */
    public Set<Date> getBoss2Win()
    {
        return this.boss2Win;
    }

    /**
     * 南西ボスを設定します
     *
     * @param boss2Win 南西ボス
     */
    public void setBoss2Win(Set<Date> boss2Win)
    {
        this.boss2Win = boss2Win;
    }

    /**
     * 南西ボスをカウントします
     */
    public void countBoss2Win(Date d)
    {
        if(this.boss2Win.size() < COUNT_MAX)
            this.boss2Win.add(d);
    }

    /**
     * 3-3+ボスを取得します
     *
     * @return 3-3+ボス
     */
    public Set<Date> getBoss3_3pWin()
    {
        return this.boss3_3pWin;
    }

    /**
     * 3-3+ボスを設定します
     *
     * @param boss3_3pWin 3-3+ボス
     */
    public void setBoss3_3pWin(Set<Date> boss3_3pWin)
    {
        this.boss3_3pWin = boss3_3pWin;
    }

    /**
     * 3-3+ボスをカウントします
     */
    public void countBoss3_3pWin(Date d)
    {
        if(this.boss3_3pWin.size() < COUNT_MAX)
            this.boss3_3pWin.add(d);
    }

    /**
     * 西方ボスを取得します
     *
     * @return 西方ボス
     */
    public Set<Date> getBoss4Win()
    {
        return this.boss4Win;
    }

    /**
     * 西方ボスを設定します
     *
     * @param boss4Win 西方ボス
     */
    public void setBoss4Win(Set<Date> boss4Win)
    {
        this.boss4Win = boss4Win;
    }

    /**
     * 西方ボスをカウントします
     */
    public void countBoss4Win(Date d)
    {
        if(this.boss4Win.size() < COUNT_MAX)
            this.boss4Win.add(d);
    }

    /**
     * 4-4ボスを取得します
     *
     * @return 4-4ボス
     */
    public Set<Date> getBoss4_4Win()
    {
        return this.boss4_4Win;
    }

    /**
     * 4-4ボスを設定します
     *
     * @param boss4_4Win 4-4ボス
     */
    public void setBoss4_4Win(Set<Date> boss4_4Win)
    {
        this.boss4_4Win = boss4_4Win;
    }

    /**
     * 4-4ボスをカウントします
     */
    public void countBoss4_4Win(Date d)
    {
        if(this.boss4_4Win.size() < COUNT_MAX)
            this.boss4_4Win.add(d);
    }

    /**
     * 5-2ボスSを取得します
     *
     * @return 5-2ボスS
     */
    public Set<Date> getBoss5_2WinS()
    {
        return this.boss5_2WinS;
    }

    /**
     * 5-2ボスSを設定します
     *
     * @param boss5_2WinS 5-2ボスS
     */
    public void setBoss5_2WinS(Set<Date> boss5_2WinS)
    {
        this.boss5_2WinS = boss5_2WinS;
    }

    /**
     * 5-2ボスSをカウントします
     */
    public void countBoss5_2WinS(Date d)
    {
        if(this.boss5_2WinS.size() < COUNT_MAX)
            this.boss5_2WinS.add(d);
    }

    /**
     * 6-1ボスSを取得します
     *
     * @return 6-1ボスS
     */
    public Set<Date> getBoss6_1WinS()
    {
        return this.boss6_1WinS;
    }

    /**
     * 6-1ボスSを設定します
     *
     * @param boss6_1WinS 6-1ボスS
     */
    public void setBoss6_1WinS(Set<Date> boss6_1WinS)
    {
        this.boss6_1WinS = boss6_1WinS;
    }

    /**
     * 6-1ボスSをカウントします
     */
    public void countBoss6_1WinS(Date d)
    {
        if(this.boss6_1WinS.size() < COUNT_MAX)
            this.boss6_1WinS.add(d);
    }

    /**
     * 戦闘勝利を取得します
     *
     * @return 戦闘勝利
     */
    public Set<Date> getBattleWin()
    {
        return this.battleWin;
    }

    /**
     * 戦闘勝利を設定します
     *
     * @param battleWin 戦闘勝利
     */
    public void setBattleWin(Set<Date> battleWin)
    {
        this.battleWin = battleWin;
    }

    /**
     * 戦闘勝利をカウントします
     */
    public void countBattleWin(Date d)
    {
        if(this.battleWin.size() < COUNT_MAX)
            this.battleWin.add(d);
    }

    /**
     * 戦闘Sランクを取得します
     *
     * @return 戦闘Sランク
     */
    public Set<Date> getBattleWinS()
    {
        return this.battleWinS;
    }

    /**
     * 戦闘Sランクを設定します
     *
     * @param battleWinS 戦闘Sランク
     */
    public void setBattleWinS(Set<Date> battleWinS)
    {
        this.battleWinS = battleWinS;
    }

    /**
     * 戦闘Sランクをカウントします
     */
    public void countBattleWinS(Date d)
    {
        if(this.battleWinS.size() < COUNT_MAX)
            this.battleWinS.add(d);
    }

    /**
     * 補給艦撃破を取得します
     *
     * @return 補給艦撃破
     */
    public Set<Date> getDefeatAP()
    {
        return this.defeatAP;
    }

    /**
     * 補給艦撃破を設定します
     *
     * @param defeatAP 補給艦撃破
     */
    public void setDefeatAP(Set<Date> defeatAP)
    {
        this.defeatAP = defeatAP;
    }

    /**
     * 補給艦撃破をカウントします
     */
    public void countDefeatAP(Date d)
    {
        if(this.defeatAP.size() < COUNT_MAX)
            this.defeatAP.add(d);
    }

    /**
     * 空母撃破を取得します
     *
     * @return 空母撃破
     */
    public Set<Date> getDefeatCV()
    {
        return this.defeatCV;
    }

    /**
     * 空母撃破を設定します
     *
     * @param defeatCV 空母撃破
     */
    public void setDefeatCV(Set<Date> defeatCV)
    {
        this.defeatCV = defeatCV;
    }

    /**
     * 空母撃破をカウントします
     */
    public void countDefeatCV(Date d)
    {
        if(this.defeatCV.size() < COUNT_MAX)
            this.defeatCV.add(d);
    }

    /**
     * 潜水艦撃破を取得します
     *
     * @return 潜水艦撃破
     */
    public Set<Date> getDefeatSS()
    {
        return this.defeatSS;
    }

    /**
     * 潜水艦撃破を設定します
     *
     * @param defeatSS 潜水艦撃破
     */
    public void setDefeatSS(Set<Date> defeatSS)
    {
        this.defeatSS = defeatSS;
    }

    /**
     * 潜水艦撃破をカウントします
     */
    public void countDefeatSS(Date d)
    {
        if(this.defeatSS.size() < COUNT_MAX)
            this.defeatSS.add(d);
    }

    /**
     * 演習を取得します
     *
     * @return 演習
     */
    public Set<Date> getPractice()
    {
        return this.practice;
    }

    /**
     * 演習を設定します
     *
     * @param practice 演習
     */
    public void setPractice(Set<Date> practice)
    {
        this.practice = practice;
    }

    /**
     * 演習をカウントします
     */
    public void countPractice(Date d)
    {
        if(this.practice.size() < COUNT_MAX)
            this.practice.add(d);
    }

    /**
     * 演習勝利を取得します
     *
     * @return 演習勝利
     */
    public Set<Date> getPracticeWin()
    {
        return this.practiceWin;
    }

    /**
     * 演習勝利を設定します
     *
     * @param practiceWin 演習勝利
     */
    public void setPracticeWin(Set<Date> practiceWin)
    {
        this.practiceWin = practiceWin;
    }

    /**
     * 演習勝利をカウントします
     */
    public void countPracticeWin(Date d)
    {
        if(this.practiceWin.size() < COUNT_MAX)
            this.practiceWin.add(d);
    }

    /**
     * 遠征成功を取得します
     *
     * @return 遠征成功
     */
    public Set<Date> getMissionSuccess()
    {
        return this.missionSuccess;
    }

    /**
     * 遠征成功を設定します
     *
     * @param missionSuccess 遠征成功
     */
    public void setMissionSuccess(Set<Date> missionSuccess)
    {
        this.missionSuccess = missionSuccess;
    }

    /**
     * 遠征成功をカウントします
     */
    public void countMissionSuccess(Date d)
    {
        if(this.missionSuccess.size() < COUNT_MAX)
            this.missionSuccess.add(d);
    }

    /**
     * 建造を取得します
     *
     * @return 建造
     */
    public Set<Date> getCreateShip()
    {
        return this.createShip;
    }

    /**
     * 建造を設定します
     *
     * @param createShip 建造
     */
    public void setCreateShip(Set<Date> createShip)
    {
        this.createShip = createShip;
    }

    /**
     * 建造をカウントします
     */
    public void countCreateShip(Date d)
    {
        if(this.createShip.size() < COUNT_MAX)
            this.createShip.add(d);
    }

    /**
     * 開発を取得します
     *
     * @return 開発
     */
    public Set<Date> getCreateItem()
    {
        return this.createItem;
    }

    /**
     * 開発を設定します
     *
     * @param createItem 開発
     */
    public void setCreateItem(Set<Date> createItem)
    {
        this.createItem = createItem;
    }

    /**
     * 開発をカウントします
     */
    public void countCreateItem(Date d)
    {
        if(this.createItem.size() < COUNT_MAX)
            this.createItem.add(d);
    }

    /**
     * 解体を取得します
     *
     * @return 解体
     */
    public Set<Date> getDestroyShip()
    {
        return this.destroyShip;
    }

    /**
     * 解体を設定します
     *
     * @param destroyShip 解体
     */
    public void setDestroyShip(Set<Date> destroyShip)
    {
        this.destroyShip = destroyShip;
    }

    /**
     * 解体をカウントします
     */
    public void countDestroyShip(Date d)
    {
        if(this.destroyShip.size() < COUNT_MAX)
            this.destroyShip.add(d);
    }

    /**
     * 廃棄を取得します
     *
     * @return 廃棄
     */
    public Set<Date> getDestroyItem()
    {
        return this.destroyItem;
    }

    /**
     * 廃棄を設定します
     *
     * @param destroyItem 廃棄
     */
    public void setDestroyItem(Set<Date> destroyItem)
    {
        this.destroyItem = destroyItem;
    }

    /**
     * 廃棄をカウントします
     */
    public void countDestroyItem(Date d)
    {
        if(this.destroyItem.size() < COUNT_MAX)
            this.destroyItem.add(d);
    }

    /**
     * 補給を取得します
     *
     * @return 補給
     */
    public Set<Date> getCharge()
    {
        return this.charge;
    }

    /**
     * 補給を設定します
     *
     * @param charge 補給
     */
    public void setCharge(Set<Date> charge)
    {
        this.charge = charge;
    }

    /**
     * 補給をカウントします
     */
    public void countCharge(Date d)
    {
        if(this.charge.size() < COUNT_MAX)
            this.charge.add(d);
    }

    /**
     * 入渠を取得します
     *
     * @return 入渠
     */
    public Set<Date> getRepair()
    {
        return this.repair;
    }

    /**
     * 入渠を設定します
     *
     * @param repair 入渠
     */
    public void setRepair(Set<Date> repair)
    {
        this.repair = repair;
    }

    /**
     * 入渠をカウントします
     */
    public void countRepair(Date d)
    {
        if(this.repair.size() < COUNT_MAX)
            this.repair.add(d);
    }

    /**
     * 近代化改修を取得します
     *
     * @return 近代化改修
     */
    public Set<Date> getPowerUp()
    {
        return this.powerUp;
    }

    /**
     * 近代化改修を設定します
     *
     * @param powerUp 近代化改修
     */
    public void setPowerUp(Set<Date> powerUp)
    {
        this.powerUp = powerUp;
    }

    /**
     * 近代化改修をカウントします
     */
    public void countPowerUp(Date d)
    {
        if(this.powerUp.size() < COUNT_MAX)
            this.powerUp.add(d);
    }
}
