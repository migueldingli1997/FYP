package aspects;

import java.util.*;
import larvaTools.LarvaController;
import larvaTools.TA.AutomatonInstance;
import larvaTools.TA.StateSkip;
import larvaTools.TA.Outcome;
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
before ( Object outcomeObj,Channel _c) : (call(* Channel.receive(..)) && target(_c) && (if (_c.equals(_cls_script_22.channel_TAsToMain))) && args(outcomeObj)) {

synchronized(_asp_script_20.lock){
UserAccount _ua;
UserInfo _ui;
String state;
final Outcome outcome =(Outcome )outcomeObj ;
state =outcome .getState ();
_ui =(UserInfo )outcome .getVar (0 );
_ua =(UserAccount )outcome .getVar (1 );

_cls_script_22 _cls_inst = _cls_script_22._get_cls_script_22_inst( _ua,_ui);
_cls_inst.outcomeObj = outcomeObj;
_cls_script_22.state = state;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 9/*evt_channelTAsToMain*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 9/*evt_channelTAsToMain*/);
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
_cls_inst._call(thisJoinPoint.getSignature().toString(), 4/*evt_deposit_ui_ua*/, 8/*evt_anySymbol_ua*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 4/*evt_deposit_ui_ua*/, 8/*evt_anySymbol_ua*/);
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
_cls_inst._call(thisJoinPoint.getSignature().toString(), 6/*evt_withdraw_ui_ua*/, 8/*evt_anySymbol_ua*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 6/*evt_withdraw_ui_ua*/, 8/*evt_anySymbol_ua*/);
}
}
}