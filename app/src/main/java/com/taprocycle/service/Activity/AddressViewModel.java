package com.taprocycle.service.Activity;

import androidx.lifecycle.ViewModel;

public class AddressViewModel extends ViewModel {
    private int selectedDeliveryAddressPosition = -1;

    public int getSelectedDeliveryAddressPosition() {
        return selectedDeliveryAddressPosition;
    }

    public void setSelectedDeliveryAddressPosition(int position) {
        selectedDeliveryAddressPosition = position;
    }
}


