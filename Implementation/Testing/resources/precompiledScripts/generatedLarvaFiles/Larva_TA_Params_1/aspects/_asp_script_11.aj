package aspects;

import java.util.*;
import larvaTools.LarvaController;
import larvaTools.TA.AutomatonInstance;
import larvaTools.TA.StateSkip;
import larvaTools.TA.Outcome;
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
before ( UserAccount ua) : (call(* UserAccount.withdraw(..)) && target(ua) && !cflow(adviceexecution()) && !cflow(within(larva.*))  && !(within(larva.*))) {

synchronized(_asp_script_10.lock){
UserAccount _ua;
_ua =ua ;

_cls_script_11 _cls_inst = _cls_script_11._get_cls_script_11_inst( _ua);
_cls_inst.ua = ua;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 6/*evt_withdraw_ua*/, 8/*evt_anySymbol_ua*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 6/*evt_withdraw_ua*/, 8/*evt_anySymbol_ua*/);
}
}
before ( Object outcomeObj,Channel _c) : (call(* Channel.receive(..)) && target(_c) && (if (_c.equals(_cls_script_11.channel_TAsToMain))) && args(outcomeObj)) {

synchronized(_asp_script_10.lock){
UserAccount _ua;
String state;
final Outcome outcome =(Outcome )outcomeObj ;
state =outcome .getState ();
_ua =(UserAccount )outcome .getVar (0 );

_cls_script_11 _cls_inst = _cls_script_11._get_cls_script_11_inst( _ua);
_cls_inst.outcomeObj = outcomeObj;
_cls_script_11.state = state;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 9/*evt_channelTAsToMain*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 9/*evt_channelTAsToMain*/);
}
}
before ( UserAccount ua) : (call(* UserAccount.deposit(..)) && target(ua) && !cflow(adviceexecution()) && !cflow(within(larva.*))  && !(within(larva.*))) {

synchronized(_asp_script_10.lock){
UserAccount _ua;
_ua =ua ;

_cls_script_11 _cls_inst = _cls_script_11._get_cls_script_11_inst( _ua);
_cls_inst.ua = ua;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 4/*evt_deposit_ua*/, 8/*evt_anySymbol_ua*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 4/*evt_deposit_ua*/, 8/*evt_anySymbol_ua*/);
}
}
}