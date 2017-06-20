package com.mvvm.zzy.plusemonitorwithbluetooth_demo.View.Activity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import com.mvvm.zzy.plusemonitorwithbluetooth_demo.BR;
import com.mvvm.zzy.plusemonitorwithbluetooth_demo.Model.Adapters.ListAdapter;
import com.mvvm.zzy.plusemonitorwithbluetooth_demo.Model.Bean.BtDeviceItem;
import com.mvvm.zzy.plusemonitorwithbluetooth_demo.Model.Bean.SystemInfo;
import com.mvvm.zzy.plusemonitorwithbluetooth_demo.R;
import com.mvvm.zzy.plusemonitorwithbluetooth_demo.ViewModel.BluetoothViewModel.BtReceiver;
import com.mvvm.zzy.plusemonitorwithbluetooth_demo.ViewModel.ButtonViewModel.SearchButtonViewModel;
import com.mvvm.zzy.plusemonitorwithbluetooth_demo.ViewModel.ButtonViewModel.SwitchButtonViewModel;
import com.mvvm.zzy.plusemonitorwithbluetooth_demo.ViewModel.ListViewModel.BtDeviceListItemViewModel;
import com.mvvm.zzy.plusemonitorwithbluetooth_demo.ViewModel.ListViewModel.ListItemViewUpdata;
import com.mvvm.zzy.plusemonitorwithbluetooth_demo.ViewModel.ListViewModel.NewProgressCreation;
import com.mvvm.zzy.plusemonitorwithbluetooth_demo.ViewModel.MethodsViewModel.ActionBarOperation;
import com.mvvm.zzy.plusemonitorwithbluetooth_demo.ViewModel.MethodsViewModel.BtPairing;
import com.mvvm.zzy.plusemonitorwithbluetooth_demo.ViewModel.MethodsViewModel.ImmersionLine;
import com.mvvm.zzy.plusemonitorwithbluetooth_demo.databinding.ActionbarLoginBinding;
import com.mvvm.zzy.plusemonitorwithbluetooth_demo.databinding.ActivityMoniterBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MoniterActivity extends AppCompatActivity {

    private SystemInfo systemInfo;
    private SwitchButtonViewModel switchButtonViewModel;
    private SearchButtonViewModel searchButtonViewModel;
    private BtDeviceListItemViewModel listItemViewModel;
    private BtReceiver btReceiver;
    private IntentFilter btFilter;
    private BluetoothAdapter btAdapter;
    private ActivityMoniterBinding binding;
    private List<BtDeviceItem> deviceList;
    private ListAdapter deviceListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        super.onCreate(savedInstanceState);
        new ImmersionLine(this, R.color.actionBarBackground);
        initActionBar();
        initData();
        initBinding();
    }

    private void initActionBar() {
        initCustomActionBar();
    }

    private void initCustomActionBar() {
        ActionBarOperation.setSystemActionBar(getApplicationContext(), this.getSupportActionBar(), R.color.actionBarBackground);
        ViewGroup root = (ViewGroup) findViewById(R.id.ablogin_root);
        ActionbarLoginBinding custonActionBar = DataBindingUtil.inflate(getLayoutInflater(), R.layout.actionbar_login, root, false);
        custonActionBar.abloginTitle.setText(R.string.navigation_optname_monitor);
        custonActionBar.abloginLeftImbtn.setVisibility(View.VISIBLE);
        custonActionBar.abloginLeftImbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        this.getSupportActionBar().setCustomView(custonActionBar.abloginRoot);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (btAdapter.isEnabled()) {
            systemInfo.setOpen(true);
        }
    }

    private void initData() {
        systemInfo = new SystemInfo();
        deviceList = new ArrayList<>();
        deviceListAdapter = new ListAdapter<>(MoniterActivity.this, R.layout.module_deviceitem, BR.deviceItem, deviceList);
        initBtAdapter();
        initBtReceiver();
    }

    private void initBtAdapter() {
        btAdapter = BluetoothAdapter.getDefaultAdapter();
        if (btAdapter == null) {
            processInvalidBtAdapter();;
        }
    }

    private void processInvalidBtAdapter() {
        Toast.makeText(getApplicationContext(), R.string.invalid_bt, Toast.LENGTH_SHORT).show();
        System.exit(0);
    }

    private void initBtReceiver() {
        btReceiver = new BtReceiver(btAdapter, systemInfo, deviceListAdapter);
        registerIntentFilter();
        registerReceiver(btReceiver, btFilter);
    }

    private void registerIntentFilter() {
        btFilter = new IntentFilter();
        btFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        btFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        btFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        btFilter.addAction(BluetoothDevice.ACTION_FOUND);
        btFilter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        btFilter.addAction(BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED);
    }

    private void initBinding() {
        switchButtonViewModel = new SwitchButtonViewModel(btAdapter);
        searchButtonViewModel = new SearchButtonViewModel(btAdapter);
        listItemViewModel = new BtDeviceListItemViewModel(btAdapter, deviceListAdapter);
        listItemViewModel.setListItemViewUpdata(new ListItemViewUpdata() {
            @Override
            public void showDialog(BtDeviceItem deviceItem) {
                processShowDialog(deviceItem);
            }
        });
        listItemViewModel.setNewProgressCreation(new NewProgressCreation() {
            @Override
            public void startNewActivity(Map<String, String> extraMap) {
//                Intent intent = new Intent(getApplicationContext(), DataTransmissionActivity.class);
//                for (Map.Entry<String, String> entry : extraMap.entrySet()) {
//                    intent.putExtra(entry.getKey(), entry.getValue());
//                }
//                intent.putExtra("systemInfo", systemInfo);
//                startActivity(intent);
            }
        });

        binding = DataBindingUtil.setContentView(MoniterActivity.this, R.layout.activity_moniter);
        binding.setSystemInfo(systemInfo);
        binding.setSwitchButton(switchButtonViewModel);
        binding.setSearchButton(searchButtonViewModel);
        binding.setDeviceListAdapter(deviceListAdapter);

        binding.lvShowDev.setOnItemClickListener(listItemViewModel);
        binding.lvShowDev.setOnItemLongClickListener(listItemViewModel);
    }

    private void processShowDialog(BtDeviceItem deviceItem) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MoniterActivity.this);
        builder.setIcon(R.drawable.icon_bt_image)
                .setMessage("是取消"+getString(R.string.Device_Name)+"为: "+deviceItem.getDeviceName()+"\n"
                        +getString(R.string.Device_Address)+"为: "+deviceItem.getDeviceAddr()+"\n"
                        +getString(R.string.Pairing));
        setPositiveButton(builder, deviceItem.getBtDevice());
        setNegativeButton(builder, deviceItem.getBtDevice())
                .create()
                .show();
    }

    private AlertDialog.Builder setPositiveButton(AlertDialog.Builder builder, final BluetoothDevice btDevice)
    {
        return builder.setPositiveButton(R.string.Select_Ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    BtPairing.removeBond(btDevice.getClass(), btDevice);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private AlertDialog.Builder setNegativeButton(AlertDialog.Builder builder, BluetoothDevice btDevice)
    {
        return builder.setNegativeButton(R.string.Select_Cancel, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

    }
}
