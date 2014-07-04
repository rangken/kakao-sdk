/**
 * Copyright 2014 Kakao Corp.
 *
 * Redistribution and modification in source or binary forms are not permitted without specific prior written permission. 
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.kakao.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.TextView;
import com.kakao.Session;
import com.kakao.SessionCallback;
import com.kakao.authorization.authcode.AuthType;
import com.kakao.helper.TalkProtocol;
import com.kakao.sdk.R;

/**
 * 로그인 버튼
 * </br>
 * 로그인 layout에 {@link LoginButton}을 선언하여 사용한다.
 * @author MJ
 */
public class LoginButton extends FrameLayout {
    private SessionCallback loginSessionCallback;

    public LoginButton(Context context) {
        super(context);
    }

    public LoginButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LoginButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * 로그인 결과를 받을 세션 콜백을 설정한다.
     * @param sessionCallback 로그인 결과를 받을 세션 콜백
     */
    public void setLoginSessionCallback(final SessionCallback sessionCallback){
        this.loginSessionCallback = sessionCallback;
    }

    /**
     * 로그인 버튼 클릭시 세션을 오픈하도록 설정한다.
     */
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        inflate(getContext(), R.layout.kakao_login_layout, this);
        setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // 카톡이 존재하면 옵션을 보여주고, 존재하지 않으면 바로 직접 로그인창.
                if(TalkProtocol.existCapriLoginActivityInTalk(getContext()))
                    onClickLoginButton();
                else
                    Session.getCurrentSession().open(loginSessionCallback, AuthType.KAKAO_ACCOUNT);
            }
        });
    }

    private void onClickLoginButton(){
        final Item[] items = {
            new Item(R.string.com_kakao_kakaotalk_account, R.drawable.kakaotalk_icon),
            new Item(R.string.com_kakao_other_kakaoaccount, R.drawable.kakaoaccount_icon),
            new Item(R.string.com_kakao_account_cancel, 0),//no icon for this one
        };

        final ListAdapter adapter = new ArrayAdapter<Item>(
            getContext(),
            android.R.layout.select_dialog_item,
            android.R.id.text1,
            items){
            public View getView(int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                TextView tv = (TextView)v.findViewById(android.R.id.text1);

                tv.setText(items[position].textId);
                tv.setTextSize(15);
                tv.setGravity(Gravity.CENTER);
                if(position == 2) {
                    tv.setBackgroundResource(R.drawable.kakao_cancel_button_background);
                } else {
                    tv.setBackgroundResource(R.drawable.kakao_account_button_background);
                }
                tv.setCompoundDrawablesWithIntrinsicBounds(items[position].icon, 0, 0, 0);

                int dp5 = (int) (5 * getResources().getDisplayMetrics().density + 0.5f);
                tv.setCompoundDrawablePadding(dp5);

                return v;
            }
        };


        new AlertDialog.Builder(getContext())
            .setAdapter(adapter, new DialogInterface.OnClickListener() {
                public void onClick(final DialogInterface dialog, final int item) {
                    if (item == 0) {// 카톡으로 시작
                        Session.getCurrentSession().open(loginSessionCallback);
                    } else if (item == 1) {// 계정 직접 입력
                        Session.getCurrentSession().open(loginSessionCallback, AuthType.KAKAO_ACCOUNT);
                    } else if (item == 2) {// 취소
                        dialog.dismiss();
                    }
                }
            }).create().show();

    }

    private static class Item {
        public final int textId;
        public final int icon;
        public Item(int textId, Integer icon) {
            this.textId = textId;
            this.icon = icon;
        }
    }
}
