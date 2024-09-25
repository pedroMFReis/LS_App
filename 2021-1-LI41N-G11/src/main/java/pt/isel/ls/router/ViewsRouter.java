package pt.isel.ls.router;

import pt.isel.ls.views.View;

import java.util.HashMap;

public class ViewsRouter {


    private HashMap<String, HashMap<Class, View>> router = new HashMap<>();

    public void addView(String textType, Class commandResult, View view) {
        HashMap<Class, View> hashMap = router.get(textType);
        if (hashMap == null) {
            hashMap = new HashMap<>();
        }
        hashMap.put(commandResult, view);
        router.put(textType, hashMap);
    }

    public View findView(String textType, Class commandResult) {
        HashMap<Class, View> hashMap = router.get(textType);
        if (hashMap != null) {
            return hashMap.get(commandResult);
        }
        return null;
    }
}
