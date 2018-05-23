package larva;


import java.util.*;
import larvaTools.LarvaController;
import larvaTools.RE.RegExpHolder;
import RE.lib.basic.Symbol;
import transactionsystem.UserInfo;
import transactionsystem.UserAccount;

import java.util.LinkedHashMap;
import java.io.PrintWriter;

public class _cls_script_20 implements _callable{

public static PrintWriter pw; 
public static _cls_script_20 root;

public static LinkedHashMap<_cls_script_20,_cls_script_20> _cls_script_20_instances = new LinkedHashMap<_cls_script_20,_cls_script_20>();
static{
try{
pw = new PrintWriter("larvaRuntimeOutput//output_script_2.txt");

root = new _cls_script_20();
_cls_script_20_instances.put(root, root);
  root.initialisation();
}catch(Exception ex)
{ex.printStackTrace();}
}

_cls_script_20 parent; //to remain null - this class does not have a parent!
public static LarvaController __1;
public static LarvaController __0;
public static RegExpHolder r;
public static int id;
int no_automata = 0;
 public int exprIndex =RegExpHolder .parseAndStore ("typedef ui : transactionsystem.UserInfo; typedef ua : transactionsystem.UserAccount; [p] deposit(ui,ua); {withdraw(ui,ua)}");
 public Symbol Sym_deposit_ui_ua =new Symbol ("deposit",Arrays .asList ("ui","ua"));
 public Symbol Sym_withdraw_ui_ua =new Symbol ("withdraw",Arrays .asList ("ui","ua"));

public static void initialize(){}
//inheritance could not be used because of the automatic call to super()
//when the constructor is called...we need to keep the SAME parent if this exists!

public _cls_script_20() {
}

public void initialisation() {
}

public static _cls_script_20 _get_cls_script_20_inst() { synchronized(_cls_script_20_instances){
 return root;
}
}

public boolean equals(Object o) {
 if ((o instanceof _cls_script_20))
{return true;}
else
{return false;}
}

public int hashCode() {
return 1;
}

public void _call(String _info, int... _event){
synchronized(_cls_script_20_instances){
}
}

public void _call_all_filtered(String _info, int... _event){

_cls_script_21[] a1 = new _cls_script_21[1];
synchronized(_cls_script_21._cls_script_21_instances){
a1 = _cls_script_21._cls_script_21_instances.keySet().toArray(a1);}
for (_cls_script_21 _inst : a1)
if (_inst != null){
_inst._call(_info, _event); 
_inst._call_all_filtered(_info, _event);
}
}

public static void _call_all(String _info, int... _event){

_cls_script_20[] a = new _cls_script_20[1];
synchronized(_cls_script_20_instances){
a = _cls_script_20_instances.keySet().toArray(a);}
for (_cls_script_20 _inst : a)

if (_inst != null) _inst._call(_info, _event);
}

public void _killThis(){
try{
if (--no_automata == 0){
synchronized(_cls_script_20_instances){
_cls_script_20_instances.remove(this);}
}
else if (no_automata < 0)
{throw new Exception("no_automata < 0!!");}
}catch(Exception ex){ex.printStackTrace();}
}


public boolean _occurredEvent(int[] _events, int event){
for (int i:_events) if (i == event) return true;
return false;
}
}