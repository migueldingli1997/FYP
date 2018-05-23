package aspects;

import java.util.*;
import larvaTools.LarvaController;
import larvaTools.TA.AutomatonInstance;
import larvaTools.TA.StateSkip;
import larvaTools.TA.Outcome;

import larva.*;
public aspect _asp_script_20 {

public static Object lock = new Object();

boolean initialized = false;

after():(staticinitialization(*)){
if (!initialized){
	initialized = true;
	_cls_script_20.initialize();
}
}
before ( LarvaController __0) : (call(* LarvaController.triggerReset(..)) && target(__0) && !cflow(adviceexecution()) && !cflow(within(larva.*))  && !(within(larva.*))) {

synchronized(_asp_script_20.lock){

_cls_script_20 _cls_inst = _cls_script_20._get_cls_script_20_inst();
_cls_inst.__0 = __0;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 9/*evt_LarvaReset*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 9/*evt_LarvaReset*/);
}
}
before () : (call(* *.closeSession(..)) && !cflow(adviceexecution()) && !cflow(within(larva.*))  && !(within(larva.*))) {

synchronized(_asp_script_20.lock){

_cls_script_20 _cls_inst = _cls_script_20._get_cls_script_20_inst();
_cls_inst._call(thisJoinPoint.getSignature().toString(), 4/*evt_closeSession*/, 6/*evt_anySymbol*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 4/*evt_closeSession*/, 6/*evt_anySymbol*/);
}
}
before () : (call(* *.makeGoldUser(..)) && !cflow(adviceexecution()) && !cflow(within(larva.*))  && !(within(larva.*))) {

synchronized(_asp_script_20.lock){

_cls_script_20 _cls_inst = _cls_script_20._get_cls_script_20_inst();
_cls_inst._call(thisJoinPoint.getSignature().toString(), 0/*evt_makeGoldUser*/, 6/*evt_anySymbol*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 0/*evt_makeGoldUser*/, 6/*evt_anySymbol*/);
}
}
before ( Object outcomeObj,Channel _c) : (call(* Channel.receive(..)) && target(_c) && (if (_c.equals(_cls_script_20.channel_TAsToMain))) && args(outcomeObj)) {

synchronized(_asp_script_20.lock){
String state;
final Outcome outcome =(Outcome )outcomeObj ;
state =outcome .getState ();

_cls_script_20 _cls_inst = _cls_script_20._get_cls_script_20_inst();
_cls_inst.outcomeObj = outcomeObj;
_cls_script_20.state = state;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 7/*evt_channelTAsToMain*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 7/*evt_channelTAsToMain*/);
}
}
before () : (call(* *.openSession(..)) && !cflow(adviceexecution()) && !cflow(within(larva.*))  && !(within(larva.*))) {

synchronized(_asp_script_20.lock){

_cls_script_20 _cls_inst = _cls_script_20._get_cls_script_20_inst();
_cls_inst._call(thisJoinPoint.getSignature().toString(), 2/*evt_openSession*/, 6/*evt_anySymbol*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 2/*evt_openSession*/, 6/*evt_anySymbol*/);
}
}
before ( LarvaController __1) : (call(* LarvaController.triggerStop(..)) && target(__1) && !cflow(adviceexecution()) && !cflow(within(larva.*))  && !(within(larva.*))) {

synchronized(_asp_script_20.lock){

_cls_script_20 _cls_inst = _cls_script_20._get_cls_script_20_inst();
_cls_inst.__1 = __1;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 11/*evt_LarvaStop*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 11/*evt_LarvaStop*/);
}
}
}