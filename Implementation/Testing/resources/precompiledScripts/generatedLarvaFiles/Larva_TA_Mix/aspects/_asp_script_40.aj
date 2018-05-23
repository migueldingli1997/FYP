package aspects;

import java.util.*;
import larvaTools.LarvaController;
import larvaTools.TA.AutomatonInstance;
import larvaTools.TA.StateSkip;
import larvaTools.TA.Outcome;

import larva.*;
public aspect _asp_script_40 {

public static Object lock = new Object();

boolean initialized = false;

after():(staticinitialization(*)){
if (!initialized){
	initialized = true;
	_cls_script_40.initialize();
}
}
before ( Object outcomeObj,Channel _c) : (call(* Channel.receive(..)) && target(_c) && (if (_c.equals(_cls_script_40.channel_TAsToMain))) && args(outcomeObj)) {

synchronized(_asp_script_40.lock){
String state;
final Outcome outcome =(Outcome )outcomeObj ;
state =outcome .getState ();

_cls_script_40 _cls_inst = _cls_script_40._get_cls_script_40_inst();
_cls_inst.outcomeObj = outcomeObj;
_cls_script_40.state = state;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 3/*evt_channelTAsToMain*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 3/*evt_channelTAsToMain*/);
}
}
before ( LarvaController __0) : (call(* LarvaController.triggerReset(..)) && target(__0) && !cflow(adviceexecution()) && !cflow(within(larva.*))  && !(within(larva.*))) {

synchronized(_asp_script_40.lock){

_cls_script_40 _cls_inst = _cls_script_40._get_cls_script_40_inst();
_cls_inst.__0 = __0;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 5/*evt_LarvaReset*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 5/*evt_LarvaReset*/);
}
}
before () : (call(* *.ADMIN_reconcile(..)) && !cflow(adviceexecution()) && !cflow(within(larva.*))  && !(within(larva.*))) {

synchronized(_asp_script_40.lock){

_cls_script_40 _cls_inst = _cls_script_40._get_cls_script_40_inst();
_cls_inst._call(thisJoinPoint.getSignature().toString(), 0/*evt_ADMIN_reconcile*/, 2/*evt_anySymbol*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 0/*evt_ADMIN_reconcile*/, 2/*evt_anySymbol*/);
}
}
before ( LarvaController __1) : (call(* LarvaController.triggerStop(..)) && target(__1) && !cflow(adviceexecution()) && !cflow(within(larva.*))  && !(within(larva.*))) {

synchronized(_asp_script_40.lock){

_cls_script_40 _cls_inst = _cls_script_40._get_cls_script_40_inst();
_cls_inst.__1 = __1;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 7/*evt_LarvaStop*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 7/*evt_LarvaStop*/);
}
}
}