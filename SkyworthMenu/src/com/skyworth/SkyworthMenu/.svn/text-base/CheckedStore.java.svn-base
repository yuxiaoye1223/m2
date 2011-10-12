package com.skyworth.SkyworthMenu;

import java.util.ArrayList;

class CheckedItem {
    String str_id;
    int checked_id;
}

public class CheckedStore {
    private ArrayList<CheckedItem> tmp_list = new ArrayList<CheckedItem>();
    public int getCheckedIDIndex(String str_id) {
        int i;

        for (i = 0; i < tmp_list.size(); i++) {
            if (tmp_list.get(i).str_id.equals(str_id))
                return i;
        }

        return -1;
    }

    public int getCheckedIDValue(String str_id) {
        int i = getCheckedIDIndex(str_id);

        if (i >= 0)
            return tmp_list.get(i).checked_id;
        else
            return 0;
    }

    public void setCheckedIDValue(String str_id, int checked_id) {
        int i = getCheckedIDIndex(str_id);

        if (i >= 0) {
            tmp_list.get(i).checked_id = checked_id;
        } else {
            CheckedItem tmp_item = new CheckedItem();
            tmp_item.str_id = str_id;
            tmp_item.checked_id = checked_id;
            tmp_list.add(tmp_item);
        }
    }
}