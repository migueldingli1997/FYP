package aspects;

import java.util.*;
import larvaTools.LarvaController;
import larvaTools.RE.RegExpHolder;
import RE.lib.basic.Symbol;
import transactionsystem.UserInfo;
import transactionsystem.UserAccount;

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
before ( RegExpHolder r) : (call(* RegExpHolder.triggerTimeout_isEmpty(..)) && target(r) && !cflow(adviceexecution()) && !cflow(within(larva.*))  && !(within(larva.*))) {

synchronized(_asp_script_20.lock){
int id;
id =r .getId ();

_cls_script_20 _cls_inst = _cls_script_20._get_cls_script_20_inst();
_cls_inst.r = r;
_cls_script_20.id = id;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 0/*timeout_isE*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 0/*timeout_isE*/);
}
}
before ( LarvaController __0) : (call(* LarvaController.triggerReset(..)) && target(__0) && !cflow(adviceexecution()) && !cflow(within(larva.*))  && !(within(larva.*))) {

synchronized(_asp_script_20.lock){

_cls_script_20 _cls_inst = _cls_script_20._get_cls_script_20_inst();
_cls_inst.__0 = __0;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 2/*evt_LarvaReset*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 2/*evt_LarvaReset*/);
}
}
before ( LarvaController __1) : (call(* LarvaController.triggerStop(..)) && target(__1) && !cflow(adviceexecution()) && !cflow(within(larva.*))  && !(within(larva.*))) {

synchronized(_asp_script_20.lock){

_cls_script_20 _cls_inst = _cls_script_20._get_cls_script_20_inst();
_cls_inst.__1 = __1;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 4/*evt_LarvaStop*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 4/*evt_LarvaStop*/);
}
}
}