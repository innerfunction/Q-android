package q.innerfunction.com.test.utils;

import android.content.Context;

public class Color {

    String value;
    Context context;
    
    public Color(Context context){
        this.context=context;
    }
 
    public void setValue(String value){
        this.value = value;
    }
    
    public String getValue(){
        return value;
    }
    
    public boolean hasContext(){
        return context!=null;
    }
}
