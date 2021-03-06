package larva;


import java.util.*;
import larvaTools.LarvaController;
import larvaTools.TA.AutomatonInstance;
import larvaTools.TA.StateSkip;
import larvaTools.TA.Outcome;
import transactionsystem.UserInfo;
import transactionsystem.UserAccount;

import java.util.LinkedHashMap;
import java.io.PrintWriter;

public class _cls_script_11 implements _callable{

public static LinkedHashMap<_cls_script_11,_cls_script_11> _cls_script_11_instances = new LinkedHashMap<_cls_script_11,_cls_script_11>();

_cls_script_10 parent;
public static UserInfo ui;
public UserInfo _ui;
int no_automata = 0;

public static void initialize(){}
//inheritance could not be used because of the automatic call to super()
//when the constructor is called...we need to keep the SAME parent if this exists!

public _cls_script_11( UserInfo _ui) {
parent = _cls_script_10._get_cls_script_10_inst();
this._ui = _ui;
}

public void initialisation() {
}

public static _cls_script_11 _get_cls_script_11_inst( UserInfo _ui) { synchronized(_cls_script_11_instances){
_cls_script_11 _inst = new _cls_script_11( _ui);
if (_cls_script_11_instances.containsKey(_inst))
{
_cls_script_11 tmp = _cls_script_11_instances.get(_inst);
 return _cls_script_11_instances.get(_inst);
}
else
{
 _cls_script_11_instances.put(_inst,_inst);
 _inst.initialisation();
 return _inst;
}
}
}

public boolean equals(Object o) {
 if ((o instanceof _cls_script_11)
 && (_ui == null || _ui.equals(((_cls_script_11)o)._ui))
 && (parent == null || parent.equals(((_cls_script_11)o).parent)))
{return true;}
else
{return false;}
}

public int hashCode() {
return (_ui==null?1:_ui.hashCode()) *(parent==null?1:parent.hashCode()) *1;
}

public void _call(String _info, int... _event){
synchronized(_cls_script_11_instances){
}
}

public void _call_all_filtered(String _info, int... _event){

_cls_script_12[] a2 = new _cls_script_12[1];
synchronized(_cls_script_12._cls_script_12_instances){
a2 = _cls_script_12._cls_script_12_instances.keySet().toArray(a2);}
for (_cls_script_12 _inst : a2)
if (_inst != null
 && (_ui == null || _ui.equals(_inst.parent._ui))){
_inst._call(_info, _event); 
_inst._call_all_filtered(_info, _event);
}
}

public static void _call_all(String _info, int... _event){

_cls_script_11[] a = new _cls_script_11[1];
synchronized(_cls_script_11_instances){
a = _cls_script_11_instances.keySet().toArray(a);}
for (_cls_script_11 _inst : a)

if (_inst != null) _inst._call(_info, _event);
}

public void _killThis(){
try{
if (--no_automata == 0){
synchronized(_cls_script_11_instances){
_cls_script_11_instances.remove(this);}
}
else if (no_automata < 0)
{throw new Exception("no_automata < 0!!");}
}catch(Exception ex){ex.printStackTrace();}
}


public boolean _occurredEvent(int[] _events, int event){
for (int i:_events) if (i == event) return true;
return false;
}
void setClocks(final StateSkip ss, final Clock...clocks) {
final long timeouts[] = {  };
final Clock ssClocks[] = ss.getClocks();
if (ssClocks != null) {
for (int i = 0; i < ssClocks.length; i++) {
if (ssClocks[i] != null) {
if (ssClocks[i].paused) {
clocks[i].pause();
} else {
final long newValueLong = System.currentTimeMillis() - ssClocks[i].current_long();
clocks[i].registerDynamically(newValueLong + timeouts[i], newValueLong);
}
}
}
}
}
}