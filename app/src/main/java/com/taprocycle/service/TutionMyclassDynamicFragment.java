package com.taprocycle.service;

import android.os.Bundle;

public class TutionMyclassDynamicFragment {
    public static TutionMyclassDynamicFragment getInstance(int position2, String scid2) {
        Bundle bundle = new Bundle();
        bundle.putInt("pos", position2);
        bundle.putString("scid", scid2);
        TutionMyclassDynamicFragment tabFragment = new TutionMyclassDynamicFragment();
        tabFragment.setArguments(bundle);
        return tabFragment;
    }

    private void setArguments(Bundle bundle) {
    }
}

