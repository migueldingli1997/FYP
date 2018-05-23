package larva;


import java.util.*;
import larvaTools.LarvaController;
import larvaTools.RE.RegExpHolder;
import RE.lib.basic.Symbol;
import transactionsystem.UserInfo;
import transactionsystem.UserAccount;

import java.util.LinkedHashMap;
import java.io.PrintWriter;

public class _cls_script_21 implements _callable{

public static LinkedHashMap<_cls_script_21,_cls_script_21> _cls_script_21_instances = new LinkedHashMap<_cls_script_21,_cls_script_21>();

_cls_script_20 parent;
public UserInfo _ui;
int no_automata = 0;

public static void initialize(){}
//inheritance could not be used because of the automatic call to super()
//when the constructor is called...we need to keep the SAME parent if this exists!

public _cls_script_21( UserInfo _ui) {
parent = _cls_script_20._get_cls_script_20_inst();
this._ui = _ui;
}

public void initialisation() {
}

public static _cls_script_21 _get_cls_script_21_inst( UserInfo _ui) { synchronized(_cls_script_21_instances){
_cls_script_21 _inst = new _cls_script_21( _ui);
if (_cls_script_21_instances.containsKey(_inst))
{
_cls_script_21 tmp = _cls_script_21_instances.get(_inst);
 return _cls_script_21_instances.get(_inst);
}
else
{
 _cls_script_21_instances.put(_inst,_inst);
 _inst.initialisation();
 return _inst;
}
}
}

public boolean equals(Object o) {
 if ((o instanceof _cls_script_21)
 && (_ui == null || _ui.equals(((_cls_script_21)o)._ui))
 && (parent == null || parent.equals(((_cls_script_21)o).parent)))
{return true;}
else
{return false;}
}

public int hashCode() {
return (_ui==null?1:_ui.hashCode()) *(parent==null?1:parent.hashCode()) *1;
}

public void _call(String _info, int... _event){
synchronized(_cls_script_21_instances){
}
}

public void _call_all_filtered(String _info, int... _event){

_cls_script_22[] a2 = new _cls_script_22[1];
synchronized(_cls_script_22._cls_script_22_instances){
a2 = _cls_script_22._cls_script_22_instances.keySet().toArray(a2);}
for (_cls_script_22 _inst : a2)
if (_inst != null
 && (_ui == null || _ui.equals(_inst.parent._ui))){
_inst._call(_info, _event); 
_inst._call_all_filtered(_info, _event);
}
}

public static void _call_all(String _info, int... _event){

_cls_script_21[] a = new _cls_script_21[1];
synchronized(_cls_script_21_instances){
a = _cls_script_21_instances.keySet().toArray(a);}
for (_cls_script_21 _inst : a)

if (_inst != null) _inst._call(_info, _event);
}

public void _killThis(){
try{
if (--no_automata == 0){
synchronized(_cls_script_21_instances){
_cls_script_21_instances.remove(this);}
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