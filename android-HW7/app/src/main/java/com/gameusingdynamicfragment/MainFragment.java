package com.gameusingdynamicfragment;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class MainFragment extends Fragment {

    public enum GameResultType {
        TYPE_1, TYPE_2, TYPE_3, HIDE
    }

    // 所屬的 Activity 必須實作以下介面中的callback方法
    public interface CallbackInterface {
        public void updateGameResult(int iCountSet, int iCountPlayerWin, int iCountComWin, int iCountDraw);
        public void enableGameResult(GameResultType type);
    };

    private CallbackInterface mCallback;

    private TextView mTxtResult;
/*
    public EditText mEdtCountSet,
                    mEdtCountPlayerWin,
                    mEdtCountComWin,
                    mEdtCountDraw;
*/

    // 新增統計遊戲局數和輸贏的變數
    private int miCountSet = 0, miCountPlayerWin = 0, miCountComWin = 0, miCountDraw = 0;

    //private ImageButton mDiceBtn;
    private Button mBtnCrap;
    private ImageView mImgViewDice;
    private Button mBtnShowResult1, mBtnShowResult2, mBtnGameStatistics, mBtnHiddenResult;

    private int[] diceImg = new int[]{
            R.drawable.dice01, R.drawable.dice02, R.drawable.dice03,
            R.drawable.dice04, R.drawable.dice05, R.drawable.dice06};

    private boolean isDiceRoll = false;

//    private final static String TAG = "Result";
//    private int mTagCount = 0;

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mCallback = (CallbackInterface) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() +
                    "must implement GameFragment.CallbackInterface.");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // 利用inflater物件的inflate()方法取得介面佈局檔，並將最後的結果傳回給系統
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mTxtResult = (TextView) getView().findViewById(R.id.txtResult);
        mImgViewDice = (ImageView) getView().findViewById(R.id.imgDice);
        mBtnCrap = (Button) getView().findViewById(R.id.btnCrap);

        mBtnCrap.setOnClickListener(btnCrapOnClick);

        mBtnShowResult1 = (Button) getView().findViewById(R.id.btnShowResult1);
        mBtnShowResult2 = (Button) getView().findViewById(R.id.btnShowResult2);
        mBtnGameStatistics = (Button) getView().findViewById(R.id.btnGameStatistics);
        mBtnHiddenResult = (Button) getView().findViewById(R.id.btnHiddenResult);

        mBtnShowResult1.setOnClickListener(btnShowResult1OnClick);
        mBtnShowResult2.setOnClickListener(btnShowResult2OnClick);
        mBtnGameStatistics.setOnClickListener(btnGameStatistics);
        mBtnHiddenResult.setOnClickListener(btnHiddenResultOnClick);
    }

    private View.OnClickListener btnCrapOnClick = new View.OnClickListener() {
        public void onClick(View v) {
            if (isDiceRoll)
                return;
            isDiceRoll = true;

            Resources res = getResources();
            final AnimationDrawable animDraw = (AnimationDrawable) res.getDrawable(R.drawable.anim_roll_dice);
            mImgViewDice.setImageDrawable(animDraw);
            animDraw.start();

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    animDraw.stop();
                    rollingAndRst();
                    isDiceRoll = false;
                }
            }, 3000);
        }
    };

    public void rollingAndRst() {
        int diceNumber = (int)(Math.random() * 6);
        miCountSet++;
        mImgViewDice.setImageDrawable(getResources().getDrawable(diceImg[diceNumber]));
        switch (diceNumber / 2) {
            case 0:
                mTxtResult.setText(getString(R.string.result) + getString(R.string.player_win));
                ++miCountPlayerWin;
                break;
            case 1:
                mTxtResult.setText(getString(R.string.result) + getString(R.string.player_draw));
                ++miCountDraw;
                break;
            case 2:
                mTxtResult.setText(getString(R.string.result) + getString(R.string.player_lose));
                ++miCountComWin;
                break;
            default:
                break;
        }
        mCallback.updateGameResult(miCountSet, miCountPlayerWin, miCountComWin, miCountDraw);
    }

    private View.OnClickListener btnShowResult1OnClick = new View.OnClickListener() {
        public void onClick(View v) {
            mCallback.enableGameResult(GameResultType.TYPE_1);
        }
    };

    private View.OnClickListener btnShowResult2OnClick = new View.OnClickListener() {
        public void onClick(View v) {
            mCallback.enableGameResult(GameResultType.TYPE_2);
        }
    };

    private View.OnClickListener btnGameStatistics = new View.OnClickListener() {
        public void onClick(View v) {
            mCallback.enableGameResult(GameResultType.TYPE_3);
        }
    };

    private View.OnClickListener btnHiddenResultOnClick = new View.OnClickListener() {
        public void onClick(View v) {
            mCallback.enableGameResult(GameResultType.HIDE);
        }
    };
}