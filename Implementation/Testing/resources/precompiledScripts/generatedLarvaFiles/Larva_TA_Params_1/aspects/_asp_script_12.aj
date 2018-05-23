package aspects;

import java.util.*;
import larvaTools.LarvaController;
import larvaTools.TA.AutomatonInstance;
import larvaTools.TA.StateSkip;
import larvaTools.TA.Outcome;
import transactionsystem.UserAccount;

import larva.*;
public aspect _asp_script_12 {

boolean initialized = false;

after():(staticinitialization(*)){
if (!initialized){
	initialized = true;
	_cls_script_12.initialize();
}
}
before ( Object ssObj,Channel _c) : (call(* Channel.receive(..)) && target(_c) && (if (_c.equals(_cls_script_11.channel_stateSkip))) && args(ssObj)) {

synchronized(_asp_script_10.lock){
AutomatonInstance ta;
UserAccount _ua;
StateSkip ss;
ss =(StateSkip )ssObj ;
ta =ss .getInstance ();
_ua =(UserAccount )ss .getVar (0 );

_cls_script_12 _cls_inst = _cls_script_12._get_cls_script_12_inst( ta,_ua);
_cls_inst.ssObj = ssObj;
_cls_script_12.ss = ss;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 11/*evt_channelStateSkip*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 11/*evt_channelStateSkip*/);
}
}
}