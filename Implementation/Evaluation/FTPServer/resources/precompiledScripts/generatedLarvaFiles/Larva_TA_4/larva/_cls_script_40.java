package larva;


import java.util.*;
import larvaTools.LarvaController;
import larvaTools.TA.AutomatonInstance;
import larvaTools.TA.StateSkip;
import larvaTools.TA.Outcome;
import com.guichaguri.minimalftp.handler.ConnectionHandler;

import java.util.LinkedHashMap;
import java.io.PrintWriter;

public class _cls_script_40 implements _callable{

public static PrintWriter pw; 
public static _cls_script_40 root;

public static LinkedHashMap<_cls_script_40,_cls_script_40> _cls_script_40_instances = new LinkedHashMap<_cls_script_40,_cls_script_40>();
static{
try{
pw = new PrintWriter("larvaRuntimeOutput//output_script_4.txt");

root = new _cls_script_40();
_cls_script_40_instances.put(root, root);
  root.initialisation();
}catch(Exception ex)
{ex.printStackTrace();}
}

_cls_script_40 parent; //to remain null - this class does not have a parent!
int no_automata = 0;

public static void initialize(){}
//inheritance could not be used because of the automatic call to super()
//when the constructor is called...we need to keep the SAME parent if this exists!

public _cls_script_40() {
}

public void initialisation() {
}

public static _cls_script_40 _get_cls_script_40_inst() { synchronized(_cls_script_40_instances){
 return root;
}
}

public boolean equals(Object o) {
 if ((o instanceof _cls_script_40))
{return true;}
else
{return false;}
}

public int hashCode() {
return 1;
}

public void _call(String _info, int... _event){
synchronized(_cls_script_40_instances){
}
}

public void _call_all_filtered(String _info, int... _event){

_cls_script_41[] a1 = new _cls_script_41[1];
synchronized(_cls_script_41._cls_script_41_instances){
a1 = _cls_script_41._cls_script_41_instances.keySet().toArray(a1);}
for (_cls_script_41 _inst : a1)
if (_inst != null){
_inst._call(_info, _event); 
_inst._call_all_filtered(_info, _event);
}
}

public static void _call_all(String _info, int... _event){

_cls_script_40[] a = new _cls_script_40[1];
synchronized(_cls_script_40_instances){
a = _cls_script_40_instances.keySet().toArray(a);}
for (_cls_script_40 _inst : a)

if (_inst != null) _inst._call(_info, _event);
}

public void _killThis(){
try{
if (--no_automata == 0){
synchronized(_cls_script_40_instances){
_cls_script_40_instances.remove(this);}
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