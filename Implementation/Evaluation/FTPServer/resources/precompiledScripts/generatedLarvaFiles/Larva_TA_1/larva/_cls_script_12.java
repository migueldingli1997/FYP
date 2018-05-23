package larva;


import java.util.*;
import larvaTools.LarvaController;
import larvaTools.TA.AutomatonInstance;
import larvaTools.TA.StateSkip;
import larvaTools.TA.Outcome;
import com.guichaguri.minimalftp.FTPConnection;

import java.util.LinkedHashMap;
import java.io.PrintWriter;

public class _cls_script_12 implements _callable{

public static LinkedHashMap<_cls_script_12,_cls_script_12> _cls_script_12_instances = new LinkedHashMap<_cls_script_12,_cls_script_12>();

_cls_script_11 parent;
public static StateSkip ss;
public static Object ssObj;
public AutomatonInstance ta;
int no_automata = 1;
public Clock clock_0 = new Clock(this,"clock_0");

public static void initialize(){}
//inheritance could not be used because of the automatic call to super()
//when the constructor is called...we need to keep the SAME parent if this exists!

public _cls_script_12( AutomatonInstance ta,FTPConnection _ftpC) {
parent = _cls_script_11._get_cls_script_11_inst( _ftpC);
clock_0.register(1000l);
this.ta = ta;
}

public void initialisation() {
   clock_0.reset();

if (ta .shouldPauseClocks ){clock_0 .pause ();
ta .shouldPauseClocks =false ;
}if (ta .shouldPerformStartActions ){ta .shouldPerformStartActions =false ;
if (true ){parent.instanceCount ++;
parent.channel_stateSkip .send (new StateSkip (new AutomatonInstance (false ),"start_to_S0",new Clock []{clock_0 },new Object []{parent._ftpC }));
}if (true ){parent.instanceCount ++;
parent.channel_stateSkip .send (new StateSkip (new AutomatonInstance (false ),"start_to_S3",new Clock []{clock_0 },new Object []{parent._ftpC }));
}}
}

public static _cls_script_12 _get_cls_script_12_inst( AutomatonInstance ta,FTPConnection _ftpC) { synchronized(_cls_script_12_instances){
_cls_script_12 _inst = new _cls_script_12( ta,_ftpC);
if (_cls_script_12_instances.containsKey(_inst))
{
_cls_script_12 tmp = _cls_script_12_instances.get(_inst);
 return _cls_script_12_instances.get(_inst);
}
else
{
 _cls_script_12_instances.put(_inst,_inst);
 _inst.initialisation();
 return _inst;
}
}
}

public boolean equals(Object o) {
 if ((o instanceof _cls_script_12)
 && (ta == null || ta.equals(((_cls_script_12)o).ta))
 && (parent == null || parent.equals(((_cls_script_12)o).parent)))
{return true;}
else
{return false;}
}

public int hashCode() {
return (ta==null?1:ta.hashCode()) *(parent==null?1:parent.hashCode()) *1;
}

public void _call(String _info, int... _event){
synchronized(_cls_script_12_instances){
_performLogic_property(_info, _event);
}
}

public void _call_all_filtered(String _info, int... _event){
}

public static void _call_all(String _info, int... _event){

_cls_script_12[] a = new _cls_script_12[1];
synchronized(_cls_script_12_instances){
a = _cls_script_12_instances.keySet().toArray(a);}
for (_cls_script_12 _inst : a)

if (_inst != null) _inst._call(_info, _event);
}

public void _killThis(){
try{
if (--no_automata == 0){
synchronized(_cls_script_12_instances){
_cls_script_12_instances.remove(this);}
synchronized(clock_0){
clock_0.off();
clock_0._inst = null;
clock_0 = null;}
}
else if (no_automata < 0)
{throw new Exception("no_automata < 0!!");}
}catch(Exception ex){ex.printStackTrace();}
}

int _state_id_property = 9;

public void _performLogic_property(String _info, int... _event) {

if (0==1){}
else if (_state_id_property==5){
		if (1==0){}
		else if ((_occurredEvent(_event,4/*evt_anySymbol_ftpC*/))){
		
		_state_id_property = 8;//moving to state bad
parent.channel_TAsToMain .send (new Outcome ("bad",new Object []{parent._ftpC }));
_killThis ();

		_goto_property(_info);
		}
		else if ((_occurredEvent(_event,11/*evt_anyTimeout*/))){
		
		_state_id_property = 8;//moving to state bad
parent.channel_TAsToMain .send (new Outcome ("bad",new Object []{parent._ftpC }));
_killThis ();

		_goto_property(_info);
		}
}
else if (_state_id_property==9){
		if (1==0){}
		else if ((_occurredEvent(_event,0/*evt_sendResponse_ftpC*/))){
		
		_state_id_property = 6;//moving to state S4
if (true ){parent.instanceCount ++;
parent.channel_stateSkip .send (new StateSkip (new AutomatonInstance (true ),"S4_to_start",new Clock []{clock_0 },new Object []{parent._ftpC }));
}if (true ){parent.instanceCount ++;
parent.channel_stateSkip .send (new StateSkip (new AutomatonInstance (false ),"S4_to_S0",new Clock []{clock_0 },new Object []{parent._ftpC }));
}parent.channel_TAsToMain .send (new Outcome ("bad",new Object []{parent._ftpC }));
_killThis ();

		_goto_property(_info);
		}
		else if ((_occurredEvent(_event,12/*evt_channelStateSkip*/)) && (ss .getDestination ().equals ("start_to_S0"))){
		setClocks (ss ,clock_0 );

		_state_id_property = 2;//moving to state S0
		_goto_property(_info);
		}
		else if ((_occurredEvent(_event,12/*evt_channelStateSkip*/)) && (ss .getDestination ().equals ("start_to_S3"))){
		setClocks (ss ,clock_0 );

		_state_id_property = 5;//moving to state S3
		_goto_property(_info);
		}
		else if ((_occurredEvent(_event,12/*evt_channelStateSkip*/)) && (ss .getDestination ().equals ("S4_to_start"))){
		setClocks (ss ,clock_0 );

		_state_id_property = 9;//moving to state start
if (ta .shouldPauseClocks ){clock_0 .pause ();
ta .shouldPauseClocks =false ;
}if (ta .shouldPerformStartActions ){ta .shouldPerformStartActions =false ;
if (true ){parent.instanceCount ++;
parent.channel_stateSkip .send (new StateSkip (new AutomatonInstance (false ),"start_to_S0",new Clock []{clock_0 },new Object []{parent._ftpC }));
}if (true ){parent.instanceCount ++;
parent.channel_stateSkip .send (new StateSkip (new AutomatonInstance (false ),"start_to_S3",new Clock []{clock_0 },new Object []{parent._ftpC }));
}}
		_goto_property(_info);
		}
		else if ((_occurredEvent(_event,12/*evt_channelStateSkip*/)) && (ss .getDestination ().equals ("S4_to_S0"))){
		setClocks (ss ,clock_0 );

		_state_id_property = 2;//moving to state S0
		_goto_property(_info);
		}
		else if ((_occurredEvent(_event,12/*evt_channelStateSkip*/)) && (ss .getDestination ().equals ("S5_to_S2"))){
		setClocks (ss ,clock_0 );

		_state_id_property = 4;//moving to state S2
		_goto_property(_info);
		}
		else if ((_occurredEvent(_event,12/*evt_channelStateSkip*/)) && (ss .getDestination ().equals ("S5_to_start"))){
		setClocks (ss ,clock_0 );

		_state_id_property = 9;//moving to state start
if (ta .shouldPauseClocks ){clock_0 .pause ();
ta .shouldPauseClocks =false ;
}if (ta .shouldPerformStartActions ){ta .shouldPerformStartActions =false ;
if (true ){parent.instanceCount ++;
parent.channel_stateSkip .send (new StateSkip (new AutomatonInstance (false ),"start_to_S0",new Clock []{clock_0 },new Object []{parent._ftpC }));
}if (true ){parent.instanceCount ++;
parent.channel_stateSkip .send (new StateSkip (new AutomatonInstance (false ),"start_to_S3",new Clock []{clock_0 },new Object []{parent._ftpC }));
}}
		_goto_property(_info);
		}
		else if ((_occurredEvent(_event,4/*evt_anySymbol_ftpC*/))){
		
		_state_id_property = 8;//moving to state bad
parent.channel_TAsToMain .send (new Outcome ("bad",new Object []{parent._ftpC }));
_killThis ();

		_goto_property(_info);
		}
		else if ((_occurredEvent(_event,11/*evt_anyTimeout*/))){
		
		_state_id_property = 8;//moving to state bad
parent.channel_TAsToMain .send (new Outcome ("bad",new Object []{parent._ftpC }));
_killThis ();

		_goto_property(_info);
		}
}
else if (_state_id_property==2){
		if (1==0){}
		else if ((_occurredEvent(_event,2/*evt_process_ftpC*/))){
		clock_0 .reset ();

		_state_id_property = 3;//moving to state S1
		_goto_property(_info);
		}
		else if ((_occurredEvent(_event,4/*evt_anySymbol_ftpC*/))){
		
		_state_id_property = 8;//moving to state bad
parent.channel_TAsToMain .send (new Outcome ("bad",new Object []{parent._ftpC }));
_killThis ();

		_goto_property(_info);
		}
		else if ((_occurredEvent(_event,11/*evt_anyTimeout*/))){
		
		_state_id_property = 8;//moving to state bad
parent.channel_TAsToMain .send (new Outcome ("bad",new Object []{parent._ftpC }));
_killThis ();

		_goto_property(_info);
		}
}
else if (_state_id_property==3){
		if (1==0){}
		else if ((_occurredEvent(_event,0/*evt_sendResponse_ftpC*/))){
		
		_state_id_property = 7;//moving to state S5
if (clock_0 .current ()<1 ){parent.instanceCount ++;
parent.channel_stateSkip .send (new StateSkip (new AutomatonInstance (false ),"S5_to_S2",new Clock []{clock_0 },new Object []{parent._ftpC }));
}if (clock_0 .current ()<1 ){parent.instanceCount ++;
parent.channel_stateSkip .send (new StateSkip (new AutomatonInstance (true ),"S5_to_start",new Clock []{clock_0 },new Object []{parent._ftpC }));
}parent.channel_TAsToMain .send (new Outcome ("bad",new Object []{parent._ftpC }));
_killThis ();

		_goto_property(_info);
		}
		else if ((_occurredEvent(_event,4/*evt_anySymbol_ftpC*/))){
		
		_state_id_property = 8;//moving to state bad
parent.channel_TAsToMain .send (new Outcome ("bad",new Object []{parent._ftpC }));
_killThis ();

		_goto_property(_info);
		}
		else if ((_occurredEvent(_event,11/*evt_anyTimeout*/))){
		
		_state_id_property = 8;//moving to state bad
parent.channel_TAsToMain .send (new Outcome ("bad",new Object []{parent._ftpC }));
_killThis ();

		_goto_property(_info);
		}
}
else if (_state_id_property==4){
		if (1==0){}
		else if ((_occurredEvent(_event,4/*evt_anySymbol_ftpC*/))){
		
		_state_id_property = 8;//moving to state bad
parent.channel_TAsToMain .send (new Outcome ("bad",new Object []{parent._ftpC }));
_killThis ();

		_goto_property(_info);
		}
		else if ((_occurredEvent(_event,11/*evt_anyTimeout*/))){
		
		_state_id_property = 8;//moving to state bad
parent.channel_TAsToMain .send (new Outcome ("bad",new Object []{parent._ftpC }));
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
case 8: if (_mode == 0) return "bad"; else return "bad";
case 9: if (_mode == 0) return "start"; else return "start";
case 2: if (_mode == 0) return "S0"; else return "S0";
case 3: if (_mode == 0) return "S1"; else return "S1";
case 4: if (_mode == 0) return "S2"; else return "S2";
default: return "!!!SYSTEM REACHED AN UNKNOWN STATE!!!";
}
}

public boolean _occurredEvent(int[] _events, int event){
for (int i:_events) if (i == event) return true;
return false;
}
void setClocks(final StateSkip ss, final Clock...clocks) {
final long timeouts[] = { 1000 };
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