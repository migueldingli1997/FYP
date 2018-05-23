package aspects;

import java.util.*;
import larvaTools.LarvaController;
import larvaTools.RE.RegExpHolder;
import RE.lib.basic.Symbol;

import larva.*;
public aspect _asp_script_50 {

public static Object lock = new Object();

boolean initialized = false;

after():(staticinitialization(*)){
if (!initialized){
	initialized = true;
	_cls_script_50.initialize();
}
}
before () : (call(* *.ADMIN_initialise(..)) && !cflow(adviceexecution()) && !cflow(within(larva.*))  && !(within(larva.*))) {

synchronized(_asp_script_50.lock){

_cls_script_50 _cls_inst = _cls_script_50._get_cls_script_50_inst();
_cls_inst._call(thisJoinPoint.getSignature().toString(), 0/*evt_ADMIN_initialise*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 0/*evt_ADMIN_initialise*/);
}
}
before ( RegExpHolder r) : (call(* RegExpHolder.triggerTimeout_hasEmptyString(..)) && target(r) && !cflow(adviceexecution()) && !cflow(within(larva.*))  && !(within(larva.*))) {

synchronized(_asp_script_50.lock){
int id;
id =r .getId ();

_cls_script_50 _cls_inst = _cls_script_50._get_cls_script_50_inst();
_cls_inst.r = r;
_cls_script_50.id = id;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 4/*timeout_hasE*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 4/*timeout_hasE*/);
}
}
before ( RegExpHolder r) : (call(* RegExpHolder.triggerTimeout_isEmpty(..)) && target(r) && !cflow(adviceexecution()) && !cflow(within(larva.*))  && !(within(larva.*))) {

synchronized(_asp_script_50.lock){
int id;
id =r .getId ();

_cls_script_50 _cls_inst = _cls_script_50._get_cls_script_50_inst();
_cls_inst.r = r;
_cls_script_50.id = id;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 2/*timeout_isE*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 2/*timeout_isE*/);
}
}
before ( LarvaController __0) : (call(* LarvaController.triggerReset(..)) && target(__0) && !cflow(adviceexecution()) && !cflow(within(larva.*))  && !(within(larva.*))) {

synchronized(_asp_script_50.lock){

_cls_script_50 _cls_inst = _cls_script_50._get_cls_script_50_inst();
_cls_inst.__0 = __0;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 6/*evt_LarvaReset*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 6/*evt_LarvaReset*/);
}
}
before ( LarvaController __1) : (call(* LarvaController.triggerStop(..)) && target(__1) && !cflow(adviceexecution()) && !cflow(within(larva.*))  && !(within(larva.*))) {

synchronized(_asp_script_50.lock){

_cls_script_50 _cls_inst = _cls_script_50._get_cls_script_50_inst();
_cls_inst.__1 = __1;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 8/*evt_LarvaStop*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 8/*evt_LarvaStop*/);
}
}
}