package aspects;

import java.util.*;
import larvaTools.LarvaController;
import larvaTools.TA.AutomatonInstance;
import larvaTools.TA.StateSkip;
import larvaTools.TA.Outcome;
import transactionsystem.UserInfo;
import transactionsystem.UserAccount;

import larva.*;
public aspect _asp_script_13 {

boolean initialized = false;

after():(staticinitialization(*)){
if (!initialized){
	initialized = true;
	_cls_script_13.initialize();
}
}
before ( Object ssObj,Channel _c) : (call(* Channel.receive(..)) && target(_c) && (if (_c.equals(_cls_script_12.channel_stateSkip))) && args(ssObj)) {

synchronized(_asp_script_10.lock){
AutomatonInstance ta;
UserAccount _ua;
UserInfo _ui;
StateSkip ss;
ss =(StateSkip )ssObj ;
ta =ss .getInstance ();
_ui =(UserInfo )ss .getVar (0 );
_ua =(UserAccount )ss .getVar (1 );

_cls_script_13 _cls_inst = _cls_script_13._get_cls_script_13_inst( ta,_ua,_ui);
_cls_inst.ssObj = ssObj;
_cls_script_13.ss = ss;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 17/*evt_channelStateSkip*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 17/*evt_channelStateSkip*/);
}
}
}