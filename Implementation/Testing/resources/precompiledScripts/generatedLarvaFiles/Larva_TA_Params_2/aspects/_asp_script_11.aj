package aspects;

import java.util.*;
import larvaTools.LarvaController;
import larvaTools.TA.AutomatonInstance;
import larvaTools.TA.StateSkip;
import larvaTools.TA.Outcome;
import transactionsystem.UserInfo;
import transactionsystem.UserAccount;

import larva.*;
public aspect _asp_script_11 {

boolean initialized = false;

after():(staticinitialization(*)){
if (!initialized){
	initialized = true;
	_cls_script_11.initialize();
}
}
before ( UserInfo ui) : (call(* UserInfo.openSession(..)) && target(ui) && !cflow(adviceexecution()) && !cflow(within(larva.*))  && !(within(larva.*))) {

synchronized(_asp_script_10.lock){
UserInfo _ui;
_ui =ui ;

_cls_script_11 _cls_inst = _cls_script_11._get_cls_script_11_inst( _ui);
_cls_inst.ui = ui;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 7/*evt_openSession_ui*/, 9/*evt_anySymbol_ui*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 7/*evt_openSession_ui*/, 9/*evt_anySymbol_ui*/);
}
}
}