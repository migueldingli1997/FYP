package larva;


import java.util.*;
import larvaTools.LarvaController;
import larvaTools.TA.AutomatonInstance;
import larvaTools.TA.StateSkip;
import larvaTools.TA.Outcome;

import java.util.LinkedHashMap;
import java.io.PrintWriter;

public class _cls_script_11 implements _callable{

public static LinkedHashMap<_cls_script_11,_cls_script_11> _cls_script_11_instances = new LinkedHashMap<_cls_script_11,_cls_script_11>();

_cls_script_10 parent;
public static StateSkip ss;
public static Object ssObj;
public AutomatonInstance ta;
int no_automata = 1;
public Clock clock_0 = new Clock(this,"clock_0");
public Clock clock_1 = new Clock(this,"clock_1");

public static void initialize(){}
//inheritance could not be used because of the automatic call to super()
//when the constructor is called...we need to keep the SAME parent if this exists!

public _cls_script_11( AutomatonInstance ta) {
parent = _cls_script_10._get_cls_script_10_inst();
clock_0.register(4000l);
clock_1.register(7000l);
this.ta = ta;
}

public void initialisation() {
   clock_0.reset();
   clock_1.reset();

if (ta .shouldPauseClocks ){ta .shouldPauseClocks =false ;
}if (ta .shouldPerformStartActions ){ta .shouldPerformStartActions =false ;
if (true ){parent.instanceCount ++;
parent.channel_stateSkip .send (new StateSkip (new AutomatonInstance (false ),"start_to_S0",new Clock []{null ,clock_1 }));
}if (true ){parent.instanceCount ++;
parent.channel_stateSkip .send (new StateSkip (new AutomatonInstance (false ),"start_to_S7",new Clock []{clock_0 ,null }));
}parent.channel_TAsToMain .send (new Outcome ("bad"));
_killThis ();
}
}

public static _cls_script_11 _get_cls_script_11_inst( AutomatonInstance ta) { synchronized(_cls_script_11_instances){
_cls_script_11 _inst = new _cls_script_11( ta);
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
 && (ta == null || ta.equals(((_cls_script_11)o).ta))
 && (parent == null || parent.equals(((_cls_script_11)o).parent)))
{return true;}
else
{return false;}
}

public int hashCode() {
return (ta==null?1:ta.hashCode()) *(parent==null?1:parent.hashCode()) *1;
}

public void _call(String _info, int... _event){
synchronized(_cls_script_11_instances){
_performLogic_property(_info, _event);
}
}

public void _call_all_filtered(String _info, int... _event){
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
synchronized(clock_0){
clock_0.off();
clock_0._inst = null;
clock_0 = null;}
synchronized(clock_1){
clock_1.off();
clock_1._inst = null;
clock_1 = null;}
}
else if (no_automata < 0)
{throw new Exception("no_automata < 0!!");}
}catch(Exception ex){ex.printStackTrace();}
}

int _state_id_property = 18;

