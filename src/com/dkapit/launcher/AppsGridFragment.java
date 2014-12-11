package com.dkapit.launcher;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.GridView;

import java.util.ArrayList;


public class AppsGridFragment extends GridFragment implements LoaderManager.LoaderCallbacks<ArrayList<AppModel>> {

    AppsListAdapter mAdapter;
    static AppsGridFragment aGrid;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setEmptyText("No Applications");

        mAdapter = new AppsListAdapter(getActivity());
        setGridAdapter(mAdapter);

        // till the data is loaded display a spinner
        setGridShown(false);

        // create the loader to load the apps list in background
        getLoaderManager().initLoader(0, null, this);
        
        aGrid = this;
    }

    @Override
    public Loader<ArrayList<AppModel>> onCreateLoader(int id, Bundle bundle) {
        return new AppsLoader(getActivity());
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<AppModel>> loader, ArrayList<AppModel> apps) {
        mAdapter.setData(apps);

        if (isResumed()) {
            setGridShown(true);
        } else {
            setGridShownNoAnimation(true);
        }
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<AppModel>> loader) {
        mAdapter.setData(null);
    }

    @Override
    public void onGridItemClick(GridView g, View v, int position, long id) {
        AppModel app = (AppModel) getGridAdapter().getItem(position);
        if (app != null) {
            Intent intent = getActivity().getPackageManager().getLaunchIntentForPackage(app.getApplicationPackageName());
            
            if (intent != null) {
            	if (app.getApplicationPackageName().equals("com.android.settings") && 
            			(getResources().getConfiguration().orientation == 2 && HomeActivity.vol_pressed)) {
            		startActivity(new Intent(getActivity(), HomeScreen.class));
            	}
            	else if (app.getApplicationPackageName().equals("com.android.settings") && !HomeActivity.vol_pressed) {
            		startActivity(HomeActivity.home);
            	}
//            	else if (!AppsLoader.accepted.contains(app.getApplicationPackageName())) {
//            		Intent honeypot = HomeActivity.honeypot;
//            		honeypot.putExtra("com.dkapit.launcher.AppsGridFragment", app.getApplicationPackageName());
//            		startActivity(honeypot);
//            	}
            	else
            		startActivity(intent);
            }
        }
    }
}
