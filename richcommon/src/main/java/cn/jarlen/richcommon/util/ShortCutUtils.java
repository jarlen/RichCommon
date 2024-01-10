package cn.jarlen.richcommon.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;

import androidx.annotation.DrawableRes;
import androidx.core.content.pm.ShortcutInfoCompat;
import androidx.core.content.pm.ShortcutManagerCompat;
import androidx.core.graphics.drawable.IconCompat;

import java.util.List;

/**
 * @author jarlen
 * @date 2019/10/17
 * 创建应用快捷方式工具类
 */
public class ShortCutUtils {

    public static final String ACTION_FROM_SHORTCUT = "action_from_shortcut";

    /**
     * 添加shortcut
     *
     * @param context 上下文
     * @param option  shortcut配置选项
     */
    public static void addShortCut(Context context, ShortCutOption option) {
        if (!ShortcutManagerCompat.isRequestPinShortcutSupported(context)) {
            return;
        }
        Intent shortcutInfoIntent = new Intent(context, option.getShortCutClass());
        /*action必须设置，不然报错*/
        shortcutInfoIntent.setAction(Intent.ACTION_VIEW);
        shortcutInfoIntent.setAction(ACTION_FROM_SHORTCUT);
        ShortcutInfoCompat info = new ShortcutInfoCompat.Builder(context, option.getShortCutClass().getName())
                .setIcon(IconCompat.createWithResource(context, option.getShortCutIconRes()))
                .setShortLabel(option.getShortLabel())
                .setIntent(shortcutInfoIntent)
                .build();
        ShortcutManagerCompat.requestPinShortcut(context, info, null);
    }

    /**
     * android.os.Build.VERSION.SDK_INT >= 25
     * 是否已经有shortcut添加
     *
     * @param context
     * @param option
     * @return true代表添加过了
     */
    public static boolean hasShortCutOverO(Context context, ShortCutOption option) {
        boolean hasShortCut = false;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N_MR1) {
            ShortcutManager shortcutManager = (ShortcutManager) context.getSystemService(Context.SHORTCUT_SERVICE);
            List<ShortcutInfo> shortcutInfoList = shortcutManager.getPinnedShortcuts();
            if (shortcutInfoList != null) {
                for (ShortcutInfo info : shortcutInfoList) {
                    if (info.getId().equals(option.getShortCutClass().getName())) {
                        hasShortCut = true;
                        break;
                    }
                }
            }
        }
        return hasShortCut;
    }

    public static class ShortCutOption {
        private String shortLabel;
        private @DrawableRes
        int shortCutIconRes;
        private Class<?> shortCutClass;

        public String getShortLabel() {
            return shortLabel;
        }

        public void setShortLabel(String shortLabel) {
            this.shortLabel = shortLabel;
        }

        public int getShortCutIconRes() {
            return shortCutIconRes;
        }

        public void setShortCutIconRes(int shortCutIconRes) {
            this.shortCutIconRes = shortCutIconRes;
        }

        public Class<?> getShortCutClass() {
            return shortCutClass;
        }

        public void setShortCutClass(Class<?> shortCutClass) {
            this.shortCutClass = shortCutClass;
        }
    }
}