public void _performLogic_property(String _info, int... _event) {

if (0==1){}
else if (_state_id_property==5){
		if (1==0){}
		else if ((_occurredEvent(_event,4/*evt_deposit*/)) && (clock_0 .current ()>=2 &&clock_0 .current ()<4 )){
		clock_0 .pause ();

		_state_id_property = 8;//moving to state S6
		_goto_property(_info);
		}
		else if ((_occurredEvent(_event,10/*evt_anySymbol*/))){
		
		_state_id_property = 17;//moving to state bad
parent.channel_TAsToMain .send (new Outcome ("bad"));
_killThis ();

		_goto_property(_info);
		}
		else if ((_occurredEvent(_event,25/*evt_anyTimeout*/))){
		
		_state_id_property = 17;//moving to state bad
parent.channel_TAsToMain .send (new Outcome ("bad"));
_killThis ();

		_goto_property(_info);
		}
}
else if (_state_id_property==6){
		if (1==0){}
		else if ((_occurredEvent(_event,2/*evt_openSession*/))){
		
		_state_id_property = 7;//moving to state S5
		_goto_property(_info);
		}
		else if ((_occurredEvent(_event,10/*evt_anySymbol*/))){
		
		_state_id_property = 17;//moving to state bad
parent.channel_TAsToMain .send (new Outcome ("bad"));
_killThis ();

		_goto_property(_info);
		}
		else if ((_occurredEvent(_event,25/*evt_anyTimeout*/))){
		
		_state_id_property = 17;//moving to state bad
parent.channel_TAsToMain .send (new Outcome ("bad"));
_killThis ();

		_goto_property(_info);
		}
}
else if (_state_id_property==7){
		if (1==0){}
		else if ((_occurredEvent(_event,6/*evt_withdraw*/)) && (clock_0 .current ()>=2 &&clock_0 .current ()<4 )){
		clock_0 .pause ();

		_state_id_property = 8;//moving to state S6
		_goto_property(_info);
		}
		else if ((_occurredEvent(_event,10/*evt_anySymbol*/))){
		
		_state_id_property = 17;//moving to state bad
parent.channel_TAsToMain .send (new Outcome ("bad"));
_killThis ();

		_goto_property(_info);
		}
		else if ((_occurredEvent(_event,25/*evt_anyTimeout*/))){
		
		_state_id_property = 17;//moving to state bad
parent.channel_TAsToMain .send (new Outcome ("bad"));
_killThis ();

		_goto_property(_info);
		}
}
else if (_state_id_property==8){
		if (1==0){}
		else if ((_occurredEvent(_event,10/*evt_anySymbol*/))){
		
		_state_id_property = 17;//moving to state bad
parent.channel_TAsToMain .send (new Outcome ("bad"));
_killThis ();

		_goto_property(_info);
		}
		else if ((_occurredEvent(_event,25/*evt_anyTimeout*/))){
		
		_state_id_property = 17;//moving to state bad
parent.channel_TAsToMain .send (new Outcome ("bad"));
_killThis ();

		_goto_property(_info);
		}
}
else if (_state_id_property==9){
		if (1==0){}
		else if ((_occurredEvent(_event,0/*evt_makeGoldUser*/))){
		
		_state_id_property = 16;//moving to state S8
if (true ){parent.instanceCount ++;
parent.channel_stateSkip .send (new StateSkip (new AutomatonInstance (false ),"S8_to_S9",new Clock []{clock_0 ,clock_1 }));
}if (true ){parent.instanceCount ++;
parent.channel_stateSkip .send (new StateSkip (new AutomatonInstance (false ),"S8_to_S11",new Clock []{clock_0 ,clock_1 }));
}parent.channel_TAsToMain .send (new Outcome ("bad"));
_killThis ();

		_goto_property(_info);
		}
		else if ((_occurredEvent(_event,10/*evt_anySymbol*/))){
		
		_state_id_property = 17;//moving to state bad
parent.channel_TAsToMain .send (new Outcome ("bad"));
_killThis ();

		_goto_property(_info);
		}
		else if ((_occurredEvent(_event,25/*evt_anyTimeout*/))){
		
		_state_id_property = 17;//moving to state bad
parent.channel_TAsToMain .send (new Outcome ("bad"));
_killThis ();

		_goto_property(_info);
		}
}
else if (_state_id_property==10){
		if (1==0){}
		else if ((_occurredEvent(_event,8/*evt_closeSession*/))){
		
		_state_id_property = 11;//moving to state S10
		_goto_property(_info);
		}
		else if ((_occurredEvent(_event,10/*evt_anySymbol*/))){
		
		_state_id_property = 17;//moving to state bad
parent.channel_TAsToMain .send (new Outcome ("bad"));
_killThis ();

		_goto_property(_info);
		}
		else if ((_occurredEvent(_event,25/*evt_anyTimeout*/))){
		
		_state_id_property = 17;//moving to state bad
parent.channel_TAsToMain .send (new Outcome ("bad"));
_killThis ();

		_goto_property(_info);
		}
}
else if (_state_id_property==18){
		if (1==0){}
		else if ((_occurredEvent(_event,26/*evt_channelStateSkip*/)) && (ss .getDestination ().equals ("start_to_S0"))){
		setClocks (ss ,clock_0 ,clock_1 );

		_state_id_property = 3;//moving to state S0
		_goto_property(_info);
		}
		else if ((_occurredEvent(_event,26/*evt_channelStateSkip*/)) && (ss .getDestination ().equals ("start_to_S7"))){
		setClocks (ss ,clock_0 ,clock_1 );

		_state_id_property = 9;//moving to state S7
		_goto_property(_info);
		}
		else if ((_occurredEvent(_event,26/*evt_channelStateSkip*/)) && (ss .getDestination ().equals ("S1_to_S2"))){
		setClocks (ss ,clock_0 ,clock_1 );

		_state_id_property = 4;//moving to state S2
		_goto_property(_info);
		}
		else if ((_occurredEvent(_event,26/*evt_channelStateSkip*/)) && (ss .getDestination ().equals ("S1_to_S4"))){
		setClocks (ss ,clock_0 ,clock_1 );

		_state_id_property = 6;//moving to state S4
		_goto_property(_info);
		}
		else if ((_occurredEvent(_event,26/*evt_channelStateSkip*/)) && (ss .getDestination ().equals ("S8_to_S9"))){
		setClocks (ss ,clock_0 ,clock_1 );

		_state_id_property = 10;//moving to state S9
		_goto_property(_info);
		}
		else if ((_occurredEvent(_event,26/*evt_channelStateSkip*/)) && (ss .getDestination ().equals ("S8_to_S11"))){
		setClocks (ss ,clock_0 ,clock_1 );

		_state_id_property = 12;//moving to state S11
		_goto_property(_info);
		}
		else if ((_occurredEvent(_event,26/*evt_channelStateSkip*/)) && (ss .getDestination ().equals ("main_to_start"))){
		setClocks (ss ,clock_0 ,clock_1 );

		_state_id_property = 18;//moving to state start
if (ta .shouldPauseClocks ){ta .shouldPauseClocks =false ;
}if (ta .shouldPerformStartActions ){ta .shouldPerformStartActions =false ;
if (true ){parent.instanceCount ++;
parent.channel_stateSkip .send (new StateSkip (new AutomatonInstance (false ),"start_to_S0",new Clock []{null ,clock_1 }));
}if (true ){parent.instanceCount ++;
parent.channel_stateSkip .send (new StateSkip (new AutomatonInstance (false ),"start_to_S7",new Clock []{clock_0 ,null }));
}parent.channel_TAsToMain .send (new Outcome ("bad"));
_killThis ();
}
		_goto_property(_info);
		}
}
else if (_state_id_property==12){
		if (1==0){}
		else if ((_occurredEvent(_event,8/*evt_closeSession*/))){
		
		_state_id_property = 13;//moving to state S12
		_goto_property(_info);
		}
		else if ((_occurredEvent(_event,10/*evt_anySymbol*/))){
		
		_state_id_property = 17;//moving to state bad
parent.channel_TAsToMain .send (new Outcome ("bad"));
_killThis ();

		_goto_property(_info);
		}
		else if ((_occurredEvent(_event,25/*evt_anyTimeout*/))){
		
		_state_id_property = 17;//moving to state bad
parent.channel_TAsToMain .send (new Outcome ("bad"));
_killThis ();

		_goto_property(_info);
		}
}
else if (_state_id_property==11){
		if (1==0){}
		else if ((_occurredEvent(_event,4/*evt_deposit*/)) && (clock_1 .current ()>=5 &&clock_1 .current ()<7 )){
		clock_1 .pause ();

		_state_id_property = 14;//moving to state S13
		_goto_property(_info);
		}
		else if ((_occurredEvent(_event,10/*evt_anySymbol*/))){
		
		_state_id_property = 17;//moving to state bad
parent.channel_TAsToMain .send (new Outcome ("bad"));
_killThis ();

		_goto_property(_info);
		}
		else if ((_occurredEvent(_event,25/*evt_anyTimeout*/))){
		
		_state_id_property = 17;//moving to state bad
parent.channel_TAsToMain .send (new Outcome ("bad"));
_killThis ();

		_goto_property(_info);
		}
}
else if (_state_id_property==14){
		if (1==0){}
		else if ((_occurredEvent(_event,10/*evt_anySymbol*/))){
		
		_state_id_property = 17;//moving to state bad
parent.channel_TAsToMain .send (new Outcome ("bad"));
_killThis ();

		_goto_property(_info);
		}
		else if ((_occurredEvent(_event,25/*evt_anyTimeout*/))){
		
		_state_id_property = 17;//moving to state bad
parent.channel_TAsToMain .send (new Outcome ("bad"));
_killThis ();

		_goto_property(_info);
		}
}
else if (_state_id_property==13){
		if (1==0){}
		else if ((_occurredEvent(_event,6/*evt_withdraw*/)) && (clock_1 .current ()>=5 &&clock_1 .current ()<7 )){
		clock_1 .pause ();

		_state_id_property = 14;//moving to state S13
		_goto_property(_info);
		}
		else if ((_occurredEvent(_event,10/*evt_anySymbol*/))){
		
		_state_id_property = 17;//moving to state bad
parent.channel_TAsToMain .send (new Outcome ("bad"));
_killThis ();

		_goto_property(_info);
		}
		else if ((_occurredEvent(_event,25/*evt_anyTimeout*/))){
		
		_state_id_property = 17;//moving to state bad
parent.channel_TAsToMain .send (new Outcome ("bad"));
_killThis ();

		_goto_property(_info);
		}
}
else if (_state_id_property==3){
		if (1==0){}
		else if ((_occurredEvent(_event,0/*evt_makeGoldUser*/))){
		
		_state_id_property = 15;//moving to state S1
if (true ){parent.instanceCount ++;
parent.channel_stateSkip .send (new StateSkip (new AutomatonInstance (false ),"S1_to_S2",new Clock []{clock_0 ,clock_1 }));
}if (true ){parent.instanceCount ++;
parent.channel_stateSkip .send (new StateSkip (new AutomatonInstance (false ),"S1_to_S4",new Clock []{clock_0 ,clock_1 }));
}parent.channel_TAsToMain .send (new Outcome ("bad"));
_killThis ();

		_goto_property(_info);
		}
		else if ((_occurredEvent(_event,10/*evt_anySymbol*/))){
		
		_state_id_property = 17;//moving to state bad
parent.channel_TAsToMain .send (new Outcome ("bad"));
_killThis ();

		_goto_property(_info);
		}
		else if ((_occurredEvent(_event,25/*evt_anyTimeout*/))){
		
		_state_id_property = 17;//moving to state bad
parent.channel_TAsToMain .send (new Outcome ("bad"));
_killThis ();

		_goto_property(_info);
		}
}
else if (_state_id_property==4){
		if (1==0){}
		else if ((_occurredEvent(_event,2/*evt_openSession*/))){
		
		_state_id_property = 5;//moving to state S3
		_goto_property(_info);
		}
		else if ((_occurredEvent(_event,10/*evt_anySymbol*/))){
		
		_state_id_property = 17;//moving to state bad
parent.channel_TAsToMain .send (new Outcome ("bad"));
_killThis ();

		_goto_property(_info);
		}
		else if ((_occurredEvent(_event,25/*evt_anyTimeout*/))){
		
		_state_id_property = 17;//moving to state bad
parent.channel_TAsToMain .send (new Outcome ("bad"));
_killThis ();

		_goto_property(_info);
		}
}
}

