package aspects;

import java.util.*;
import larvaTools.LarvaController;
import larvaTools.TA.AutomatonInstance;
import larvaTools.TA.StateSkip;
import larvaTools.TA.Outcome;
import transactionsystem.UserAccount;

import larva.*;
public aspect _asp_script_12 {

boolean initialized = false;

after():(staticinitialization(*)){
if (!initialized){
	initialized = true;
	_cls_script_12.initialize();
}
}
before ( Object ssObj,Channel _c) : (call(* Channel.receive(..)) && target(_c) && (if (_c.equals(_cls_script_11.channel_stateSkip))) && args(ssObj)) {

synchronized(_asp_script_10.lock){
AutomatonInstance ta;
UserAccount _ua;
StateSkip ss;
ss =(StateSkip )ssObj ;
ta =ss .getInstance ();
_ua =(UserAccount )ss .getVar (0 );

_cls_script_12 _cls_inst = _cls_script_12._get_cls_script_12_inst( ta,_ua);
_cls_inst.ssObj = ssObj;
_cls_script_12.ss = ss;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 16/*evt_channelStateSkip*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 16/*evt_channelStateSkip*/);
}
}
before ( Clock _c, long millis) : (call(* Clock.event(long)) && args(millis) && target(_c) && !cflow(adviceexecution())) {

synchronized(_asp_script_10.lock){
AutomatonInstance ta;
UserAccount _ua;
ta =null ;
_ua =null ;

synchronized(_c){
 if (_c != null && _c._inst != null) {
_c._inst._call(thisJoinPoint.getSignature().toString(), 13/*evt_clock_0_dyn*/, 15/*evt_anyTimeout*/);
 } if (_c != null && _c._inst != null) {
_c._inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 13/*evt_clock_0_dyn*/, 15/*evt_anyTimeout*/);
}
}
}
}
before ( Clock _c, long millis) : (call(* Clock.event(long)) && args(millis) && target(_c)  && (if (_c.name.equals("clock_0"))) && (if (millis == 4000)) && !cflow(adviceexecution())) {

synchronized(_asp_script_10.lock){
AutomatonInstance ta;
UserAccount _ua;
ta =null ;
_ua =null ;

synchronized(_c){
 if (_c != null && _c._inst != null) {
_c._inst._call(thisJoinPoint.getSignature().toString(), 11/*evt_clock_0*/, 15/*evt_anyTimeout*/);
 } if (_c != null && _c._inst != null) {
_c._inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 11/*evt_clock_0*/, 15/*evt_anyTimeout*/);
}
}
}
}
}