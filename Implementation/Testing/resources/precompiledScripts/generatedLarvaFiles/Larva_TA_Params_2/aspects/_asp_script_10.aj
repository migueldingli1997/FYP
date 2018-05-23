package aspects;

import java.util.*;
import larvaTools.LarvaController;
import larvaTools.TA.AutomatonInstance;
import larvaTools.TA.StateSkip;
import larvaTools.TA.Outcome;
import transactionsystem.UserInfo;
import transactionsystem.UserAccount;

import larva.*;
public aspect _asp_script_10 {

public static Object lock = new Object();

boolean initialized = false;

after():(staticinitialization(*)){
if (!initialized){
	initialized = true;
	_cls_script_10.initialize();
}
}
before () : (call(* *.ADMIN_initialise(..)) && !cflow(adviceexecution()) && !cflow(within(larva.*))  && !(within(larva.*))) {

synchronized(_asp_script_10.lock){

_cls_script_10 _cls_inst = _cls_script_10._get_cls_script_10_inst();
_cls_inst._call(thisJoinPoint.getSignature().toString(), 0/*evt_ADMIN_initialise*/, 2/*evt_anySymbol*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 0/*evt_ADMIN_initialise*/, 2/*evt_anySymbol*/);
}
}
before ( LarvaController __0) : (call(* LarvaController.triggerReset(..)) && target(__0) && !cflow(adviceexecution()) && !cflow(within(larva.*))  && !(within(larva.*))) {

synchronized(_asp_script_10.lock){

_cls_script_10 _cls_inst = _cls_script_10._get_cls_script_10_inst();
_cls_inst.__0 = __0;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 3/*evt_LarvaReset*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 3/*evt_LarvaReset*/);
}
}
before ( LarvaController __1) : (call(* LarvaController.triggerStop(..)) && target(__1) && !cflow(adviceexecution()) && !cflow(within(larva.*))  && !(within(larva.*))) {

synchronized(_asp_script_10.lock){

_cls_script_10 _cls_inst = _cls_script_10._get_cls_script_10_inst();
_cls_inst.__1 = __1;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 5/*evt_LarvaStop*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 5/*evt_LarvaStop*/);
}
}
}