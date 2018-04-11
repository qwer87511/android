package edu.ntut.user.myhw3;

import android.content.res.Resources;
import android.widget.RadioGroup;

/**
 * Created by user on 2018/3/20.
 */

public class MarriageSuggestion {

    public String getSuggestion(String strSex, int iAgeRange, int numFamily) {

        String strSug = "建議：";
        int iSugLevel = 0; // 1 = 還不急, 2 = 開始找對象, 3 = 趕快結婚

        if (strSex.equals("male")) {
            switch (iAgeRange) {
                case 1:
                    if (numFamily < 4)
                        iSugLevel = 3;
                    else if (numFamily >= 4 && numFamily <= 10)
                        iSugLevel = 3;
                    else
                        iSugLevel = 1;
                    break;
                case 2:
                    if (numFamily < 4)
                        iSugLevel = 3;
                    else if (numFamily >= 4 && numFamily <= 10)
                        iSugLevel = 2;
                    else
                        iSugLevel = 1;
                    break;
                case 3:
                    if (numFamily < 4)
                        iSugLevel = 2;
                    else if (numFamily >= 4 && numFamily <= 10)
                        iSugLevel = 3;
                    else
                        iSugLevel = 2;
                    break;
            }
        } else {
            switch (iAgeRange) {
                case 1:
                    if (numFamily < 4)
                        iSugLevel = 3;
                    else if (numFamily >= 4 && numFamily <= 10)
                        iSugLevel = 3;
                    else
                        iSugLevel = 1;
                    break;
                case 2:
                    if (numFamily < 4)
                        iSugLevel = 3;
                    else if (numFamily >= 4 && numFamily <= 10)
                        iSugLevel = 2;
                    else
                        iSugLevel = 1;
                    break;
                case 3:
                    if (numFamily < 4)
                        iSugLevel = 2;
                    else if (numFamily >= 4 && numFamily <= 10)
                        iSugLevel = 3;
                    else
                        iSugLevel = 2;
                    break;
            }
        }

        switch(iSugLevel) {
            case 1:
                strSug += "還不急";
                break;
            case 2:
                strSug += "開始找對象";
                break;
            case 3:
                strSug += "趕快結婚";
                break;
        }

        return strSug;
    }
}
