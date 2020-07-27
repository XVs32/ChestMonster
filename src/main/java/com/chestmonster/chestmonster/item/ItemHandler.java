package com.chestmonster.chestmonster.item;

import net.minecraft.item.ItemStack;

import java.util.Comparator;
import java.util.List;

public class ItemHandler {

    public static List<ItemStack> stack(List<ItemStack> itemList){

        for(int i=0;i<itemList.size();i++){
            if( !itemList.get(i).isEmpty() && itemList.get(i).isStackable() ){
                for(int j = itemList.size() - 1;j>i;j--){
                    if( isSameItem(itemList.get(i),itemList.get(j)) ){
                        chestItemTransfer(itemList, i, j, 64);
                    }
                }
            }
        }

        return itemList;
    }

    public static List<ItemStack> sort(List<ItemStack> itemList){
        itemList.sort(new ItemHandler.ItemStackComparator());
        return itemList;
    }


    private static boolean isSameItem(ItemStack o1, ItemStack o2){
        return o1.getTranslationKey().equals(o2.getTranslationKey());
    }



    private static void chestItemTransfer(List<ItemStack> itemList, int o1, int o2, int amount){
        itemStacker(itemList.get(o1), itemList.get(o2),amount);
    }

    private static void itemStacker(ItemStack destination, ItemStack source, int amount){

        amount = Math.min(amount , destination.getMaxStackSize() - destination.getCount());
        amount = Math.min(amount , source.getCount());

        destination.setCount(destination.getCount() + amount);
        source.setCount(source.getCount() - amount);

    }



    static class ItemStackComparator implements Comparator {

        @Override
        public int compare(Object o1, Object o2) {
            String[] name1 = ( ((ItemStack) o1).getTranslationKey()).split("_|\\.");
            String[] name2 = ( ((ItemStack) o2).getTranslationKey()).split("_|\\.");

            int i = name1.length - 1;
            int j = name2.length - 1;

            while (true) {
                int result = name1[i].compareTo(name2[j]);
                if (result == 0) {
                    if (i == 0 && j == 0) {
                        return 0;
                    } else if (i == 0) {
                        return -1;
                    } else if (j == 0) {
                        return 1;
                    } else {
                        i--;
                        j--;
                    }
                } else {
                    return -result;
                }
            }
        }
    }

}

