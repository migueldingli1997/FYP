package aspects;

import java.util.*;
import larvaTools.LarvaController;
import larvaTools.TA.AutomatonInstance;
import larvaTools.TA.StateSkip;
import larvaTools.TA.Outcome;
import com.guichaguri.minimalftp.handler.ConnectionHandler;

import larva.*;
public aspect _asp_script_52 {

boolean initialized = false;

after():(staticinitialization(*)){
if (!initialized){
	initialized = true;
	_cls_script_52.initialize();
}
}
before ( Object ssObj,Channel _c) : (call(* Channel.receive(..)) && target(_c) && (if (_c.equals(_cls_script_51.channel_stateSkip))) && args(ssObj)) {

synchronized(_asp_script_50.lock){
AutomatonInstance ta;
ConnectionHandler _cH;
StateSkip ss;
ss =(StateSkip )ssObj ;
ta =ss .getInstance ();
_cH =(ConnectionHandler )ss .getVar (0 );

_cls_script_52 _cls_inst = _cls_script_52._get_cls_script_52_inst( ta,_cH);
_cls_inst.ssObj = ssObj;
_cls_script_52.ss = ss;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 10/*evt_channelStateSkip*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 10/*evt_channelStateSkip*/);
}
}
before ( Clock _c, long millis) : (call(* Clock.event(long)) && args(millis) && target(_c) && !cflow(adviceexecution())) {

synchronized(_asp_script_50.lock){
AutomatonInstance ta;
ConnectionHandler _cH;
ta =null ;
_cH =null ;

synchronized(_c){
 if (_c != null && _c._inst != null) {
_c._inst._call(thisJoinPoint.getSignature().toString(), 7/*evt_clock_0_dyn*/, 9/*evt_anyTimeout*/);
 } if (_c != null && _c._inst != null) {
_c._inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 7/*evt_clock_0_dyn*/, 9/*evt_anyTimeout*/);
}
}
}
}
before ( Clock _c, long millis) : (call(* Clock.event(long)) && args(millis) && target(_c)  && (if (_c.name.equals("clock_0"))) && (if (millis == 10000)) && !cflow(adviceexecution())) {

synchronized(_asp_script_50.lock){
AutomatonInstance ta;
ConnectionHandler _cH;
ta =null ;
_cH =null ;

synchronized(_c){
 if (_c != null && _c._inst != null) {
_c._inst._call(thisJoinPoint.getSignature().toString(), 5/*evt_clock_0*/, 9/*evt_anyTimeout*/);
 } if (_c != null && _c._inst != null) {
_c._inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 5/*evt_clock_0*/, 9/*evt_anyTimeout*/);
}
}
}
}
}