public void _goto_property(String _info){
 String state_format = _string_property(_state_id_property, 1);
 if (state_format.startsWith("!!!SYSTEM REACHED BAD STATE!!!")) {
_cls_script_10.pw.println("[property]MOVED ON METHODCALL: "+ _info +" TO STATE::> " + state_format);
_cls_script_10.pw.flush();
}
}

public String _string_property(int _state_id, int _mode){
switch(_state_id){
case 5: if (_mode == 0) return "S3"; else return "S3";
case 6: if (_mode == 0) return "S4"; else return "S4";
case 7: if (_mode == 0) return "S5"; else return "S5";
case 8: if (_mode == 0) return "S6"; else return "S6";
case 9: if (_mode == 0) return "S7"; else return "S7";
case 17: if (_mode == 0) return "bad"; else return "bad";
case 16: if (_mode == 0) return "S8"; else return "S8";
case 10: if (_mode == 0) return "S9"; else return "S9";
case 18: if (_mode == 0) return "start"; else return "start";
case 12: if (_mode == 0) return "S11"; else return "S11";
case 11: if (_mode == 0) return "S10"; else return "S10";
case 14: if (_mode == 0) return "S13"; else return "S13";
case 13: if (_mode == 0) return "S12"; else return "S12";
case 3: if (_mode == 0) return "S0"; else return "S0";
case 15: if (_mode == 0) return "S1"; else return "S1";
case 4: if (_mode == 0) return "S2"; else return "S2";
default: return "!!!SYSTEM REACHED AN UNKNOWN STATE!!!";
}
}

public boolean _occurredEvent(int[] _events, int event){
for (int i:_events) if (i == event) return true;
return false;
}
void setClocks(final StateSkip ss, final Clock...clocks) {
final long timeouts[] = { 4000, 7000 };
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