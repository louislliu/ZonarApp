package com.zonar.zonarapp;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.zonar.zonarapp.ui.adapter.ModeAdapter;
import com.zonar.zonarapp.ui.dialog.InputDialog;
import com.zonar.zonarapp.ui.layout.ZaMajorLayout;
import com.zonar.zonarapp.ui.view.SpinnerTextView;

import java.util.ArrayList;

public class ZaMajorActivity extends ZaBaseActivity implements View.OnClickListener {

    private SpinnerTextView menuModeView;

    private ZaMajorLayout zaMajorLayout;
    private ListView modeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setLayout();

        findView();

        setView();

        setListener();
    }

    private void setLayout() {
        showLogoTitle();
        getToolbar().showMenuButton();

        zaMajorLayout = new ZaMajorLayout(this);
        setContentView(zaMajorLayout);

        menuModeView = (SpinnerTextView) addOptionsMenu(new SpinnerTextView(this));
        menuModeView.setTitle("模式名稱");
    }

    private void findView() {
        modeList = zaMajorLayout.modeList;
    }

    private void setView() {
        ArrayList<String> items = new ArrayList<>();
        items.add("模式一項目名稱");
        items.add("室內模式");
        items.add("戶外行走環境");
        items.add(null);
        ModeAdapter modeAdapter = new ModeAdapter(this, items);
        modeList.setAdapter(modeAdapter);
    }

    private void setListener() {
        menuModeView.setOnClickListener(this);

        modeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                modeList.setVisibility(View.GONE);

                // TODO choose mode
                String mode = (String) (adapterView.getItemAtPosition(position));
                if (TextUtils.isEmpty(mode)) {
                    final InputDialog dialog = new InputDialog(activity);
                    dialog.setTitle("新增模式");
                    dialog.setHint("輸入模式", null);
                    dialog.setOnClickListener(new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if (i == DialogInterface.BUTTON_POSITIVE) {
                                String newMode = dialog.getEdiTextContent1();

                                ((ModeAdapter) modeList.getAdapter()).removeItem(((ModeAdapter) modeList.getAdapter()).getCount() - 1);
                                ((ModeAdapter) modeList.getAdapter()).addItem(newMode);
                                ((ModeAdapter) modeList.getAdapter()).addItem(null);
                            }
                        }
                    });
                    dialog.show();
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view.equals(menuModeView)) {
            modeList.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected boolean isDefaultLogoShow() {
        return true;
    }

    @Override
    public void onBackPressed() {
        if (modeList.isShown()) {
            modeList.setVisibility(View.GONE);
            return;
        }
        super.onBackPressed();
    }
}