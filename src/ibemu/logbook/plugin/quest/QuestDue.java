package ibemu.logbook.plugin.quest;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * クエストの期日
 *
 */
public class QuestDue {
    private static Calendar daily;
    private static Calendar weekly;
    private static Calendar monthly;

    static
    {
        Calendar now = Calendar.getInstance(TimeZone.getTimeZone("JST"));
        daily = Calendar.getInstance(TimeZone.getTimeZone("JST"));
        weekly = Calendar.getInstance(TimeZone.getTimeZone("JST"));
        monthly = Calendar.getInstance(TimeZone.getTimeZone("JST"));

        daily.set(Calendar.HOUR_OF_DAY, 5);
        daily.clear(Calendar.MINUTE);
        daily.clear(Calendar.SECOND);
        daily.clear(Calendar.MILLISECOND);
        if (now.after(daily))
            daily.add(Calendar.DATE, 1);

        weekly.set(Calendar.HOUR_OF_DAY, 5);
        weekly.clear(Calendar.MINUTE);
        weekly.clear(Calendar.SECOND);
        weekly.clear(Calendar.MILLISECOND);
        weekly.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        if (now.after(weekly))
            weekly.add(Calendar.DATE, 7);

        monthly.set(Calendar.HOUR_OF_DAY, 5);
        monthly.clear(Calendar.MINUTE);
        monthly.clear(Calendar.SECOND);
        monthly.clear(Calendar.MILLISECOND);
        monthly.set(Calendar.DAY_OF_MONTH, 1);
        if (now.after(monthly))
            monthly.add(Calendar.MONTH, 1);
    }

    public static synchronized Date getDaily()
    {
        Calendar now = Calendar.getInstance(TimeZone.getTimeZone("JST"));
        while (now.after(daily))
            daily.add(Calendar.DATE, 1);
        return daily.getTime();
    }

    public static synchronized Date getWeekly()
    {
        Calendar now = Calendar.getInstance(TimeZone.getTimeZone("JST"));
        while (now.after(weekly))
            weekly.add(Calendar.DATE, 7);
        return weekly.getTime();
    }

    public static synchronized Date getMonthly()
    {
        Calendar now = Calendar.getInstance(TimeZone.getTimeZone("JST"));
        while (now.after(monthly))
            monthly.add(Calendar.MONTH, 1);
        return monthly.getTime();
    }
}
