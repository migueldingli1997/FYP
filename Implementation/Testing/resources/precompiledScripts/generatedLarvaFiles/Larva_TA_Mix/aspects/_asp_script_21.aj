package aspects;

import java.util.*;
import larvaTools.LarvaController;
import larvaTools.TA.AutomatonInstance;
import larvaTools.TA.StateSkip;
import larvaTools.TA.Outcome;

import larva.*;
public aspect _asp_script_21 {

boolean initialized = false;

after():(staticinitialization(*)){
if (!initialized){
	initialized = true;
	_cls_script_21.initialize();
}
}
before ( Object ssObj,Channel _c) : (call(* Channel.receive(..)) && target(_c) && (if (_c.equals(_cls_script_20.channel_stateSkip))) && args(ssObj)) {

synchronized(_asp_script_20.lock){
AutomatonInstance ta;
StateSkip ss;
ss =(StateSkip )ssObj ;
ta =ss .getInstance ();

_cls_script_21 _cls_inst = _cls_script_21._get_cls_script_21_inst( ta);
_cls_inst.ssObj = ssObj;
_cls_script_21.ss = ss;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 18/*evt_channelStateSkip*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 18/*evt_channelStateSkip*/);
}
}
before ( Clock _c, long millis) : (call(* Clock.event(long)) && args(millis) && target(_c) && !cflow(adviceexecution())) {

synchronized(_asp_script_20.lock){
AutomatonInstance ta;
ta =null ;

synchronized(_c){
 if (_c != null && _c._inst != null) {
_c._inst._call(thisJoinPoint.getSignature().toString(), 15/*evt_clock_0_dyn*/, 17/*evt_anyTimeout*/);
 } if (_c != null && _c._inst != null) {
_c._inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 15/*evt_clock_0_dyn*/, 17/*evt_anyTimeout*/);
}
}
}
}
before ( Clock _c, long millis) : (call(* Clock.event(long)) && args(millis) && target(_c)  && (if (_c.name.equals("clock_0"))) && (if (millis == 4000)) && !cflow(adviceexecution())) {

synchronized(_asp_script_20.lock){
AutomatonInstance ta;
ta =null ;

synchronized(_c){
 if (_c != null && _c._inst != null) {
_c._inst._call(thisJoinPoint.getSignature().toString(), 13/*evt_clock_0*/, 17/*evt_anyTimeout*/);
 } if (_c != null && _c._inst != null) {
_c._inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 13/*evt_clock_0*/, 17/*evt_anyTimeout*/);
}
}
}
}
}