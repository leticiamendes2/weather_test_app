package xero.test.weatherapp.view.permissions;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import xero.test.weatherapp.R;

/**
 * Created by Letícia on 12/10/2017.
 */

public class PermissionsFragment extends Fragment {

    @BindView(R.id.btnGivePermission)
    public TextView btnGivePermission;
    private PermissionsPresenter presenter = null;

    public void setPresenter(@NonNull PermissionsPresenter permissionsPresenter) {
        this.presenter = permissionsPresenter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_permissions, container, false);
        ButterKnife.bind(this, view);

        btnGivePermission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if (!presenter.hasPermissions()) {
                presenter.askPermission();
            }
            }
        });

        return view;
    }
}
