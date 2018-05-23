package aspects;

import java.util.*;
import larvaTools.LarvaController;
import larvaTools.TA.AutomatonInstance;
import larvaTools.TA.StateSkip;
import larvaTools.TA.Outcome;

import larva.*;
public aspect _asp_script_41 {

boolean initialized = false;

after():(staticinitialization(*)){
if (!initialized){
	initialized = true;
	_cls_script_41.initialize();
}
}
before ( Object ssObj,Channel _c) : (call(* Channel.receive(..)) && target(_c) && (if (_c.equals(_cls_script_40.channel_stateSkip))) && args(ssObj)) {

synchronized(_asp_script_40.lock){
AutomatonInstance ta;
StateSkip ss;
ss =(StateSkip )ssObj ;
ta =ss .getInstance ();

_cls_script_41 _cls_inst = _cls_script_41._get_cls_script_41_inst( ta);
_cls_inst.ssObj = ssObj;
_cls_script_41.ss = ss;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 11/*evt_channelStateSkip*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 11/*evt_channelStateSkip*/);
}
}
}