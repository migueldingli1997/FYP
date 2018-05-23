package aspects;

import java.util.*;
import larvaTools.LarvaController;
import larvaTools.RE.RegExpHolder;
import RE.lib.basic.Symbol;
import transactionsystem.UserAccount;

import larva.*;
public aspect _asp_script_21 {

boolean initialized = false;

after():(staticinitialization(*)){
if (!initialized){
	initialized = true;
	_cls_script_21.initialize();
}
}
before ( UserAccount ua) : (call(* UserAccount.withdraw(..)) && target(ua) && !cflow(adviceexecution()) && !cflow(within(larva.*))  && !(within(larva.*))) {

synchronized(_asp_script_20.lock){
UserAccount _ua;
_ua =ua ;

_cls_script_21 _cls_inst = _cls_script_21._get_cls_script_21_inst( _ua);
_cls_inst.ua = ua;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 8/*evt_withdraw_ua*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 8/*evt_withdraw_ua*/);
}
}
before ( UserAccount ua) : (call(* UserAccount.deposit(..)) && target(ua) && !cflow(adviceexecution()) && !cflow(within(larva.*))  && !(within(larva.*))) {

synchronized(_asp_script_20.lock){
UserAccount _ua;
_ua =ua ;

_cls_script_21 _cls_inst = _cls_script_21._get_cls_script_21_inst( _ua);
_cls_inst.ua = ua;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 6/*evt_deposit_ua*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 6/*evt_deposit_ua*/);
}
}
}