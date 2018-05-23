package aspects;

import java.util.*;
import larvaTools.LarvaController;
import larvaTools.RE.RegExpHolder;
import RE.lib.basic.Symbol;

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
before () : (call(* *.withdraw(..)) && !cflow(adviceexecution()) && !cflow(within(larva.*))  && !(within(larva.*))) {

synchronized(_asp_script_20.lock){

_cls_script_20 _cls_inst = _cls_script_20._get_cls_script_20_inst();
_cls_inst._call(thisJoinPoint.getSignature().toString(), 6/*evt_withdraw*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 6/*evt_withdraw*/);
}
}
before () : (call(* *.deposit(..)) && !cflow(adviceexecution()) && !cflow(within(larva.*))  && !(within(larva.*))) {

synchronized(_asp_script_20.lock){

_cls_script_20 _cls_inst = _cls_script_20._get_cls_script_20_inst();
_cls_inst._call(thisJoinPoint.getSignature().toString(), 4/*evt_deposit*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 4/*evt_deposit*/);
}
}
before ( RegExpHolder r) : (call(* RegExpHolder.triggerTimeout_hasEmptyString(..)) && target(r) && !cflow(adviceexecution()) && !cflow(within(larva.*))  && !(within(larva.*))) {

synchronized(_asp_script_20.lock){
int id;
id =r .getId ();

_cls_script_20 _cls_inst = _cls_script_20._get_cls_script_20_inst();
_cls_inst.r = r;
_cls_script_20.id = id;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 14/*timeout_hasE*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 14/*timeout_hasE*/);
}
}
before ( LarvaController __0) : (call(* LarvaController.triggerReset(..)) && target(__0) && !cflow(adviceexecution()) && !cflow(within(larva.*))  && !(within(larva.*))) {

synchronized(_asp_script_20.lock){

_cls_script_20 _cls_inst = _cls_script_20._get_cls_script_20_inst();
_cls_inst.__0 = __0;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 16/*evt_LarvaReset*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 16/*evt_LarvaReset*/);
}
}
before () : (call(* *.closeSession(..)) && !cflow(adviceexecution()) && !cflow(within(larva.*))  && !(within(larva.*))) {

synchronized(_asp_script_20.lock){

_cls_script_20 _cls_inst = _cls_script_20._get_cls_script_20_inst();
_cls_inst._call(thisJoinPoint.getSignature().toString(), 8/*evt_closeSession*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 8/*evt_closeSession*/);
}
}
before () : (call(* *.makeSilverUser(..)) && !cflow(adviceexecution()) && !cflow(within(larva.*))  && !(within(larva.*))) {

synchronized(_asp_script_20.lock){

_cls_script_20 _cls_inst = _cls_script_20._get_cls_script_20_inst();
_cls_inst._call(thisJoinPoint.getSignature().toString(), 10/*evt_makeSilverUser*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 10/*evt_makeSilverUser*/);
}
}
before () : (call(* *.makeGoldUser(..)) && !cflow(adviceexecution()) && !cflow(within(larva.*))  && !(within(larva.*))) {

synchronized(_asp_script_20.lock){

_cls_script_20 _cls_inst = _cls_script_20._get_cls_script_20_inst();
_cls_inst._call(thisJoinPoint.getSignature().toString(), 0/*evt_makeGoldUser*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 0/*evt_makeGoldUser*/);
}
}
before () : (call(* *.openSession(..)) && !cflow(adviceexecution()) && !cflow(within(larva.*))  && !(within(larva.*))) {

synchronized(_asp_script_20.lock){

_cls_script_20 _cls_inst = _cls_script_20._get_cls_script_20_inst();
_cls_inst._call(thisJoinPoint.getSignature().toString(), 2/*evt_openSession*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 2/*evt_openSession*/);
}
}
before ( RegExpHolder r) : (call(* RegExpHolder.triggerTimeout_isEmpty(..)) && target(r) && !cflow(adviceexecution()) && !cflow(within(larva.*))  && !(within(larva.*))) {

synchronized(_asp_script_20.lock){
int id;
id =r .getId ();

_cls_script_20 _cls_inst = _cls_script_20._get_cls_script_20_inst();
_cls_inst.r = r;
_cls_script_20.id = id;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 12/*timeout_isE*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 12/*timeout_isE*/);
}
}
before ( LarvaController __1) : (call(* LarvaController.triggerStop(..)) && target(__1) && !cflow(adviceexecution()) && !cflow(within(larva.*))  && !(within(larva.*))) {

synchronized(_asp_script_20.lock){

_cls_script_20 _cls_inst = _cls_script_20._get_cls_script_20_inst();
_cls_inst.__1 = __1;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 18/*evt_LarvaStop*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 18/*evt_LarvaStop*/);
}
}
}