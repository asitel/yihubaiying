package com.example.administrator.yihubaiyin;

import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;


public  class yonghuxinxi {
    HashMap hs=new HashMap();
    boolean bl=true;

  boolean  yonghuxinxi(String zhucename, String zhucepassword, String zhucexinbie){


        adduser add=new adduser();
        Set keyset=hs.keySet();
        Iterator it=keyset.iterator();

  while(it.hasNext()){
      String key=(String) it.next();
      if (key.equals(zhucename)==true){
          bl=false;

      }

  }
  if (bl==true){
    hs.put(zhucename,add.adduser(zhucepassword,zhucexinbie));}

   return bl;
}

boolean denglujiance(String jiancename ,String jiancepassword){
     boolean bl2=false;
boolean bl3=hs.containsKey(jiancename);


      if (bl3==true){
          Iterator it=((ArrayList)hs.get(jiancename)).iterator();
          String s=(String) it.next();
          if (s.equals(jiancepassword)==true){
            bl2=true;

          }
      }

return bl2;

}

}
class adduser{


    ArrayList  adduser(String password,String xinbie){
        ArrayList arrayList=new ArrayList();

        arrayList.add(password);
        arrayList.add(xinbie);
        return arrayList;

    }
}

class dengluxinxi{
    String dlname;
    String dlpassword;
   void getdenluxinxi(String s1,String s2){
        this.dlname=s1;
        this.dlpassword=s2;
    }
  String  getxinbei(){
       return "";
    }

  String  getdlname(){
        return this.dlname;
    }
    String  getdlpassword(){
        return this.dlpassword;
    }
}