package aspects;

import java.util.*;
import larvaTools.LarvaController;
import larvaTools.TA.AutomatonInstance;
import larvaTools.TA.StateSkip;
import larvaTools.TA.Outcome;
import com.guichaguri.minimalftp.handler.ConnectionHandler;

import larva.*;
public aspect _asp_script_22 {

boolean initialized = false;

after():(staticinitialization(*)){
if (!initialized){
	initialized = true;
	_cls_script_22.initialize();
}
}
before ( Object ssObj,Channel _c) : (call(* Channel.receive(..)) && target(_c) && (if (_c.equals(_cls_script_21.channel_stateSkip))) && args(ssObj)) {

synchronized(_asp_script_20.lock){
AutomatonInstance ta;
ConnectionHandler _cH;
StateSkip ss;
ss =(StateSkip )ssObj ;
ta =ss .getInstance ();
_cH =(ConnectionHandler )ss .getVar (0 );

_cls_script_22 _cls_inst = _cls_script_22._get_cls_script_22_inst( ta,_cH);
_cls_inst.ssObj = ssObj;
_cls_script_22.ss = ss;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 15/*evt_channelStateSkip*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 15/*evt_channelStateSkip*/);
}
}
}