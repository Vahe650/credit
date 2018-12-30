package app.credit.service;

import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
@Service
public class CreditService {
    public static final DateFormat DATE_FORMAT = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
    public String getDates(String date) {

        String year = date.substring(2, 4);

        String month = date.substring(5, 7);

        String day = date.substring(8, 10);

        switch (month.substring(0, 2)) {

            case "01":

                month = "Հնվ";

                break;

            case "02":

                month = "Փտր";

                break;

            case "03":

                month = "Մրտ";

                break;

            case "04":

                month = "Ապր";

                break;

            case "05":

                month = "Մայ";

                break;

            case "06":

                month = "Հուն";

                break;

            case "07":

                month = "Հուլ";

                break;

            case "08":

                month = "Օգս";

                break;

            case "09":

                month = "Սեպ";

                break;

            case "10":

                month="Հոկ";

                break;

            case "11":

                month="Նոյ";

                break;

            case "12":

                month="Դեկ";

                break;

        }
        return  month+"-"+day+"-"+year+"թ";

    }

}
