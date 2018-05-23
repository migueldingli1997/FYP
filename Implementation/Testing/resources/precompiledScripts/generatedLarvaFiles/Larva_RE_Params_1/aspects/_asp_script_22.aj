package aspects;

import java.util.*;
import larvaTools.LarvaController;
import larvaTools.RE.RegExpHolder;
import RE.lib.basic.Symbol;
import transactionsystem.UserInfo;
import transactionsystem.UserAccount;

import larva.*;
public aspect _asp_script_22 {

boolean initialized = false;

after():(staticinitialization(*)){
if (!initialized){
	initialized = true;
	_cls_script_22.initialize();
}
}
before ( UserAccount ua) : (call(* UserAccount.deposit(..)) && target(ua) && !cflow(adviceexecution()) && !cflow(within(larva.*))  && !(within(larva.*))) {

synchronized(_asp_script_20.lock){
UserAccount _ua;
UserInfo _ui;
_ua =ua ;
_ui =ua .getOwnerInfo ();

_cls_script_22 _cls_inst = _cls_script_22._get_cls_script_22_inst( _ua,_ui);
_cls_inst.ua = ua;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 6/*evt_deposit_ui_ua*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 6/*evt_deposit_ui_ua*/);
}
}
before ( UserAccount ua) : (call(* UserAccount.withdraw(..)) && target(ua) && !cflow(adviceexecution()) && !cflow(within(larva.*))  && !(within(larva.*))) {

synchronized(_asp_script_20.lock){
UserAccount _ua;
UserInfo _ui;
_ua =ua ;
_ui =ua .getOwnerInfo ();

_cls_script_22 _cls_inst = _cls_script_22._get_cls_script_22_inst( _ua,_ui);
_cls_inst.ua = ua;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 8/*evt_withdraw_ui_ua*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 8/*evt_withdraw_ui_ua*/);
}
}
}