package larva;


import java.util.*;
import larvaTools.LarvaController;
import larvaTools.TA.AutomatonInstance;
import larvaTools.TA.StateSkip;
import larvaTools.TA.Outcome;

import java.util.LinkedHashMap;
import java.io.PrintWriter;

public class _cls_script_30 implements _callable{

public static PrintWriter pw; 
public static _cls_script_30 root;
public static Channel channel_stateSkip = new Channel();
public static Channel channel_TAsToMain = new Channel();

public static LinkedHashMap<_cls_script_30,_cls_script_30> _cls_script_30_instances = new LinkedHashMap<_cls_script_30,_cls_script_30>();
static{
try{
pw = new PrintWriter("larvaRuntimeOutput//output_script_3.txt");

root = new _cls_script_30();
_cls_script_30_instances.put(root, root);
  root.initialisation();
}catch(Exception ex)
{ex.printStackTrace();}
}

_cls_script_30 parent; //to remain null - this class does not have a parent!
public static LarvaController __1;
public static LarvaController __0;
public static String state;
public static Object outcomeObj;
int no_automata = 1;
 public int instanceCount =0 ;
 public int currentState =0 ;

public static void initialize(){}
//inheritance could not be used because of the automatic call to super()
//when the constructor is called...we need to keep the SAME parent if this exists!

public _cls_script_30() {
}

public void initialisation() {

currentState =0 ;
instanceCount =1 ;
channel_stateSkip .send (new StateSkip (new AutomatonInstance (true ,true ),"main_to_start"));

}

public static _cls_script_30 _get_cls_script_30_inst() { synchronized(_cls_script_30_instances){
 return root;
}
}

public boolean equals(Object o) {
 if ((o instanceof _cls_script_30))
{return true;}
else
{return false;}
}

public int hashCode() {
return 1;
}

public void _call(String _info, int... _event){
synchronized(_cls_script_30_instances){
_performLogic_main(_info, _event);
}
}

public void _call_all_filtered(String _info, int... _event){

_cls_script_31[] a1 = new _cls_script_31[1];
synchronized(_cls_script_31._cls_script_31_instances){
a1 = _cls_script_31._cls_script_31_instances.keySet().toArray(a1);}
for (_cls_script_31 _inst : a1)
if (_inst != null){
_inst._call(_info, _event); 
_inst._call_all_filtered(_info, _event);
}
}

public static void _call_all(String _info, int... _event){

_cls_script_30[] a = new _cls_script_30[1];
synchronized(_cls_script_30_instances){
a = _cls_script_30_instances.keySet().toArray(a);}
for (_cls_script_30 _inst : a)

if (_inst != null) _inst._call(_info, _event);
}

public void _killThis(){
try{
if (--no_automata == 0){
synchronized(_cls_script_30_instances){
_cls_script_30_instances.remove(this);}
}
else if (no_automata < 0)
{throw new Exception("no_automata < 0!!");}
}catch(Exception ex){ex.printStackTrace();}
}

int _state_id_main = 2;

public void _performLogic_main(String _info, int... _event) {

if (0==1){}
else if (_state_id_main==1){
		if (1==0){}
		else if ((_occurredEvent(_event,7/*evt_LarvaReset*/))){
		
		_state_id_main = 2;//moving to state start
currentState =0 ;
instanceCount =1 ;
channel_stateSkip .send (new StateSkip (new AutomatonInstance (true ,true ),"main_to_start"));

		_goto_main(_info);
		}
}
else if (_state_id_main==0){
		if (1==0){}
		else if ((_occurredEvent(_event,9/*evt_LarvaStop*/))){
		instanceCount =0 ;

		_state_id_main = 1;//moving to state stopped
		_goto_main(_info);
		}
}
else if (_state_id_main==2){
		if (1==0){}
		else if ((_occurredEvent(_event,5/*evt_channelTAsToMain*/)) && (state .equals ("bad")&&--instanceCount ==0 )){
		
		_state_id_main = 0;//moving to state bad
currentState =-1 ;

		_goto_main(_info);
		}
		else if ((_occurredEvent(_event,9/*evt_LarvaStop*/))){
		instanceCount =0 ;

		_state_id_main = 1;//moving to state stopped
		_goto_main(_info);
		}
}
}

public void _goto_main(String _info){
 String state_format = _string_main(_state_id_main, 1);
 if (state_format.startsWith("!!!SYSTEM REACHED BAD STATE!!!")) {
_cls_script_30.pw.println("[main]MOVED ON METHODCALL: "+ _info +" TO STATE::> " + state_format);
_cls_script_30.pw.flush();
}
}

public String _string_main(int _state_id, int _mode){
switch(_state_id){
case 1: if (_mode == 0) return "stopped"; else return "stopped";
case 0: if (_mode == 0) return "bad"; else return "!!!SYSTEM REACHED BAD STATE!!! bad "+new _BadStateExceptionscript_3().toString()+" ";
case 2: if (_mode == 0) return "start"; else return "start";
default: return "!!!SYSTEM REACHED AN UNKNOWN STATE!!!";
}
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