package larva;


import java.util.*;
import larvaTools.LarvaController;
import larvaTools.TA.AutomatonInstance;
import larvaTools.TA.StateSkip;
import larvaTools.TA.Outcome;
import com.guichaguri.minimalftp.handler.ConnectionHandler;
import com.guichaguri.minimalftp.FTPConnection;

import java.util.LinkedHashMap;
import java.io.PrintWriter;

public class _cls_script_32 implements _callable{

public static LinkedHashMap<_cls_script_32,_cls_script_32> _cls_script_32_instances = new LinkedHashMap<_cls_script_32,_cls_script_32>();

_cls_script_31 parent;
public static StateSkip ss;
public static Object ssObj;
public AutomatonInstance ta;
int no_automata = 1;
public Clock clock_0 = new Clock(this,"clock_0");

public static void initialize(){}
//inheritance could not be used because of the automatic call to super()
//when the constructor is called...we need to keep the SAME parent if this exists!

public _cls_script_32( AutomatonInstance ta,ConnectionHandler _cH) {
parent = _cls_script_31._get_cls_script_31_inst( _cH);
this.ta = ta;
}

public void initialisation() {
   clock_0.reset();

if (ta .shouldPauseClocks ){ta .shouldPauseClocks =false ;
}
}

public static _cls_script_32 _get_cls_script_32_inst( AutomatonInstance ta,ConnectionHandler _cH) { synchronized(_cls_script_32_instances){
_cls_script_32 _inst = new _cls_script_32( ta,_cH);
if (_cls_script_32_instances.containsKey(_inst))
{
_cls_script_32 tmp = _cls_script_32_instances.get(_inst);
 return _cls_script_32_instances.get(_inst);
}
else
{
 _cls_script_32_instances.put(_inst,_inst);
 _inst.initialisation();
 return _inst;
}
}
}

public boolean equals(Object o) {
 if ((o instanceof _cls_script_32)
 && (ta == null || ta.equals(((_cls_script_32)o).ta))
 && (parent == null || parent.equals(((_cls_script_32)o).parent)))
{return true;}
else
{return false;}
}

public int hashCode() {
return (ta==null?1:ta.hashCode()) *(parent==null?1:parent.hashCode()) *1;
}

public void _call(String _info, int... _event){
synchronized(_cls_script_32_instances){
_performLogic_property(_info, _event);
}
}

public void _call_all_filtered(String _info, int... _event){
}

public static void _call_all(String _info, int... _event){

_cls_script_32[] a = new _cls_script_32[1];
synchronized(_cls_script_32_instances){
a = _cls_script_32_instances.keySet().toArray(a);}
for (_cls_script_32 _inst : a)

if (_inst != null) _inst._call(_info, _event);
}

public void _killThis(){
try{
if (--no_automata == 0){
synchronized(_cls_script_32_instances){
_cls_script_32_instances.remove(this);}
synchronized(clock_0){
clock_0.off();
clock_0._inst = null;
clock_0 = null;}
}
else if (no_automata < 0)
{throw new Exception("no_automata < 0!!");}
}catch(Exception ex){ex.printStackTrace();}
}

int _state_id_property = 23;

public void _performLogic_property(String _info, int... _event) {

if (0==1){}
else if (_state_id_property==5){
		if (1==0){}
		else if ((_occurredEvent(_event,4/*evt_cds_cH*/))){
		
		_state_id_property = 6;//moving to state S4
		_goto_property(_info);
		}
		else if ((_occurredEvent(_event,12/*evt_anySymbol_cH*/)) && (parent.authenticated )){
		
		_state_id_property = 22;//moving to state good
parent.channel_TAsToMain .send (new Outcome ("good",new Object []{parent._cH }));
_killThis ();

		_goto_property(_info);
		}
}
else if (_state_id_property==6){
		if (1==0){}
		else if ((_occurredEvent(_event,4/*evt_cds_cH*/))){
		
		_state_id_property = 7;//moving to state S5
		_goto_property(_info);
		}
		else if ((_occurredEvent(_event,12/*evt_anySymbol_cH*/)) && (parent.authenticated )){
		
		_state_id_property = 22;//moving to state good
parent.channel_TAsToMain .send (new Outcome ("good",new Object []{parent._cH }));
_killThis ();

		_goto_property(_info);
		}
}
else if (_state_id_property==7){
		if (1==0){}
		else if ((_occurredEvent(_event,4/*evt_cds_cH*/))){
		
		_state_id_property = 8;//moving to state S6
		_goto_property(_info);
		}
		else if ((_occurredEvent(_event,12/*evt_anySymbol_cH*/)) && (parent.authenticated )){
		
		_state_id_property = 22;//moving to state good
parent.channel_TAsToMain .send (new Outcome ("good",new Object []{parent._cH }));
_killThis ();

		_goto_property(_info);
		}
}
else if (_state_id_property==12){
		if (1==0){}
		else if ((_occurredEvent(_event,12/*evt_anySymbol_cH*/)) && (parent.authenticated )){
		
		_state_id_property = 22;//moving to state good
parent.channel_TAsToMain .send (new Outcome ("good",new Object []{parent._cH }));
_killThis ();

		_goto_property(_info);
		}
}
else if (_state_id_property==8){
		if (1==0){}
		else if ((_occurredEvent(_event,4/*evt_cds_cH*/))){
		
		_state_id_property = 9;//moving to state S7
		_goto_property(_info);
		}
		else if ((_occurredEvent(_event,12/*evt_anySymbol_cH*/)) && (parent.authenticated )){
		
		_state_id_property = 22;//moving to state good
parent.channel_TAsToMain .send (new Outcome ("good",new Object []{parent._cH }));
_killThis ();

		_goto_property(_info);
		}
}
else if (_state_id_property==11){
		if (1==0){}
		else if ((_occurredEvent(_event,0/*evt_onConnected_cH*/)) && (clock_0 .current ()>=3600 )){
		
		_state_id_property = 21;//moving to state bad
parent.channel_TAsToMain .send (new Outcome ("bad",new Object []{parent._cH }));
_killThis ();

		_goto_property(_info);
		}
		else if ((_occurredEvent(_event,2/*evt_auth_cH*/)) && (parent.authenticated &&clock_0 .current ()>=3600 )){
		
		_state_id_property = 21;//moving to state bad
parent.channel_TAsToMain .send (new Outcome ("bad",new Object []{parent._cH }));
_killThis ();

		_goto_property(_info);
		}
		else if ((_occurredEvent(_event,6/*evt_user_cH*/)) && (clock_0 .current ()>=3600 )){
		
		_state_id_property = 21;//moving to state bad
parent.channel_TAsToMain .send (new Outcome ("bad",new Object []{parent._cH }));
_killThis ();

		_goto_property(_info);
		}
		else if ((_occurredEvent(_event,8/*evt_pass_cH*/)) && (clock_0 .current ()>=3600 )){
		
		_state_id_property = 21;//moving to state bad
parent.channel_TAsToMain .send (new Outcome ("bad",new Object []{parent._cH }));
_killThis ();

		_goto_property(_info);
		}
		else if ((_occurredEvent(_event,4/*evt_cds_cH*/)) && (clock_0 .current ()>=3600 )){
		
		_state_id_property = 21;//moving to state bad
parent.channel_TAsToMain .send (new Outcome ("bad",new Object []{parent._cH }));
_killThis ();

		_goto_property(_info);
		}
		else if ((_occurredEvent(_event,12/*evt_anySymbol_cH*/)) && (parent.authenticated )){
		
		_state_id_property = 22;//moving to state good
parent.channel_TAsToMain .send (new Outcome ("good",new Object []{parent._cH }));
_killThis ();

		_goto_property(_info);
		}
}
else if (_state_id_property==9){
		if (1==0){}
		else if ((_occurredEvent(_event,4/*evt_cds_cH*/))){
		
		_state_id_property = 21;//moving to state bad
parent.channel_TAsToMain .send (new Outcome ("bad",new Object []{parent._cH }));
_killThis ();

		_goto_property(_info);
		}
		else if ((_occurredEvent(_event,12/*evt_anySymbol_cH*/)) && (parent.authenticated )){
		
		_state_id_property = 22;//moving to state good
parent.channel_TAsToMain .send (new Outcome ("good",new Object []{parent._cH }));
_killThis ();

		_goto_property(_info);
		}
}
else if (_state_id_property==10){
		if (1==0){}
		else if ((_occurredEvent(_event,12/*evt_anySymbol_cH*/)) && (parent.authenticated )){
		
		_state_id_property = 22;//moving to state good
parent.channel_TAsToMain .send (new Outcome ("good",new Object []{parent._cH }));
_killThis ();

		_goto_property(_info);
		}
}
else if (_state_id_property==14){
		if (1==0){}
		else if ((_occurredEvent(_event,10/*evt_onDisconnected_cH*/))){
		
		_state_id_property = 15;//moving to state S12
if (true ){parent.instanceCount ++;
parent.channel_stateSkip .send (new StateSkip (new AutomatonInstance (false ),"S12_to_S9",new Clock []{clock_0 },new Object []{parent._cH }));
}if (true ){parent.instanceCount ++;
parent.channel_stateSkip .send (new StateSkip (new AutomatonInstance (false ),"S12_to_S10",new Clock []{null },new Object []{parent._cH }));
}parent.channel_TAsToMain .send (new Outcome ("good",new Object []{parent._cH }));
_killThis ();

		_goto_property(_info);
		}
		else if ((_occurredEvent(_event,6/*evt_user_cH*/))){
		
		_state_id_property = 16;//moving to state S13
if (true ){parent.instanceCount ++;
parent.channel_stateSkip .send (new StateSkip (new AutomatonInstance (false ),"S13_to_S9",new Clock []{clock_0 },new Object []{parent._cH }));
}if (true ){parent.instanceCount ++;
parent.channel_stateSkip .send (new StateSkip (new AutomatonInstance (false ),"S13_to_S10",new Clock []{null },new Object []{parent._cH }));
}parent.channel_TAsToMain .send (new Outcome ("good",new Object []{parent._cH }));
_killThis ();

		_goto_property(_info);
		}
		else if ((_occurredEvent(_event,4/*evt_cds_cH*/))){
		
		_state_id_property = 17;//moving to state S14
if (true ){parent.instanceCount ++;
parent.channel_stateSkip .send (new StateSkip (new AutomatonInstance (false ),"S14_to_S9",new Clock []{clock_0 },new Object []{parent._cH }));
}if (true ){parent.instanceCount ++;
parent.channel_stateSkip .send (new StateSkip (new AutomatonInstance (false ),"S14_to_S10",new Clock []{null },new Object []{parent._cH }));
}parent.channel_TAsToMain .send (new Outcome ("good",new Object []{parent._cH }));
_killThis ();

		_goto_property(_info);
		}
		else if ((_occurredEvent(_event,0/*evt_onConnected_cH*/))){
		
		_state_id_property = 18;//moving to state S15
if (true ){parent.instanceCount ++;
parent.channel_stateSkip .send (new StateSkip (new AutomatonInstance (false ),"S15_to_S9",new Clock []{clock_0 },new Object []{parent._cH }));
}if (true ){parent.instanceCount ++;
parent.channel_stateSkip .send (new StateSkip (new AutomatonInstance (false ),"S15_to_S10",new Clock []{null },new Object []{parent._cH }));
}parent.channel_TAsToMain .send (new Outcome ("good",new Object []{parent._cH }));
_killThis ();

		_goto_property(_info);
		}
		else if ((_occurredEvent(_event,2/*evt_auth_cH*/)) && (parent.authenticated )){
		
		_state_id_property = 19;//moving to state S16
if (true ){parent.instanceCount ++;
parent.channel_stateSkip .send (new StateSkip (new AutomatonInstance (false ),"S16_to_S9",new Clock []{clock_0 },new Object []{parent._cH }));
}if (true ){parent.instanceCount ++;
parent.channel_stateSkip .send (new StateSkip (new AutomatonInstance (false ),"S16_to_S10",new Clock []{null },new Object []{parent._cH }));
}parent.channel_TAsToMain .send (new Outcome ("good",new Object []{parent._cH }));
_killThis ();

		_goto_property(_info);
		}
		else if ((_occurredEvent(_event,8/*evt_pass_cH*/))){
		
		_state_id_property = 20;//moving to state S17
if (true ){parent.instanceCount ++;
parent.channel_stateSkip .send (new StateSkip (new AutomatonInstance (false ),"S17_to_S9",new Clock []{clock_0 },new Object []{parent._cH }));
}if (true ){parent.instanceCount ++;
parent.channel_stateSkip .send (new StateSkip (new AutomatonInstance (false ),"S17_to_S10",new Clock []{null },new Object []{parent._cH }));
}parent.channel_TAsToMain .send (new Outcome ("good",new Object []{parent._cH }));
_killThis ();

		_goto_property(_info);
		}
		else if ((_occurredEvent(_event,12/*evt_anySymbol_cH*/)) && (parent.authenticated )){
		
		_state_id_property = 22;//moving to state good
parent.channel_TAsToMain .send (new Outcome ("good",new Object []{parent._cH }));
_killThis ();

		_goto_property(_info);
		}
}
else if (_state_id_property==23){
		if (1==0){}
		else if ((_occurredEvent(_event,0/*evt_onConnected_cH*/))){
		
		_state_id_property = 3;//moving to state S0
		_goto_property(_info);
		}
		else if ((_occurredEvent(_event,15/*evt_channelStateSkip*/)) && (ss .getDestination ().equals ("S1_to_S2"))){
		
		_state_id_property = 4;//moving to state S2
		_goto_property(_info);
		}
		else if ((_occurredEvent(_event,15/*evt_channelStateSkip*/)) && (ss .getDestination ().equals ("S1_to_S9"))){
		
		_state_id_property = 14;//moving to state S9
if (true ){parent.instanceCount ++;
parent.channel_stateSkip .send (new StateSkip (new AutomatonInstance (false ),"S9_to_S10",new Clock []{null },new Object []{parent._cH }));
}
		_goto_property(_info);
		}
		else if ((_occurredEvent(_event,15/*evt_channelStateSkip*/)) && (ss .getDestination ().equals ("S9_to_S10"))){
		
		_state_id_property = 11;//moving to state S10
		_goto_property(_info);
		}
		else if ((_occurredEvent(_event,15/*evt_channelStateSkip*/)) && (ss .getDestination ().equals ("S12_to_S9"))){
		
		_state_id_property = 14;//moving to state S9
if (true ){parent.instanceCount ++;
parent.channel_stateSkip .send (new StateSkip (new AutomatonInstance (false ),"S9_to_S10",new Clock []{null },new Object []{parent._cH }));
}
		_goto_property(_info);
		}
		else if ((_occurredEvent(_event,15/*evt_channelStateSkip*/)) && (ss .getDestination ().equals ("S12_to_S10"))){
		
		_state_id_property = 11;//moving to state S10
		_goto_property(_info);
		}
		else if ((_occurredEvent(_event,15/*evt_channelStateSkip*/)) && (ss .getDestination ().equals ("S13_to_S9"))){
		
		_state_id_property = 14;//moving to state S9
if (true ){parent.instanceCount ++;
parent.channel_stateSkip .send (new StateSkip (new AutomatonInstance (false ),"S9_to_S10",new Clock []{null },new Object []{parent._cH }));
}
		_goto_property(_info);
		}
		else if ((_occurredEvent(_event,15/*evt_channelStateSkip*/)) && (ss .getDestination ().equals ("S13_to_S10"))){
		
		_state_id_property = 11;//moving to state S10
		_goto_property(_info);
		}
		else if ((_occurredEvent(_event,15/*evt_channelStateSkip*/)) && (ss .getDestination ().equals ("S14_to_S9"))){
		
		_state_id_property = 14;//moving to state S9
if (true ){parent.instanceCount ++;
parent.channel_stateSkip .send (new StateSkip (new AutomatonInstance (false ),"S9_to_S10",new Clock []{null },new Object []{parent._cH }));
}
		_goto_property(_info);
		}
		else if ((_occurredEvent(_event,15/*evt_channelStateSkip*/)) && (ss .getDestination ().equals ("S14_to_S10"))){
		
		_state_id_property = 11;//moving to state S10
		_goto_property(_info);
		}
		else if ((_occurredEvent(_event,15/*evt_channelStateSkip*/)) && (ss .getDestination ().equals ("S15_to_S9"))){
		
		_state_id_property = 14;//moving to state S9
if (true ){parent.instanceCount ++;
parent.channel_stateSkip .send (new StateSkip (new AutomatonInstance (false ),"S9_to_S10",new Clock []{null },new Object []{parent._cH }));
}
		_goto_property(_info);
		}
		else if ((_occurredEvent(_event,15/*evt_channelStateSkip*/)) && (ss .getDestination ().equals ("S15_to_S10"))){
		
		_state_id_property = 11;//moving to state S10
		_goto_property(_info);
		}
		else if ((_occurredEvent(_event,15/*evt_channelStateSkip*/)) && (ss .getDestination ().equals ("S16_to_S9"))){
		
		_state_id_property = 14;//moving to state S9
if (true ){parent.instanceCount ++;
parent.channel_stateSkip .send (new StateSkip (new AutomatonInstance (false ),"S9_to_S10",new Clock []{null },new Object []{parent._cH }));
}
		_goto_property(_info);
		}
		else if ((_occurredEvent(_event,15/*evt_channelStateSkip*/)) && (ss .getDestination ().equals ("S16_to_S10"))){
		
		_state_id_property = 11;//moving to state S10
		_goto_property(_info);
		}
		else if ((_occurredEvent(_event,15/*evt_channelStateSkip*/)) && (ss .getDestination ().equals ("S17_to_S9"))){
		
		_state_id_property = 14;//moving to state S9
if (true ){parent.instanceCount ++;
parent.channel_stateSkip .send (new StateSkip (new AutomatonInstance (false ),"S9_to_S10",new Clock []{null },new Object []{parent._cH }));
}
		_goto_property(_info);
		}
		else if ((_occurredEvent(_event,15/*evt_channelStateSkip*/)) && (ss .getDestination ().equals ("S17_to_S10"))){
		
		_state_id_property = 11;//moving to state S10
		_goto_property(_info);
		}
		else if ((_occurredEvent(_event,15/*evt_channelStateSkip*/)) && (ss .getDestination ().equals ("main_to_start"))){
		
		_state_id_property = 23;//moving to state start
if (ta .shouldPauseClocks ){ta .shouldPauseClocks =false ;
}
		_goto_property(_info);
		}
		else if ((_occurredEvent(_event,12/*evt_anySymbol_cH*/)) && (parent.authenticated )){
		
		_state_id_property = 22;//moving to state good
parent.channel_TAsToMain .send (new Outcome ("good",new Object []{parent._cH }));
_killThis ();

		_goto_property(_info);
		}
}
else if (_state_id_property==3){
		if (1==0){}
		else if ((_occurredEvent(_event,2/*evt_auth_cH*/)) && (parent.authenticated )){
		
		_state_id_property = 13;//moving to state S1
if (true ){parent.instanceCount ++;
parent.channel_stateSkip .send (new StateSkip (new AutomatonInstance (false ),"S1_to_S2",new Clock []{clock_0 },new Object []{parent._cH }));
}if (true ){parent.instanceCount ++;
parent.channel_stateSkip .send (new StateSkip (new AutomatonInstance (false ),"S1_to_S9",new Clock []{clock_0 },new Object []{parent._cH }));
}parent.channel_TAsToMain .send (new Outcome ("good",new Object []{parent._cH }));
_killThis ();

		_goto_property(_info);
		}
		else if ((_occurredEvent(_event,12/*evt_anySymbol_cH*/)) && (parent.authenticated )){
		
		_state_id_property = 22;//moving to state good
parent.channel_TAsToMain .send (new Outcome ("good",new Object []{parent._cH }));
_killThis ();

		_goto_property(_info);
		}
}
else if (_state_id_property==4){
		if (1==0){}
		else if ((_occurredEvent(_event,4/*evt_cds_cH*/))){
		
		_state_id_property = 5;//moving to state S3
		_goto_property(_info);
		}
		else if ((_occurredEvent(_event,12/*evt_anySymbol_cH*/)) && (parent.authenticated )){
		
		_state_id_property = 22;//moving to state good
parent.channel_TAsToMain .send (new Outcome ("good",new Object []{parent._cH }));
_killThis ();

		_goto_property(_info);
		}
}
}

public void _goto_property(String _info){
 String state_format = _string_property(_state_id_property, 1);
 if (state_format.startsWith("!!!SYSTEM REACHED BAD STATE!!!")) {
_cls_script_30.pw.println("[property]MOVED ON METHODCALL: "+ _info +" TO STATE::> " + state_format);
_cls_script_30.pw.flush();
}
}

public String _string_property(int _state_id, int _mode){
switch(_state_id){
case 5: if (_mode == 0) return "S3"; else return "S3";
case 6: if (_mode == 0) return "S4"; else return "S4";
case 7: if (_mode == 0) return "S5"; else return "S5";
case 8: if (_mode == 0) return "S6"; else return "S6";
case 9: if (_mode == 0) return "S7"; else return "S7";
case 21: if (_mode == 0) return "bad"; else return "bad";
case 10: if (_mode == 0) return "S8"; else return "S8";
case 14: if (_mode == 0) return "S9"; else return "S9";
case 23: if (_mode == 0) return "start"; else return "start";
case 22: if (_mode == 0) return "good"; else return "good";
case 12: if (_mode == 0) return "S11"; else return "S11";
case 11: if (_mode == 0) return "S10"; else return "S10";
case 16: if (_mode == 0) return "S13"; else return "S13";
case 15: if (_mode == 0) return "S12"; else return "S12";
case 18: if (_mode == 0) return "S15"; else return "S15";
case 17: if (_mode == 0) return "S14"; else return "S14";
case 20: if (_mode == 0) return "S17"; else return "S17";
case 19: if (_mode == 0) return "S16"; else return "S16";
case 3: if (_mode == 0) return "S0"; else return "S0";
case 13: if (_mode == 0) return "S1"; else return "S1";
case 4: if (_mode == 0) return "S2"; else return "S2";
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