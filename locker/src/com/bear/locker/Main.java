package com.bear.locker;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import android.os.Bundle;

import android.widget.Toast;

import butterknife.ButterKnife;

import butterknife.InjectView;

import butterknife.OnClick;

public class Main extends Activity {

    static final int ADMIN_REQ = 1001;
    static final String description = "kLocker Administrator";

    DevicePolicyManager mDevicePolicyManager;
    ComponentName mComponentName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ButterKnife.inject(this);

        mDevicePolicyManager = (DevicePolicyManager)getSystemService(Context.DEVICE_POLICY_SERVICE);
        mComponentName = new ComponentName(this, AdminReceiver.class);
    }

    @OnClick(R.id.enable)
    void doEnable() {
        Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, mComponentName);
        intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, description);
        startActivityForResult(intent, ADMIN_REQ);
    }

    @OnClick(R.id.lock)
    void doLock() {
        boolean isAdmin = mDevicePolicyManager.isAdminActive(mComponentName);
        if (isAdmin) {
            mDevicePolicyManager.lockNow();
        }else{
            Toast.makeText(getApplicationContext(), "Not Registered as admin", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.unlock)
    void doUnlock() {
        finish();
    }

    @OnClick(R.id.disable)
    void doDisable() {
        mDevicePolicyManager.removeActiveAdmin(mComponentName);
        Toast.makeText(getApplicationContext(), "Admin registration removed", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ADMIN_REQ) {
            if (resultCode == RESULT_OK){
                Toast.makeText(getApplicationContext(), "Registered As Admin", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Failed to register as Admin", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
